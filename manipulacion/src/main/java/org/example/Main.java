package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> doubledNumbers = numbers.stream()
                .peek(num -> System.out.println("Número original: " + num))
                .map(num -> num * 2)
                .peek(doubledNum -> System.out.println("Número doblado: " + doubledNum))
                .map(num -> num + 1)
                .peek(doubledNum2 -> System.out.println("Número + 1: " + doubledNum2))
                .toList();

        System.out.println("Números doblados: " + doubledNumbers);




        List<String> nombres = Arrays.asList("Juan", "Ana", "Carlos", "María");

        nombres.stream()
                .sorted() // Ordena en orden natural (alfabético en este caso)
                .forEach(System.out::println);


    }
}