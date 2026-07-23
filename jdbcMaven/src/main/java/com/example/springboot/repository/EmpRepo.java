package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.entity.Department;
import com.example.springboot.entity.Employee;

public interface EmpRepo extends JpaRepository<Employee, Integer> {

	List<Employee> findByDepartment(Department department);

}
