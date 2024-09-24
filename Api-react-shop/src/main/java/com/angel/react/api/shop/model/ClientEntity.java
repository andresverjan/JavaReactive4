package com.angel.react.api.shop.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Table(name = "client")
public class ClientEntity {
    @Id
    private Long id;
    @NotNull(message = "The field idPersona is required")
    @NotBlank(message = "The field idPersona is required")
    @NotEmpty(message = "The field idPersona is required")
    @Column("idperson")
    private Long idperson;
    @Column("status")
    private String status;
}
