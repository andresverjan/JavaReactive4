package org.example.actividadfinal.controller;


import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.User;
import org.example.actividadfinal.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public Mono<ResponseData> saveUser(@RequestBody User user) {
        return userService.createSeller(user);
    }
}
