package com.ravenprojects.uchuva_bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name="current_balance")
    private BigDecimal currentBalance;

    @OneToOne(mappedBy = "account")
    private User user;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfer> outgoingTransfers;

    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfer> incomingTransfers;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private DebitCard debitCard;

}
