package org.example.actividadfinal.service;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.User;
import org.example.actividadfinal.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public Mono<ResponseData> createSeller(User user) {
        return userRepository.save(user)
            .flatMap(s -> Mono.just(
                ResponseData.builder()
                .data(s)
                .message(user.getType() + " creado exitosamente!")
                .build()
            ));
    }
}
