package Ejercicio8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManejoFlatMap {

    public static void main(String[] args) {
        List<List<String>> listofListOfCities = Arrays.asList(
                Arrays.asList("Delhi", "Mumbai"),
                Arrays.asList("Beijing", "Shanghai", "Tianjin"),
                Arrays.asList("Kathmandu", "Lalitpur"),
                Arrays.asList("Thimphu", "Phuntsholing")
        );

        List<String> listOfCitiesUppercase = listofListOfCities.stream()
                .flatMap(citiesByCountries -> citiesByCountries.stream())
                //.filter(s -> s.startsWith("T")) // Descomentar si quieres filtrar por ciudades que comiencen con "T"
                .collect(Collectors.toList());

        listOfCitiesUppercase.forEach(System.out::println);
    }

}
