package tarea1;

public class Tarea1 {

    public static String concatenar(Persona persona){
        return persona.getNombre() + " " + persona.getApellido();
    }

    public static void main(String[] args){
        Persona persona1 = new Persona("Sebastian", "Chavarria");
        System.out.println(concatenar(persona1));
    }
}
