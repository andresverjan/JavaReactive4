package org.example;

import reactor.core.publisher.Mono;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserService {
    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.getUserData("error").subscribe(
                data->{
                    System.out.println("User data: " + data);
                },
                error -> System.err.println("Final error: " + error)
        );
    }

    public Mono<String> getUserData(String userId){
        //return Mono.just(userId).doOnNext(a-> System.out.println(a));
        return primaryUserService(userId)
                .doOnError(e -> logError("LOG Primary service error", e))
                .onErrorResume(error1 -> secondaryUserService(userId)
                        .doOnError(e2 -> logError("LOG Secondary service error", e2))
                )
                .onErrorReturn("Critical system failure: unable to retrieve user data");
    }


    private Mono<String> primaryUserService(String userId) {
        return Mono.fromCallable(()->{
            if (userId.equals("error")){
                throw new RuntimeException("Encountered an error processing user");
            }
            return "principal..."+userId;
        }).doOnNext(a-> System.out.println(a));
    }

    private Mono<String> secondaryUserService(String userId) {
        return Mono.fromCallable(()->{
            if (userId.equals("error")){
                throw new RuntimeException("Encountered an error processing user secundary");
            }
            return "Secundario..."+userId;
        }).doOnNext(a-> System.out.println(a));
    }

    private void logError(String message, Throwable e) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.err.println("Error->"+timestamp + " - " + message + ": " + e.getMessage());
        //registro de error
    }
}
