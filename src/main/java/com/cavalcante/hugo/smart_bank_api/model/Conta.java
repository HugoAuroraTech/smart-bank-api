package com.cavalcante.hugo.smart_bank_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Conta")
@Table(name = "contas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroConta;

    @Column(nullable = false, length = 10)
    private String agencia;

    @Column(nullable = false, precision = 19, scale = 2) // 1. Precisão para valores monetários
    private BigDecimal saldo;


    @ManyToOne(fetch = FetchType.LAZY) // 3. Estratégia de busca
    @JoinColumn(name = "cliente_id", nullable = false) // 4. Chave estrangeira
    private Cliente cliente;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

}