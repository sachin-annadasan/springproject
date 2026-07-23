package com.example.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.springboot.entity.EntryDateEntity;
public interface EntryDateRepo extends JpaRepository<EntryDateEntity, Integer>,
JpaSpecificationExecutor<EntryDateEntity>{
	

}
