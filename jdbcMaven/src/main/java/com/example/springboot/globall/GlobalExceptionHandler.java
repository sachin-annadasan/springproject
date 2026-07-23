package com.example.springboot.globall;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springboot.exception.MovieNotfoundException;
import com.example.springboot.exception.RatingsNotfoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MovieNotfoundException.class)
    public ResponseEntity<?> handleMovieNotFound(
            MovieNotfoundException ex) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(ex.getMessage());
    }


    @ExceptionHandler(RatingsNotfoundException.class)
    public ResponseEntity<?> handleRatingsNotFound(
            RatingsNotfoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

}