package org.actividad_1;

import java.util.function.Function;

public class Main {

    public static Person concatName(Person person){
        String name = person.getName() + " " + person.getLastName();
        String lastname = person.getLastName() + " " + person.getName();
        return new Person(name, lastname);
    }

    public static Person processPerson(Person person, Function<Person, Person> function){
        return function.apply(person);
    }

    public static void main(String[] args) {
        Person person = new Person("Camilo","Naranjo");

        Person processedPerson = processPerson(person, Main::concatName);

        System.out.println("Name: " + processedPerson.getName());
        System.out.println("LastName: " + processedPerson.getLastName());
    }
}