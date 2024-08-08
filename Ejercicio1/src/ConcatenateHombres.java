import java.util.function.Function;

public class ConcatenateHombres {
    public static Person concatenate(Person person){
        String newName = person.getName() + " " + person.getLastname();
        String newLastName = person.getLastname() + " " + person.getName();
        return new Person(newName,newLastName);
    }
public static Person applyFunction(Person persona, Function<Person, Person> function){
    return function.apply(persona);
}

public static void main(String[] args) {
    Person nicolas=new Person("Nicolas","Quintero");
    Person newPerson=applyFunction(nicolas, ConcatenateHombres::concatenate);
    System.out.println(newPerson);
}
}
