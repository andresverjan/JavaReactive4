package org.api;

import org.api.controllers.ProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {


    @Autowired
    private ProductController controller;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}