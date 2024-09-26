package com.example.demo.service;

import com.example.demo.model.OrdenProducto;
import com.example.demo.model.Producto;
import com.example.demo.model.Reporte;
import com.example.demo.repository.OrdenCompraRepository;
import com.example.demo.repository.OrdenProductoRepository;
import com.example.demo.repository.OrdenVentaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReporteService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private OrdenVentaRepository ordenVentaRepository;

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Reporte de compras
    public Mono<Reporte> generarReporteCompras(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenCompraRepository.findByFechaOrdenBetween(fechaInicio, fechaFin)
                .collectList()  // Recolectar todas las órdenes dentro del rango de fechas
                .flatMap(ordenes -> {
                    if (ordenes.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("No se encontraron compras en el intervalo de tiempo"));
                    }
                    // Obtener todos los productos asociados a las órdenes
                    return Flux.fromIterable(ordenes)
                            .flatMap(ordenCompra -> ordenProductoRepository.findByOrdenId(ordenCompra.getId()).collectList())
                            .collectList();  // Recoger todos los productos de las órdenes
                })
                .flatMap(productosOrdenados -> {
                    // Obtener los detalles de cada producto
                    return Flux.fromIterable(productosOrdenados)
                            .flatMap(ordenProducto -> Flux.fromIterable(ordenProducto.get(0).getProductoIds()))
                            .flatMap(productoId -> productoRepository.findById(productoId))
                            .collectList()
                            .flatMap(productos -> {
                                double total = productos.stream().mapToDouble(Producto::getPrice).sum();
                                return Mono.just(new Reporte(fechaInicio, fechaFin, productos, total));
                            });
                });
    }

    // Reporte de ventas
    public Mono<Reporte> generarReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenVentaRepository.findByFechaOrdenBetween(fechaInicio, fechaFin)
                .collectList()  // Recolectar todas las órdenes dentro del rango de fechas
                .flatMap(ordenes -> {
                    if (ordenes.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("No se encontraron ventas en el intervalo de tiempo"));
                    }
                    // Recolectar todos los productos asociados a las órdenes
                    return Mono.just(ordenes)
                            .flatMapMany(ordenesList -> Flux.fromIterable(ordenesList)
                                    .flatMap(ordenVenta -> ordenProductoRepository.findByOrdenId(ordenVenta.getId())
                                            .collectList())  // Obtener los productos para cada orden
                            )
                            .collectList();  // Recoger todos los productos de las órdenes
                })
                .flatMap(productosOrdenadosList -> {
                    // Validar que existan productos antes de continuar
                    if (productosOrdenadosList.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("No se encontraron productos asociados a las órdenes en el rango de fechas"));
                    }

                    // Crear una lista para almacenar los IDs de los productos
                    List<Long> productoIds = new ArrayList<>();

                    // Iterar sobre las listas de productos y recolectar los IDs de los productos
                    for (List<OrdenProducto> productosOrdenados : productosOrdenadosList) {
                        for (OrdenProducto ordenProducto : productosOrdenados) {
                            productoIds.addAll(ordenProducto.getProductoIds());
                        }
                    }

                    // Obtener los detalles de cada producto y eliminar duplicados
                    return Flux.fromIterable(productoIds)
                            .distinct()  // Eliminar duplicados
                            .flatMap(productoId -> productoRepository.findById(productoId))  // Obtener los detalles del producto
                            .collectList()  // Recolectar todos los productos
                            .flatMap(productos -> {
                                if (productos.isEmpty()) {
                                    return Mono.error(new IllegalArgumentException("No se encontraron productos en las órdenes"));
                                }
                                // Calcular el total de la venta
                                double total = productos.stream().mapToDouble(Producto::getPrice).sum();
                                return Mono.just(new Reporte(fechaInicio, fechaFin, productos, total));
                            });
                });
    }

    // Reporte de top 5 productos más vendidos
    public Mono<List<Producto>> generarTop5ArticulosVendidos(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenProductoRepository.findAll()
                .collectList()
                .flatMap(ordenProductos -> {
                    // Contar las ventas por producto
                    Map<Long, Long> conteoPorProducto = new HashMap<>();
                    ordenProductos.forEach(ordenProducto -> {
                        List<Long> productoIds = ordenProducto.getProductoIds();
                        productoIds.forEach(productoId -> conteoPorProducto.merge(productoId, 1L, Long::sum));
                    });

                    // Ordenar los productos por el número de ventas
                    return Flux.fromIterable(conteoPorProducto.entrySet())
                            .sort(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .take(5)  // Tomar los 5 productos más vendidos
                            .flatMap(entry -> productoRepository.findById(entry.getKey()))  // Obtener los detalles de los productos
                            .collectList();
                });
    }
}
