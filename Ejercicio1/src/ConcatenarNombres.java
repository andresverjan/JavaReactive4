import java.util.function.Function;

public class ConcatenarNombres {


    public static Persona concatenar(Persona person){
        System.out.println( person.getNombre().concat(person.getApellido()));
        return person;
    }
    public static Persona applyFunction(Persona persona, Function<Persona,Persona> function){
        return function.apply(persona);
    }

    public static void main(String[] args) {
        Persona nicolas=new Persona("Nicolas","Quintero");
        applyFunction(nicolas,ConcatenarNombres::concatenar);

    }
}
