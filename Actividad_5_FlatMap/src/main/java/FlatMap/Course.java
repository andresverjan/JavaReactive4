package FlatMap;

import java.util.List;

@lombok.Builder
@lombok.Data
public class Course {
    private String name;
    private List<Module> modules;
}
