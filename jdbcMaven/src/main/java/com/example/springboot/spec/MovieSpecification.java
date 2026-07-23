package com.example.springboot.spec;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.springboot.entity.EntryDateEntity;
import com.example.springboot.entity.Movie;

public class MovieSpecification {
	
	public static Specification<Movie> hasName(String name)
	{
		return (root, query, cb) ->cb.equal(root.get("name"), name); 
	}
	public static Specification<Movie> hasRatings(Integer ratings)
	{
		return (root, query, cb) ->cb.equal(root.get("ratings"), ratings); 
	}
	public static Specification<Movie> hasOttAvailable(Boolean avai)
	{
		return (root, query, cb) ->cb.equal(root.get("ottavailable"), avai); 
	}
	public static Specification<Movie> hasNameLike(String name)
	{
		return (root,query,cb)->cb.like(cb.lower(root.get("name")),name.toLowerCase()+"%");
	}
	public static Specification<Movie> greater(Integer ratings)
	{
		return (root,query,cb)->cb.greaterThan(root.get("ratings"), ratings);
	}
	public static Specification<Movie> ratingBetween(Integer min, Integer max) {
	    return (root, query, cb) ->
	        cb.between(root.get("ratings"), min, max);
	}
	public static Specification<Movie> orderByRatingDesc() {
	
	    return (root, query, cb) -> {
	        query.orderBy(cb.desc(root.get("ratings")));
	        return cb.conjunction();
	    };
	}
	public static Specification<EntryDateEntity> range(LocalDate date1,LocalDate date2)
	{
		return (root,query,cb)->
		cb.between(root.get("created_at"), date1, date2);
	}
	}


	

