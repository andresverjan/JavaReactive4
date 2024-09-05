package ejemplos;

import tarea1.Persona;

import java.util.TreeSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Ejemplo5Operaciones {

    private static List<Persona> personas = Arrays.asList(
            new Persona("Sebastian", "Chavarria"),
            new Persona("Pedro", "Rodriguez"),
            new Persona("Pablo", "Murillo"),
            new Persona("Mariana", "Gomez"),
            new Persona("Ana", "Cuartas"),
            new Persona("Juana", "De Arco"),
            new Persona("Adriana", "Rios")
    );

    public static void main (String[] args){
        // Accumulate names into a List
        List<String> list = personas.stream().map(Persona::getNombre).collect(Collectors.toList());
        System.out.println(list);

        // Accumulate names into a TreeSet
        Set<String> set = personas.stream().map(Persona::getNombre).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(set);

        // Convert elements to strings and concatenate them, separated by commas
        String joined = personas.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.out.println(joined);

        // Compute sum of salaries of employee
        int total = personas.stream()
                .collect(Collectors.summingInt(Persona::getSalary));
        System.out.printf("El  salario de las %d es %d", personas.size(), total );

        // Group employees by department
        /*Map<Department, List<Employee>> byDept
                = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));*/


        //EJEMPLO 2
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> doubledNumbers = numbers.stream()
                .peek(num -> System.out.println("Número original: " + num))
                .map(num -> num * 2)
                .peek(doubledNum -> System.out.println("Número doblado: " + doubledNum))
                .map(num -> num + 1)
                .peek(doubledNum2 -> System.out.println("Número + 1: " + doubledNum2))
                .toList();
        System.out.println("Números doblados: " + doubledNumbers);

        //EJEMPLO 3
        List<String> nombres = Arrays.asList("Juan", "Ana", "Carlos", "María");
        nombres.stream()
                .sorted() // Ordena en orden natural (alfabético en este caso)
                .forEach(System.out::println);
    }

}
