package com.example.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.entity.Address;
import com.example.springboot.entity.Person;
import com.example.springboot.repository.AddressRepo;
import com.example.springboot.repository.PersonRepo;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class OnetoManyManytoOneController {

    private final PersonRepo personRepo;

    private final AddressRepo addressRepo;
//    @PostMapping("/save")
//    public Person savePerson() {
//
//        // Create Address
//        Address address = new Address();
//        address.setHomeaddress("12 MG Road");
//        address.setDistrict("Coimbatore");
//        address.setState("Tamil Nadu");
//
//        // Create Person
//        Person person = new Person();
//        person.setName("ajay");
//        person.setGender("Male");
//        person.setAddress(address);
//
// 
//        Person savedPerson = personRepo.save(person);
//
//     
//        System.out.println(savedPerson);
//
//        return savedPerson;
//    }
    @GetMapping("/fetch/{id}")
    public Person fetchPerson(@PathVariable int id) {

        Person person = personRepo.findById(id).orElseThrow();

        // Optional: initialize lazy relation
//        person.getAddress().getDistrict();

        return person;
    }
    @GetMapping("/fetcha/{id}")
    public Address fetchAdress(@PathVariable int id) {

       Address address = addressRepo.findById(id).orElseThrow();

//        // Optional: initialize lazy relation
//        person.getAddress().getDistrict();

        return address;
    }
}