package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.users.User;
import valko.co.cartmanagament.repository.users.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> createUser(User user) {
        user.toBuilder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    public Mono<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Mono<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> updateUser(Integer id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.toBuilder()
                            .name(user.name())
                            .email(user.email())
                            .address(user.address())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return userRepository.save(user);
                });
    }

    public Mono<Void> deleteUserById(Integer id) {
        return userRepository.deleteById(id);
    }
}
