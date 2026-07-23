package com.example.springboot.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.entity.Movie;
import com.example.springboot.repository.MovieRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EditService {
      private final MovieRepository movieRepository;
	    @Transactional(
	       propagation = Propagation.REQUIRES_NEW,
	       rollbackFor = Exception.class
	    )
	    public void editName(int id) throws Exception {

	        Movie movie = movieRepository.findById(id).get();

	        movie.setOttavailable(false);

	        movieRepository.save(movie);

	        throw new Exception("failed");
	    }
	    @Async
		public void display1() {
			System.out.println("Displaying2");
			try {
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			System.out.println("ClosingDisplaying2");
			
		}
	}
