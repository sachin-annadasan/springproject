package com.example.springboot.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UUid {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
}
