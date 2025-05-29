package com.example.movieApp.service.interfaces;

import com.example.movieApp.dto.request.MovieRequest;
import com.example.movieApp.dto.response.MovieResponse;
import com.example.movieApp.dto.response.ResponseMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovieService {

    MovieResponse createMovie(MovieRequest request);

    MovieResponse updateMovieById(MovieRequest request,int id);

    MovieResponse getMovieById(int id);

    List<MovieResponse> getMoviesOrdered(int total, String order);

    ResponseEntity<Void> deleteMovieById(int id);
}
