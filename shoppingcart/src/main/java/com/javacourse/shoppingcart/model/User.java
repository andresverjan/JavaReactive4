package com.javacourse.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("TBL_USER")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends Auditing {
    @Id
    private Long id;

    @Column("name")
    private String name;

    @Column("document_number")
    private String documentNumber;

    @Column("email")
    private String email;

    @Column("cellphone")
    private String cellphone;
}
