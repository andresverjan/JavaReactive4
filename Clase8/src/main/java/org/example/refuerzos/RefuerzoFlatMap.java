package org.example.refuerzos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RefuerzoFlatMap {
    public static void main(String[] args) {
        List<List<String>> listofListOfCities= Arrays.asList(Arrays.asList("Delhi","Mumbai"),
                Arrays.asList("Beijing","Shanghai","Tianjin"),
                Arrays.asList("Kathmandu","Lalitpur"),
                Arrays.asList("Thimphu","Phuntsholing"));

        List<String> listOfCitiesUppercase=listofListOfCities.stream()
                .flatMap(citiesByCountries -> citiesByCountries.stream())
                .filter(s -> s.startsWith("T"))
                .collect(Collectors.toList());

        listOfCitiesUppercase.forEach(System.out::println);
    }



}
