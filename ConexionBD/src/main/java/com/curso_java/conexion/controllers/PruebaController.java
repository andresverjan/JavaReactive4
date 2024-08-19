package com.curso_java.conexion.controllers;

import com.curso_java.conexion.entities.Prueba;
import com.curso_java.conexion.service.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class PruebaController {

    @Autowired
    private PruebaService service;

    public void printEntityById(Integer id) {
        Mono<Prueba> entityMono = service.getEntityById(id);
        entityMono.subscribe(entity -> System.out.println(entity.getNombre()));
    }
}