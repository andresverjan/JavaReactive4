package org.api;

import org.api.controllers.ProductController;
import org.api.controllers.ShoppingCartController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {


    @Autowired
    private ProductController productController;

    @Autowired
    private ShoppingCartController shoppingCartController;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}