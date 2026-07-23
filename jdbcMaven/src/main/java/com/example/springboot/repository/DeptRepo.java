package com.example.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot.entity.Department;
public interface DeptRepo  extends JpaRepository<Department, Integer>{

}
