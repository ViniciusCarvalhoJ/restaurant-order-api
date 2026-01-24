package com.example.pedidosapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email é obrigatório")
    private String email;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    //Criar data automaticamente
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
