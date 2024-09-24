package com.angel.react.api.shop.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @NotBlank
    @NotEmpty
    @Column("document")
    private String document;
}
