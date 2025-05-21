package com.ravenprojects.uchuva_bank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.YearMonth;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "debit-cards")
public class DebitCard {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false, unique = true, length = 16, name = "card_number")
    private String cardNumber;

    @Column(nullable = false, name = "card_holder_name")
    private String cardHolderName;

    @Column(nullable = false, name = "expiration_date")
    private YearMonth expirationDate;

    @Column(nullable = false)
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

}
