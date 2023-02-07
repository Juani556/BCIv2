package com.bci.ejercicio.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
public class Phone {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Long number;

    private Integer cityCode;

    private String countryCode;

    @ManyToOne
    private User user;
}
