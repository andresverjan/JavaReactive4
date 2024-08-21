package org.cursoReactividad.Exercise3;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Test Mono");
        System.out.println("---------------------------------------------------------------------------");
        givenMonoPublisher_whenSuscribeThenReturnSingleValue();
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Test Flux");
        System.out.println("---------------------------------------------------------------------------");
        givenFluxPublisher();

    }
    public static void givenMonoPublisher_whenSuscribeThenReturnSingleValue(){
        Mono<Integer> mono = Mono.just( (int) (Math.random() * 100 + 1));
        System.out.println("Square Root Greater Than 10");
        mono.subscribe(element -> {
            if (element > 10) {
                System.out.println("number (" + element + ") = " + Math.sqrt(element));
            } else {
                System.out.println("the number is less than 10");
            }
        });
        System.out.println("Square Root Greater Than 20");
        mono.subscribe(element -> {
            if (element > 20) {
                System.out.println("number (" + element + ") = " + Math.sqrt(element));
            } else {
                System.out.println("the number is less than 20");
            }
        }       );



    }

    public static void givenFluxPublisher(){
        Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");
        System.out.println("Country start with C");
        flux.subscribe(
                element -> {
                    if (element.toUpperCase().charAt(0)=='C'){
                        System.out.println(element);
                    }
                });
        System.out.println("Vowels of each country");
        flux.subscribe(
                element->{
                    if (element.length()>5){
                        StringBuilder vowels= new StringBuilder();
                        for(int i=0;i<element.length();i++){
                            char letter= element.toLowerCase().charAt(i);
                            if (letter=='a'|| letter=='e' || letter=='i'|| letter=='o'|| letter=='u'){
                                vowels.append(letter);
                            }
                        }
                        System.out.println("The country is: "+element+ " and the vowels are "+ vowels );
                    }
                }
        );
    }

}
