package com.example.springboot.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.springboot.entity.EntryDateEntity;
import com.example.springboot.repository.EntryDateRepo;
import com.example.springboot.spec.MovieSpecification;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntryDateService {
    private final EntryDateRepo dateRepo;
	public List<EntryDateEntity> seespecificrange(LocalDate date1, LocalDate date2) {
		
		
		Specification<EntryDateEntity> spec=MovieSpecification.range(date1, date2);
		return dateRepo.findAll(spec);
	}
	

}
