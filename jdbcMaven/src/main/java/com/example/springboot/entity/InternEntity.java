package com.example.springboot.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class InternEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	Integer id;
	@Column(name="name")
	String name;
	@Column(name="role")
	String role;
	@Column(name="phone")
	String phone;
	@Column(name="onleave")
	Boolean onleave;
	@Column(name="joindate")
	LocalDate joinDate ;
}
