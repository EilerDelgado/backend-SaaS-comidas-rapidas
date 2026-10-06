package com.saas.app_de_comida.model;

import com.saas.app_de_comida.model.enums.EstadoPedido;
import com.saas.app_de_comida.model.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(generator = "prefixed-code")
    @org.hibernate.annotations.GenericGenerator(
        name = "prefixed-code",
        strategy = "com.saas.app_de_comida.util.PrefixedCodeGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(name = "prefix", value = "PED")
        }
    )
    @Column(name = "codigo", nullable = false, unique = true, updatable = false)
    private String codigo;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "metodo_pago", nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

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

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}
