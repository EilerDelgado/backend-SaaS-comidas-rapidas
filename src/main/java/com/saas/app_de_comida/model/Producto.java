package com.saas.app_de_comida.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false, unique = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false, unique = false)
    private String descripcion;

    @Column(name = "precio", nullable = false, unique = false)
    private BigDecimal precio;

    @Column(name = "disponibilidad", nullable = false, unique = false)
    private boolean disponibilidad;

    @ManyToOne
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id")
    private Categoria categoriaProducto;
}
