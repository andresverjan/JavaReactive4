package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //System.out.println("Hello world!");

        /*
        // Accumulate names into a List
        List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

        // Accumulate names into a TreeSet
        Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));

        // Convert elements to strings and concatenate them, separated by commas
        String joined = things.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        // Compute sum of salaries of employee
        int total = employees.stream()
                .collect(Collectors.summingInt(Employee::getSalary)));

        // Group employees by department
        Map<Department, List<Employee>> byDept
                = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

         */
        List<String> nombres = Arrays.asList("Juan", "Ana", "Carlos", "María");

        nombres.stream()
                .sorted() // Ordena en orden natural (alfabético en este caso)
                .forEach(System.out::println);

    }
}