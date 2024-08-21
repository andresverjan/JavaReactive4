package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int random10 = (int) (Math.random() * 30);
        Mono<Integer> mono10 = Mono.just(random10);
        mono10.subscribe(number->{
            System.out.println("Raiz cuadrada para numeros mayores a 10, su numero es: "+number);
            if(number>10){
                System.out.println("la raiz cuadrada es: "+Math.sqrt(number));
            }
            else{
                System.out.println("No es un numero mayor a 10");
            }
        });
        int random20 = (int) (Math.random() * 60);
        Mono<Integer> mono20 = Mono.just(random20);
        mono20.subscribe(number->{
            System.out.println("Raiz cuadrada para numeros mayores a 20, su numero es: "+number);
            if(number>20){
                System.out.println("la raiz cuadrada es: "+Math.sqrt(number));
            }
            else{
                System.out.println("No es un numero mayor a 20");
            }
        });
        Flux<String> flux = Flux.just("Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Paraguay");
        System.out.println("Subscribe de paises con C");
        flux.subscribe(
                country->{
                    char letter = country.charAt(0);
                    if(letter=='C'|| letter=='c'){
                        System.out.println(country);
                    }
                }
        );
        System.out.println("Subscribe vocales de paises tamaño mayor a 5");
        flux.subscribe(
                country->{
                    if(country.length()>5){
                        String vocals="";
                        for(int i=0;i<country.length();i++){
                            char letter=country.charAt(i);
                            if(letter=='a'||letter=='e'||letter=='i'||letter=='o'||letter=='u'){
                                vocals=vocals+letter;
                            }
                            if(letter=='A'||letter=='E'||letter=='I'||letter=='O'||letter=='U'){
                                vocals=vocals+letter;
                            }
                        }
                        System.out.println(vocals);
                    }
                }
        );
    }
}