package com.example.springboot.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name="T_HEROS")
public class Hero {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name="h_id")
	   private Integer h_id;
		@Column(name="name")
		private String name;
		
}
