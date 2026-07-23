//package com.example.springboot.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.annotation.RequestScope;
//
//import com.example.springboot.entity.InternEntity;
//import com.example.springboot.requestDto.internRequestDto;
//import com.example.springboot.responseDto.reponseDto;
//import com.example.springboot.service.internService;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//import java.util.*;
//@RestController
//@RequestMapping("/interns")
//@AllArgsConstructor
//public class interController {
//	
//	private final internService internService;
//	
//	
//	@GetMapping
//	public List<InternEntity> getAll()
//	{
//		return internService.getAll();
//	}
//	
//	public reponseDto create(@RequestBody @Valid internRequestDto internRequestDto) {
//		return internService.create(internRequestDto);
//	}
//	
//	@PutMapping("/{id}")
//	 public reponseDto update(@RequestBody internRequestDto internreq, @PathVariable Integer id,
//			 @RequestParam String name)
//	 {
//		return internService.update(internreq,id);
//		
//	 }
//	
//
//}
