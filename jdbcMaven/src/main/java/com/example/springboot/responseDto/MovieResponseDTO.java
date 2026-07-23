package com.example.springboot.responseDto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MovieResponseDTO {
private String name;
	
	private Boolean ottavailable;
	
	private LocalDate releasedate;

	private Integer ratings;

}
