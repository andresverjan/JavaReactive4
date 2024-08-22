package com.example.demo.drivenadapter.r2dbc.person;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder(toBuilder = true)
//@NoArgsConstructor
//@AllArgsConstructor
@Table("tbl_vnt_person")
public class PersonData {
}
