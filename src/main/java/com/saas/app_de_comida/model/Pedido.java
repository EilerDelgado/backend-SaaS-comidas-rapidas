package com.saas.app_de_comida.model;

import com.saas.app_de_comida.model.enums.MetodoPago;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "metodo_pago", nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id", nullable = true)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "fk_cajero", referencedColumnName = "id", nullable = true)
    private Usuario cajero;

    @Column(name = "nombre_cliente_local", nullable = true)
    private String nombreClienteLocal;

    @ManyToOne
    @JoinColumn(name = "fk_restaurante", referencedColumnName = "id", nullable = false)
    private Restaurante restaurante;
}
