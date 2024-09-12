package org.example.flatmap.course;

import org.example.flatmap.module.Module;

import java.util.List;

public class Course {

    private String name;
    private List<Module> modules;

    public Course(String name, List<Module> modules) {
        this.name = name;
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
