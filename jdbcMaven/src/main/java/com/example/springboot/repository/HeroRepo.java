package com.example.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.entity.Hero;

@Repository
public interface HeroRepo extends JpaRepository<Hero, Integer>{

	Optional<Hero> findByName(String name);
}
