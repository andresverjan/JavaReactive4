import Persona.Persona;

import java.util.Scanner;
import java.util.function.Function;

public class Main {

    private static Persona concat(Persona persona){
        String concatName = persona.getName() + " " + persona.getLastName();
        String concatLastName = persona.getName() + " " + persona.getLastName();
        return new Persona(concatName, concatLastName);
    }

    private static Persona fnApplyConcat(Persona persona, Function<Persona, Persona> fn) {
        return fn.apply(persona);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String username = scanner.next();
        System.out.print("Enter your last name: ");
        String lastName = scanner.next();
        Persona newPerson = fnApplyConcat(new Persona(username, lastName), Main::concat);
        System.out.println("Your name concat is -> " + newPerson.getName());
        System.out.println("Your lastName concat is -> " + newPerson.getLastName());
    }
}

