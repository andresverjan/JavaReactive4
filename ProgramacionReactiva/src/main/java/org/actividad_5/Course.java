package org.actividad_5;

import java.util.List;

class Course {
    public String getName() {
        return name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public Course(String name, List<Module> modules) {
        this.name = name;
        this.modules = modules;
    }

    private String name;

    private List<Module> modules;

}
