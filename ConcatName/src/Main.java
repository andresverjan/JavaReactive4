import Persona.Persona;

import java.util.Scanner;
import java.util.function.Function;

public class Main {

    private static Persona concat(Persona persona){
        persona.setConcat(persona.getName() + " " + persona.getLastName());
        return persona;
    }

    private static Persona fnApply(Persona persona, Function<Persona, Persona> fn) {
        return fn.apply(persona);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String username = scanner.next();
        System.out.print("Enter your last name: ");
        String lastName = scanner.next();
        System.out.println("Your name concat is -> " + fnApply(
                new Persona(username, lastName),
                Main::concat).getConcat());
    }
}

