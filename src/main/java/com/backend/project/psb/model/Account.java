package com.backend.project.psb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @OneToOne
    @JsonManagedReference
    @ToString.Exclude
    private User user;
    @OneToMany
    private List<CreditCard> creditCards;
    private Date creationDate;
    private Double accountBalance;
}
