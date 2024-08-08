
public class Person {
    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public String toString() {
        return "Nombre: " + name + " Apellido: " + lastname;
    }

    String name;
    String lastname;
}
