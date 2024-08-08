import java.util.function.Function;

public class ConcatenarNombres {
    public static Persona concatenar(Persona person){
        String newName = person.getNombre() + " " + person.getApellido();
        String newApellido = person.getApellido() + " " + person.getNombre();
        Persona newPerson= new Persona(newName,newApellido);
        return newPerson;
    }
public static Persona applyFunction(Persona persona, Function<Persona,Persona> function){
    return function.apply(persona);
}

public static void main(String[] args) {
    Persona nicolas=new Persona("Nicolas","Quintero");
    Persona newPerson=applyFunction(nicolas, ConcatenarNombres::concatenar);
    System.out.println(newPerson);

}
}
