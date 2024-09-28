package com.store.shopping.drivenAdapters.clients.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@Table("client")
public class ClientData {
    @Column("id")
    private String id;
    @Column("name")
    private String name;
    @Column("lastname")
    private String lastname;
    @Column("address")
    private String address;
    @Column("telephone")
    private String telephone;
    @Column("date_of_birth")
    private LocalDate dateOfBirth;
}
