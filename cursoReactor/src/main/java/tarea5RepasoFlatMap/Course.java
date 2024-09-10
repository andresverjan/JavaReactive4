package tarea5RepasoFlatMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Course {
    private String name;
    private List<Module> modules;
}
