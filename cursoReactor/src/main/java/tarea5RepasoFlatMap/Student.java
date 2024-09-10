package tarea5RepasoFlatMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Student {
    private String name;
    private List<Course> courses;
}