package org.example.actividadfinal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    @Column(value = "id_usuario")
    private Long idUser;

    @Column(value = "nombre")
    private String name;

    @Column(value = "tipo_identificacion")
    private String typeIdentification;

    @Column(value = "numero_identificacion")
    private String numberIdentification;

    @Column(value = "tipo")
    private String type;


}
