package com.programacion.reactiva.actividad_final.service;

import com.programacion.reactiva.actividad_final.model.Cart;
import com.programacion.reactiva.actividad_final.model.User;
import com.programacion.reactiva.actividad_final.model.dto.ShoppingCartDTO;
import com.programacion.reactiva.actividad_final.repository.CartRepository;
import com.programacion.reactiva.actividad_final.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Mono<ShoppingCartDTO> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .flatMap(user -> {
                    Cart cart = new Cart();
                    cart.setUserId(user.getId());
                    cart.setTotal(0.0);

                    return cartRepository.save(cart).map(savedCart -> {
                        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
                        shoppingCartDTO.setCartId(savedCart.getId());
                        shoppingCartDTO.setUserId(user.getId());
                        shoppingCartDTO.setItems(Collections.emptyList());
                        shoppingCartDTO.setTotal(savedCart.getTotal());

                        return shoppingCartDTO;
                    });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }
}
