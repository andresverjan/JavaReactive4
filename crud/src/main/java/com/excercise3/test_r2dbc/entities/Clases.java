package com.excercise3.test_r2dbc.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table("public.clases")
public class Clases {
    @Id
    @Column("id")
    private Long id;
    @Column("nombre")
    private String nombre;
    @Column("tema")
    private String tema;
    @Column("horas")
    private long horas;
}
