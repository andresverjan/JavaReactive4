package org.cursoReactividad.Exercise6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
public class Course  {

        private String name;
        private List<Module> modules;

    }