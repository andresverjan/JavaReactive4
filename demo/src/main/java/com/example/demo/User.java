package com.example.demo;

public class User {
    public User(String id, String name, String lastname, String description) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.description = description;
    }

    String id;
    String name;
    String lastname;
    String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
