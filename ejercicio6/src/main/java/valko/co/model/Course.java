package valko.co.model;

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

    public List<Module> getModules() {
        return modules;
    }
}
