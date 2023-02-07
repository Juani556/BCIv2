package com.bci.ejercicio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

    private Boolean isActive = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Phone> phones;
}
