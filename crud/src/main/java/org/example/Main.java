package org.example;

import org.example.controllers.PersonaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    @Autowired
    private PersonaController controller;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}