package com.example.springboot.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@Table(name="T_MOVIES")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
   private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="ottavailable")
	private Boolean ottavailable;
	@Column(name="releasedate")
	private LocalDate releasedate;
	@Column(name="ratings")
	private Integer ratings;
	
	@OneToOne
	@JoinColumn(name="h_id")
   private Hero hero;
}
