package com.ravenprojects.uchuva_bank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_document_type", "id_document_number"})
        }
)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 100)
    @NotBlank
    @Column(nullable = false, name="first_name")
    private String firstName;

    @Size(min = 2, max = 100)
    @NotBlank
    @Column(nullable = false, name="last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="id_document_type")
    private IdDocumentType idDocumentType;

    @NotBlank
    @Column(nullable = false, name="id_document_number")
    private String idDocumentNumber;

    @NotBlank
    @Column(nullable = false, name="password_hash")
    private String passwordHash;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @Size(min = 10, max = 10)
    @NotBlank
    @Column(nullable = false)
    private String phone;

    @NotBlank
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }

}
