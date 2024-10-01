package com.javacourse.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Auditing {
    @Column("created_date")
    private LocalDateTime createdDate;
    @Column("updated_date")
    private LocalDateTime updatedDate;
}
