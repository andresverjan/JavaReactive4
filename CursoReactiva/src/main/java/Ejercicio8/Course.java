package Ejercicio8;

import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;

import java.util.List;


class Course {
    private String name;
    private List<Module> modules;

    public Course(String name, List<Module> modules) {
        this.name = name;
        this.modules = modules;
    }

    public List<Module> getModules() {
        return modules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}