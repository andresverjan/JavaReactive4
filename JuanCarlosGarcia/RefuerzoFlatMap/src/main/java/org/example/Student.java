package org.example;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Student {
    private String name;
    private List<Course> courses;
}
