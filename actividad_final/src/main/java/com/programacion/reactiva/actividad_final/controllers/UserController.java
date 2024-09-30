package com.programacion.reactiva.actividad_final.controllers;

import com.programacion.reactiva.actividad_final.model.User;
import com.programacion.reactiva.actividad_final.model.dto.ShoppingCartDTO;
import com.programacion.reactiva.actividad_final.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @GetMapping
    public Mono<ShoppingCartDTO> findUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return userService.findByEmailAndPassword(email, password);
    }
}
