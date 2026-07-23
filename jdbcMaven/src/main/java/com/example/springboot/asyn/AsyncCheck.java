package com.example.springboot.asyn;

import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.entity.UUid;
import com.example.springboot.repository.UUIDrepo;
import com.example.springboot.service.EditService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AsyncCheck {
	EditService editservice;
	UUIDrepo drepo;
    @GetMapping("/async/check")
    public void display()
    {
    
    	UUid uuid=new UUid();
    	uuid.setName("sachin");
    	drepo.save(uuid);
    	System.out.println("Displaying1");
    	editservice.display1();
    	System.out.println("closingDisplaying1");
    }

}