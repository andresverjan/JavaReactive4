package compras.controller;

import compras.model.ProductEntity;
import compras.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductEntity> getProducts(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ProductEntity> getProductById(@PathVariable Integer id){
        return productService.findById(id);
    }

    @GetMapping("/{}")
    public Flux<ProductEntity> searchProductos(@RequestParam String name) {
        return productService.findByName(name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductEntity> createProducto(@RequestBody ProductEntity producto) {
        return productService.create(producto);
    }

    @PutMapping()
    public Mono<ProductEntity> updateProducto(@RequestBody ProductEntity producto) {
        return productService.update(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProducto(@PathVariable Integer id) {
        return productService.deleteById(id);
    }
}
