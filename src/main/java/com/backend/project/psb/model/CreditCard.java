package com.backend.project.psb.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private LocalDate expirationDate;

    private String cvv;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

}
