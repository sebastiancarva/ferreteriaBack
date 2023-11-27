package com.ferreteria.Ferreteria.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre",length = 50)
    private String nombre;
    @Column(name = "precio",length = 50)
    private double precio;
    @Column(name = "cantidad",  length = 50, nullable = false)
    private int cantidad;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "cantidad_comprada")
    private int cantidadComprada;
    private double totalaPagar;
}
