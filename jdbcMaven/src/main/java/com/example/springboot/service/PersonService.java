package com.example.springboot.service;

import org.springframework.stereotype.Service;

import com.example.springboot.entity.Person;
import com.example.springboot.repository.PersonRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

	private PersonRepo personRepo;
	public Person get(int id) {
		
		return personRepo.getByAddress(id);
	}

}
