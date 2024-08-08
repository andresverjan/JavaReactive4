package Persona;

public class Persona {
    private String name;
    private String lastName;

    public Persona(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
    
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
