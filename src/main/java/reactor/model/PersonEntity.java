package reactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@ToString
@Table("persona")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonEntity {
    @Id
    @Column("id")
    private Integer id;
    @Column("nombre")
    private String name;
    @Column("edad")
    private Integer age;
    @Column("genero")
    private String gender;
    @Column("fecha_nacimiento")
    private LocalDate birthdate;
    @Column("rh")
    private String booldType;

}
