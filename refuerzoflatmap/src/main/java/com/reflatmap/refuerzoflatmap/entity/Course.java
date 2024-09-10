package com.reflatmap.refuerzoflatmap.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String name;
    private List<Module> modules;
}
