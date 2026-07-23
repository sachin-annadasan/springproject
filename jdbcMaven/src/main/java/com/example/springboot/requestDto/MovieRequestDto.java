package com.example.springboot.requestDto;
import java.time.LocalDate;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieRequestDto {
	@NotBlank(message="movie name is required")
	private String name;
	private Boolean ottavailable;
	@NotNull(message = "Release date is required")
	private LocalDate releasedate;
	@NotNull(message = "ratings is required")
	@Min(value=1, message = "Rating must be at least 1")
	@Max(value=10, message = "Rating should not exceed 10")
	private Integer ratings;
	
}
