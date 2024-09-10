package org.cursoReactividad.Exercise6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Student {
    private String name;
    private List<Course> courses;
}



