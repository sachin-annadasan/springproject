package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot.entity.Movie;
import com.example.springboot.projection.MovieProjection;

public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {

	List<Movie> findByName(String name);

	List<Movie> findByRatings(Integer ratings, Sort sort);

	List<Movie> findByRatingsAndOttavailable(Integer ratings, Boolean ottavailable);

	List<Movie> findByNameAndRatings(String name, Integer ratings);

	Page<Movie> findByOttavailable(boolean ottavailable, Pageable pageable);

	List<Movie> findByRatingsGreaterThan(Integer rating);

	@Query(value = """
			SELECT t.*
			FROM t_movies t
			JOIN t_heros h ON t.h_id = h.h_id
			WHERE h.h_id = :h_id
			""", nativeQuery = true)
	List<Movie> getMovies(@Param("h_id") int id);

	List<MovieProjection> findAllProjectedBy();

}