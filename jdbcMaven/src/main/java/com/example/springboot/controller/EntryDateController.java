package com.example.springboot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.entity.EntryDateEntity;
import com.example.springboot.service.EntryDateService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/entry")
@AllArgsConstructor
public class EntryDateController {
	private final EntryDateService dateService;
   @GetMapping
   public List<EntryDateEntity> seespecificrange(@RequestParam   @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date1,@RequestParam   @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date2)
   {
	   System.out.println(date1);
	   System.out.println(date2);
	   return dateService.seespecificrange(date1,date2);
   }
}
