package com.example.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springboot.entity.Person;
@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {
    @Query(value = "Select p.* from t_person p inner join t_address t on p.id=t.address_id"
    		+ " where t.address_id=:iid",nativeQuery = true)
    Person getByAdress(@Param("iid") int adressid); 
	@Query(value = "SELECT * FROM t_person WHERE address_id = :id", nativeQuery = true)
	Person getByAddress(@Param("id") int id);
}
