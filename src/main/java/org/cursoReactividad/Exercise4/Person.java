package org.cursoReactividad.Exercise4;

public class Person {
    String name;
    String lastname;
    String phone;
    int age;
    String starSign;

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public String getStarSign() {
        return starSign;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStarSign(String starSign) {
        this.starSign = starSign;
    }

    public Person(String name, String lastname, String documentNumber, int age, String starSign) {
        this.name = name;
        this.lastname = lastname;
        this.phone = documentNumber;
        this.age = age;
        this.starSign = starSign;
    }
}
