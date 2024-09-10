package org.example;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Course {
    private String name;
    private List<Module> modules;
}
