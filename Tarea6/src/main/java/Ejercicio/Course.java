package Ejercicio;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor

public class Course {

    private String name;
    private List<Module> modules;
}
