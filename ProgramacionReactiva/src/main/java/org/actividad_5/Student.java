package org.actividad_5;


import java.util.List;

class Student {
    private String name;

    public Student(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    private List<Course> courses;
}


