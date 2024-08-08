import customers.Person;

import java.util.function.Function;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Person concatening(Person person){
        String catName= person.getName()+" "+person.getLastName();
        return new Person(catName,catName);
    }
    public static Person personConcat(Person person, Function<Person,Person> functionConcat){
        return functionConcat.apply(person);
    }
    public static void main(String[] args) {
        Person person1= new Person("Carlos", "Guzman");
        Person person2=personConcat(person1,Main::concatening);
        System.out.println(person2.getName());
        System.out.println(person2.getLastName());
    }
}