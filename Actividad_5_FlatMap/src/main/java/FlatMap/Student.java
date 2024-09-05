package FlatMap;

import java.util.List;

@lombok.Builder
@lombok.Data
public class Student {
    private String name;
    private List<Course> courses;
}
