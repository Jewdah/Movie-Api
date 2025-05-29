package com.example.movieApp.dto.response;

import com.example.movieApp.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Integer id;
    private String film;
    private String genre;
    private String studio;
    private Integer score;
    private Integer year;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.film = movie.getFilm();
        this.genre = movie.getGenre();
        this.studio = movie.getStudio();
        this.score = movie.getScore();
        this.year = movie.getReleaseYear();
    }
}
