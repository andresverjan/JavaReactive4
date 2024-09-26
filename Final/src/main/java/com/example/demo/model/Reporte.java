package com.example.demo.model;

import com.example.demo.service.ProductoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Table("producto")
public class Reporte {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Producto> productos;  // Lista de productos vendidos/comprados
    private double total;  // Total de ventas o compras en el periodo

    // Constructor, getters y setters
    public Reporte(LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Producto> productos, double total) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.productos = productos;
        this.total = total;
    }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public List<Producto> getProductos() { return productos; }
    public double getTotal() { return total; }

    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    public void setTotal(double total) { this.total = total; }
}
