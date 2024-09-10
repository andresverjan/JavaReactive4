package varios;
import reactor.core.publisher.Mono;

public class ErrorHandlingExample {
    public static void main(String[] args) {
        Mono<String> mono = Mono.just("data")
                .map(value -> {
                    if (value.equals("data")) {
                        throw new RuntimeException("Error in map");
                    }
                    return value;
                })
                .doOnError(e -> System.out.println("doOnError: " + e.getMessage()))
                .onErrorResume(e -> {
                    System.out.println("onErrorResume: " + e.getMessage());
                    return Mono.just("fallback value from onErrorResume");
                })
                .onErrorReturn("fallback value from onErrorReturn");

        mono.subscribe(System.out::println, e -> System.err.println("Error: " + e.getMessage()));
    }
}
