package com.example.springboot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.entity.UUid;

public interface UUIDrepo extends JpaRepository<UUid,UUID>{

}
