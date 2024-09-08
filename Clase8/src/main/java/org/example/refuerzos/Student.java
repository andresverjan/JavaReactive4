package org.example.refuerzos;

import java.util.List;

class Student {
    private String name;
    private List<Course> courses;

    public Student(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", \ncourses=" + courses.toString() +
                '}';
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}