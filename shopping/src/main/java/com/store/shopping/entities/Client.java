package com.store.shopping.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Client {
    private String id;
    private String name;
    private String lastname;
    private String address;
    private String telephone;
    private LocalDate dateOfBirth;

}
