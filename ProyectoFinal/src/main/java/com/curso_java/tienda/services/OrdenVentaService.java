package com.curso_java.tienda.services;

import com.curso_java.tienda.dtos.OrdenVentaDTO;
import com.curso_java.tienda.dtos.OrdenVentaProductoDTO;
import com.curso_java.tienda.entities.OrdenVenta;
import com.curso_java.tienda.entities.OrdenVentaProducto;
import com.curso_java.tienda.repositories.OrdenVentaProductoRepository;
import com.curso_java.tienda.repositories.OrdenVentaRepository;
import com.curso_java.tienda.repositories.ProductoRepository;
import com.curso_java.tienda.repositories.UsuarioRepository;
import com.curso_java.tienda.requests.OrdenProductoRequest;
import com.curso_java.tienda.requests.OrdenVentaEditarRequest;
import com.curso_java.tienda.requests.OrdenVentaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para gestionar las órdenes de venta.
 */
@Service
public class OrdenVentaService {

    @Autowired
    private OrdenVentaRepository ordenVentaRepository;
    @Autowired
    private OrdenVentaProductoRepository ordenVentaProductoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Crea una nueva orden de venta.
     *
     * @param request Datos de la orden de venta a crear.
     * @return Mono con el DTO de la orden de venta creada.
     */
    public Mono<OrdenVentaDTO> createOrdenVenta(OrdenVentaRequest request) {
        // Validar que el usuario existe y tiene el rol adecuado (cliente o ambos)
        return usuarioRepository.findById(request.getUsuarioId())
                .flatMap(usuario -> {
                    // Si el usuario no tiene el rol adecuado, retornar un Mono vacío
                    if (!usuario.getRol().equalsIgnoreCase("cliente") && !usuario.getRol().equalsIgnoreCase("ambos")) {
                        return Mono.empty();
                    }

                    // Paso 1: Crear la entidad OrdenVenta sin los productos
                    OrdenVenta ordenVenta = new OrdenVenta();
                    ordenVenta.setUsuarioId(request.getUsuarioId());
                    ordenVenta.setTotal(BigDecimal.ZERO);
                    ordenVenta.setCreatedAt(LocalDateTime.now());
                    ordenVenta.setUpdatedAt(LocalDateTime.now());

                    // Guardar la OrdenVenta sin productos
                    return ordenVentaRepository.save(ordenVenta)
                            .flatMap(savedOrden -> {
                                // Paso 2: Crear los productos asociados a la orden
                                return Flux.fromIterable(request.getProductos())
                                        .flatMap(productoRequest -> {
                                            // Buscar el producto por su ID
                                            return productoRepository.findById(productoRequest.getProductoId())
                                                    .flatMap(producto -> {
                                                        // Verificar si hay suficiente stock antes de crear la OrdenVentaProducto
                                                        if (producto.getStock() < productoRequest.getCantidad()) {
                                                            return Mono.error(new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre()));
                                                        }

                                                        // Actualizar el stock del producto
                                                        producto.setStock(producto.getStock() - productoRequest.getCantidad());

                                                        // Crear la entidad OrdenVentaProducto
                                                        OrdenVentaProducto ordenVentaProducto = new OrdenVentaProducto();
                                                        ordenVentaProducto.setOrdenId(savedOrden.getId());
                                                        ordenVentaProducto.setProductoId(producto.getId());
                                                        ordenVentaProducto.setCantidad(productoRequest.getCantidad());
                                                        ordenVentaProducto.setPrecioUnitario(producto.getPrecio());
                                                        ordenVentaProducto.setCreatedAt(LocalDateTime.now());
                                                        ordenVentaProducto.setUpdatedAt(LocalDateTime.now());

                                                        // Guardar el producto y actualizar el stock
                                                        return productoRepository.save(producto)
                                                                .then(ordenVentaProductoRepository.save(ordenVentaProducto));
                                                    });
                                        })
                                        .collectList()
                                        .flatMap(productosOrden -> {
                                            // Calcular el total de la orden
                                            BigDecimal total = calculateTotalOrden(productosOrden);
                                            savedOrden.setTotal(total);
                                            savedOrden.setEstado("Creada");

                                            // Guardar la orden con el total actualizado
                                            return ordenVentaRepository.save(savedOrden)
                                                    .then(Mono.just(toOrdenVentaDTO(savedOrden)));
                                        });
                            });
                })
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Edita una orden de venta existente.
     *
     * @param ordenId ID de la orden de venta a editar.
     * @param request Datos de la orden de venta a editar.
     * @return Mono con el DTO de la orden de venta editada.
     */
    public Mono<OrdenVentaDTO> editaOrdenVenta(String ordenId, OrdenVentaEditarRequest request) {
        return ordenVentaRepository.findById(ordenId)
                // Si no se encuentra la orden, retornar un Mono vacío
                .switchIfEmpty(Mono.empty())
                .flatMap(ordenVenta -> {
                    // Para cada producto en la solicitud, verificar si ya existe en la orden y luego editarlo
                    return Flux.fromIterable(request.getProductos())
                            .flatMap(productoEditado -> {
                                return ordenVentaProductoRepository.findByOrdenIdAndProductoId(ordenId, productoEditado.getProductoId())
                                        // Si no se encuentra el producto en la orden, lanzar un error
                                        .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado en la orden")))
                                        .flatMap(ordenVentaProducto -> {
                                            // Verificar la cantidad anterior y la nueva cantidad
                                            int cantidadAnterior = ordenVentaProducto.getCantidad();
                                            int cantidadNueva = productoEditado.getCantidad();

                                            // Si la cantidad ha cambiado, actualizar el inventario
                                            if (cantidadNueva != cantidadAnterior) {
                                                return productoRepository.findById(productoEditado.getProductoId())
                                                        .flatMap(producto -> {
                                                            // Calcular el ajuste en el inventario
                                                            int ajusteInventario = cantidadAnterior - cantidadNueva;

                                                            // Verificar si hay suficiente stock si se está incrementando la cantidad
                                                            if (ajusteInventario < 0 && producto.getStock() < Math.abs(ajusteInventario)) {
                                                                return Mono.error(new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre()));
                                                            }

                                                            // Actualizar el stock del producto
                                                            producto.setStock(producto.getStock() + ajusteInventario); // Ajuste positivo o negativo
                                                            ordenVentaProducto.setCantidad(cantidadNueva);
                                                            ordenVentaProducto.setUpdatedAt(LocalDateTime.now());

                                                            // Guardar los cambios en el inventario y el producto de la orden
                                                            return productoRepository.save(producto)
                                                                    .then(ordenVentaProductoRepository.save(ordenVentaProducto));
                                                        });
                                            } else {
                                                // Si la cantidad no cambió, solo actualizar la fecha
                                                ordenVentaProducto.setUpdatedAt(LocalDateTime.now());
                                                return ordenVentaProductoRepository.save(ordenVentaProducto);
                                            }
                                        }).onErrorResume(e -> Mono.error(new RuntimeException("Producto no encontrado en la orden")));
                            })
                            // Recalcular el total después de editar los productos
                            .thenMany(ordenVentaProductoRepository.findAllByOrdenId(ordenId))
                            .collectList() // Recoger todos los productos de la orden
                            .flatMap(ordenVentaProductos -> {
                                // Calcular el nuevo total de la orden
                                BigDecimal nuevoTotal = calculateTotalOrden(ordenVentaProductos);

                                // Actualizar el total de la orden
                                ordenVenta.setTotal(nuevoTotal);
                                ordenVenta.setUpdatedAt(LocalDateTime.now());
                                ordenVenta.setEstado(request.getEstado());

                                // Guardar la orden actualizada
                                return ordenVentaRepository.save(ordenVenta).flatMap(updatedOrden -> Mono.just(toOrdenVentaDTO(updatedOrden)));
                            });
                });
    }

    /**
     * Agrega nuevos productos a una orden de venta existente.
     *
     * @param ordenId ID de la orden de venta a la que se agregarán los productos.
     * @param nuevosProductos Lista de productos a agregar a la orden de venta.
     * @return Mono con el DTO de la orden de venta actualizada.
     */
    public Mono<OrdenVentaDTO> addProductosToOrdenVenta(String ordenId, List<OrdenProductoRequest> nuevosProductos) {
        return ordenVentaRepository.findById(ordenId)
                // Si no se encuentra la orden, retornar un Mono vacío
                .switchIfEmpty(Mono.empty())
                .flatMap(ordenVenta -> {
                    // Verificar si la orden está cancelada
                    if ("Cancelada".equals(ordenVenta.getEstado())) {
                        return Mono.error(new RuntimeException("No se pueden agregar productos a una orden cancelada"));
                    }

                    // Paso 1: Obtener los productos ya existentes en la orden
                    return ordenVentaProductoRepository.findAllByOrdenId(ordenId)
                            .collectList()
                            .flatMap(productosExistentes -> {
                                // Paso 2: Filtrar los nuevos productos que ya están en la orden
                                List<OrdenProductoRequest> productosAAgregar = nuevosProductos.stream()
                                        .filter(nuevoProducto -> productosExistentes.stream()
                                                .noneMatch(productoExistente -> productoExistente.getProductoId()
                                                        .equals(nuevoProducto.getProductoId())
                                                )
                                        )
                                        .toList(); // Convertir a lista los productos que no están en la orden

                                // Paso 3: Agregar los productos que no están presentes en la orden
                                return Flux.fromIterable(productosAAgregar)
                                        .flatMap(nuevoProducto -> {
                                            // Buscar el producto por su ID
                                            return productoRepository.findById(nuevoProducto.getProductoId())
                                                    .flatMap(producto -> {
                                                        // Verificar si hay suficiente stock antes de agregar el producto a la orden
                                                        if (producto.getStock() < nuevoProducto.getCantidad()) {
                                                            return Mono.error(new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre()));
                                                        }

                                                        // Actualizar el stock del producto
                                                        producto.setStock(producto.getStock() - nuevoProducto.getCantidad());

                                                        // Crear la entidad OrdenVentaProducto
                                                        OrdenVentaProducto nuevoOrdenVentaProducto = new OrdenVentaProducto();
                                                        nuevoOrdenVentaProducto.setOrdenId(ordenId);
                                                        nuevoOrdenVentaProducto.setProductoId(producto.getId());
                                                        nuevoOrdenVentaProducto.setCantidad(nuevoProducto.getCantidad());
                                                        nuevoOrdenVentaProducto.setPrecioUnitario(producto.getPrecio());
                                                        nuevoOrdenVentaProducto.setCreatedAt(LocalDateTime.now());
                                                        nuevoOrdenVentaProducto.setUpdatedAt(LocalDateTime.now());

                                                        // Guardar el producto y actualizar el stock
                                                        return productoRepository.save(producto)
                                                                .then(ordenVentaProductoRepository.save(nuevoOrdenVentaProducto))
                                                                // Retornar el total para este producto
                                                                .thenReturn(producto.getPrecio().multiply(new BigDecimal(nuevoProducto.getCantidad())));
                                                    });
                                        })
                                        .collectList() // Recolectar los totales de los productos agregados
                                        .flatMap(totalesProductos -> {
                                            // Paso 4: Sumar los totales y actualizar el total de la orden
                                            BigDecimal totalAdicional = totalesProductos.stream()
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar los precios de todos los productos agregados

                                            // Actualizar el total de la orden
                                            ordenVenta.setTotal(ordenVenta.getTotal().add(totalAdicional));
                                            // Actualizar la fecha de modificación
                                            ordenVenta.setUpdatedAt(LocalDateTime.now());

                                            // Guardar la orden con el nuevo total
                                            return ordenVentaRepository.save(ordenVenta)
                                                    // Paso 5: Retornar la orden con los productos agregados
                                                    .then(Mono.just(toOrdenVentaDTO(ordenVenta)));
                                        });
                            });
                });
    }

    /**
     * Obtiene todas las órdenes de venta.
     *
     * Este método recupera todas las entidades de OrdenVenta de la base de datos,
     * las convierte a DTOs de OrdenVentaDTO y las devuelve en un Flux.
     *
     * @return \`Flux<OrdenVentaDTO>\` que emite cada una de las órdenes de venta encontradas.
     */
    public Flux<OrdenVentaDTO> getOrdenesVenta() {
        // Recuperar todas las entidades de OrdenVenta de la base de datos
        return ordenVentaRepository.findAll()
                // Convertir cada entidad OrdenVenta a un DTO de OrdenVentaDTO
                .map(ordenVenta -> toOrdenVentaDTO(ordenVenta));
    }

    /**
     * Obtiene todos los productos asociados a una orden de venta específica.
     *
     * Este método recupera todas las entidades OrdenVentaProducto asociadas a una orden de venta
     * identificada por su ordenId. Luego, para cada OrdenVentaProducto, busca los detalles del
     * producto correspondiente en el repositorio de productos y convierte la entidad OrdenVentaProducto
     * a un DTO de OrdenVentaProductoDTO.
     *
     * @param ordenId ID de la orden de venta cuyos productos se desean obtener.
     * @return Flux<OrdenVentaProductoDTO> que emite cada uno de los productos encontrados en la orden de venta.
     */
    public Flux<OrdenVentaProductoDTO> getProductsByOrden(String ordenId) {
        // Buscar todas las entidades OrdenVentaProducto asociadas a la orden de venta por ordenId
        return ordenVentaProductoRepository.findAllByOrdenId(ordenId)
                // Para cada OrdenVentaProducto, buscar los detalles del producto por productoId
                .flatMap(ordenVentaProducto -> productoRepository.findById(ordenVentaProducto.getProductoId())
                        // Convertir la entidad OrdenVentaProducto a un DTO de OrdenVentaProductoDTO
                        .map(producto -> toOrdenVentaProductoDTO(ordenVentaProducto)))
                // Si no se encuentran productos en la orden, devolver un Mono vacío
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Cancela una orden de venta existente.
     *
     * Este método busca una orden de venta por su ID y, si la encuentra y no está ya cancelada,
     * actualiza su estado a "Cancelada" y la guarda en el repositorio. Si la orden ya está cancelada,
     * lanza un error. Si no se encuentra la orden, retorna un Mono vacío.
     *
     * @param ordenId ID de la orden de venta a cancelar.
     * @return Mono con el DTO de la orden de venta cancelada, o un Mono vacío si no se encuentra la orden.
     */
    public Mono<OrdenVentaDTO> cancelOrdenVenta(String ordenId) {
        // Buscar la orden de venta por su ID
        return ordenVentaRepository.findById(ordenId)
                .flatMap(orden -> {
                    // Verificar si la orden ya está cancelada
                    if ("Cancelada".equals(orden.getEstado())) {
                        // Si la orden ya está cancelada, lanzar un error
                        return Mono.error(new RuntimeException("La orden ya ha sido cancelada"));
                    }
                    // Actualizar el estado de la orden a "Cancelada"
                    orden.setEstado("Cancelada");
                    // Actualizar la fecha de modificación de la orden
                    orden.setUpdatedAt(LocalDateTime.now());
                    // Guardar la orden actualizada en el repositorio
                    return ordenVentaRepository.save(orden)
                            // Convertir la entidad OrdenVenta a un DTO de OrdenVentaDTO y retornarlo
                            .then(Mono.just(toOrdenVentaDTO(orden)));
                })
                // Si no se encuentra la orden, retornar un Mono vacío
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Calcula el total de una orden de venta.
     *
     * Este método toma una lista de OrdenVentaProducto y calcula el total de la orden
     * sumando el precio unitario multiplicado por la cantidad de cada producto en la lista.
     *
     * @param productosOrden Lista de productos en la orden de venta.
     * @return El total de la orden de venta como BigDecimal.
     */
    private BigDecimal calculateTotalOrden(List<OrdenVentaProducto> productosOrden) {
        // Convertir la lista de productos en un stream
        return productosOrden.stream()
                // Mapear cada producto a su precio total (precio unitario * cantidad)
                .map(producto -> producto.getPrecioUnitario().multiply(BigDecimal.valueOf(producto.getCantidad())))
                // Reducir el stream sumando todos los precios totales de los productos
                .reduce(BigDecimal.ZERO, (acumulador, valorActual) -> acumulador.add(valorActual));
    }

    /**
     * Convierte una entidad OrdenVenta a su correspondiente DTO OrdenVentaDTO.
     *
     * @param orden La entidad OrdenVenta que se desea convertir a DTO.
     * @return Una nueva instancia de OrdenVentaDTO que contiene los datos de la entidad OrdenVenta.
     */
    public OrdenVentaDTO toOrdenVentaDTO(OrdenVenta orden) {
        // Crear y retornar una nueva instancia de OrdenVentaDTO con los datos de la entidad OrdenVenta
        return new OrdenVentaDTO(orden.getId(), orden.getUsuarioId(), orden.getTotal(), orden.getEstado());
    }

    /**
     * Convierte una entidad OrdenVentaProducto a su correspondiente DTO OrdenVentaProductoDTO.
     *
     * @param ordenVentaProducto La entidad OrdenVentaProducto que se desea convertir a DTO.
     * @return Una nueva instancia de OrdenVentaProductoDTO que contiene los datos de la entidad OrdenVentaProducto.
     */
    public OrdenVentaProductoDTO toOrdenVentaProductoDTO(OrdenVentaProducto ordenVentaProducto) {
        // Crear y retornar una nueva instancia de OrdenVentaProductoDTO con los datos de la entidad OrdenVentaProducto
        return new OrdenVentaProductoDTO(ordenVentaProducto.getProductoId(), ordenVentaProducto.getCantidad(), ordenVentaProducto.getPrecioUnitario());
    }
}