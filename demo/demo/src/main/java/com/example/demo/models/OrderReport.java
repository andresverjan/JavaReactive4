package com.example.demo.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderReport {
 private int orderNumber;
 private List<Orders> productOrderList;
 private double total;
}
