package org.example.crudperson.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.sql.Timestamp;

@Data
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Person {

    @Id
    private Long id;

    @Column(value = "created_date")
    private Timestamp createdDate;

    @Column(value = "name")
    private String name;

    @Column(value = "age")
    private String age;

    @Column(value = "gender")
    private String gender;

    @Column(value = "date_of_birth")
    private Timestamp dateOfBirth;

    @Column(value = "blood_type")
    private String bloodType;

}
