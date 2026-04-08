package com.saas.app_de_comida.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "caja")
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_dia", nullable = false)
    private BigDecimal totalDia;

    @Column(name = "total_efectivo", nullable = false)
    private BigDecimal totalEfectivo;

    @Column(name = "total_nequi", nullable = false)
    private BigDecimal totalNequi;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre", nullable = true)
    private LocalDateTime fechaCierre;

    @Column(name = "abierta", nullable = false)
    private boolean abierta;

    }
