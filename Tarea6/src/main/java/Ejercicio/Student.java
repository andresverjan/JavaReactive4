package Ejercicio;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Student {
        private String name;
        private List<Course> courses;
}
