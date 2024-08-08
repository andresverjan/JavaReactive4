package com.bancolombia.co;


import java.util.function.Function;

public class Persona {

    public Persona(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    private String name;
    private String lastname;



    public static Persona concatenate(Persona persona){
        String newName = persona.getName()+persona.getLastname();
        String newLastname = persona.getLastname()+persona.getName();
        return new Persona(newName,newLastname);
    }

    public static Persona orderSupFun (Persona persona, Function<Persona,Persona>fun){
        return fun.apply(persona);
    }

    public static void main(String[] args){
        Persona p = new Persona("Luis","Hurtado");
        Persona np = orderSupFun(p,Persona::concatenate);
        System.out.println("First: "+p);
        System.out.println("Second: "+np);
    }
}
