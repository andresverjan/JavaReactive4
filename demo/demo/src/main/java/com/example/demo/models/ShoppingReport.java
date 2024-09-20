package com.example.demo.models;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingReport {
    private int orderNumber;
    private List<Shopping> ShoppingList;
}
