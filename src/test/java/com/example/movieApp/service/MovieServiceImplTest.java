package com.example.movieApp.service;

import com.example.movieApp.dto.request.MovieRequest;
import com.example.movieApp.dto.response.MovieResponse;
import com.example.movieApp.entity.Movie;
import com.example.movieApp.exception.listexception.NotFoundException;
import com.example.movieApp.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    /**
     * Verifica que el método createMovie cree una película correctamente
     * cuando se pasa un MovieRequest válido. Se asegura que la respuesta
     * no sea nula, que los campos sean los esperados y que se haya llamado
     * una vez al método save del repositorio.
     */
    @Test
    void testCreateMovie() {
        MovieRequest request = new MovieRequest("Inception", "Sci-Fi", "Warner Bros", 8, 2010);
        Movie movie = new Movie(1, "Inception", "Sci-Fi", "Warner Bros", 8, 2010);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieResponse response = movieService.createMovie(request);

        assertNotNull(response);
        assertEquals("Inception", response.getFilm());
        assertEquals(8, response.getScore());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    /**
     * Verifica que el método updateMovieById actualice correctamente una película
     * existente con nuevos datos. Se comprueba que el repositorio busque y guarde
     * la película y que los valores actualizados sean los esperados.
     */

    @Test
    void testUpdateMovieById() {
        int movieId = 1;
        MovieRequest request = new MovieRequest("The Dark Knight", "Action", "Warner Bros", 9, 2008);
        Movie movie = new Movie(movieId, "Inception", "Sci-Fi", "Warner Bros", 8, 2010);
        Movie updatedMovie = new Movie(movieId, "The Dark Knight", "Action", "Warner Bros", 9, 2008);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        MovieResponse response = movieService.updateMovieById(request, movieId);

        assertNotNull(response);
        assertEquals("The Dark Knight", response.getFilm());
        assertEquals(9, response.getScore());
        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    /**
     * Valida que al intentar actualizar una película que no existe, el método
     * lance una excepción NotFoundException con el mensaje adecuado.
     */
    @Test
    void testUpdateMovieByIdNotFound() {
        int movieId = 1;
        MovieRequest request = new MovieRequest("The Dark Knight", "Action", "Warner Bros", 9, 2008);

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            movieService.updateMovieById(request, movieId);
        });

        assertEquals("Película no encontrada con id: " + movieId, exception.getMessage());
    }

    /**
     * Verifica que getMovieById retorne correctamente una película existente.
     * Se asegura que la respuesta no sea nula, que los datos coincidan con la película
     * buscada y que el repositorio haya sido consultado una vez.
     */
    @Test
    void testGetMovieById() {
        int movieId = 1;
        Movie movie = new Movie(movieId, "Inception", "Sci-Fi", "Warner Bros", 8, 2010);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        MovieResponse response = movieService.getMovieById(movieId);

        assertNotNull(response);
        assertEquals("Inception", response.getFilm());
        assertEquals(8, response.getScore());
        verify(movieRepository, times(1)).findById(movieId);
    }

    /**
     * Valida que getMovieById lance una NotFoundException cuando se busca
     * una película por un ID que no existe, y que el mensaje de error sea correcto.
     */
    @Test
    void testGetMovieByIdNotFound() {
        int movieId = 1;

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            movieService.getMovieById(movieId);
        });

        assertEquals("Película no encontrada con id: " + movieId, exception.getMessage());
    }

    /**
     * Comprueba que getMoviesOrdered retorne una lista con películas ordenadas
     * según el parámetro dado, y que la lista tenga la cantidad esperada.
     * Además valida que el repositorio haya sido llamado una vez.
     */
    @Test
    void testGetMoviesOrdered() {
        int total = 5;
        String order = "asc";
        Movie movie1 = new Movie(1, "Inception", "Sci-Fi", "Warner Bros", 8, 2010);
        Movie movie2 = new Movie(2, "The Dark Knight", "Action", "Warner Bros", 9, 2008);
        List<Movie> movies = Arrays.asList(movie1, movie2);

        when(movieRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(movies));

        List<MovieResponse> response = movieService.getMoviesOrdered(total, order);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Inception", response.get(0).getFilm());
        assertEquals("The Dark Knight", response.get(1).getFilm());
        verify(movieRepository, times(1)).findAll(any(PageRequest.class));
    }

    /**
     * Verifica que deleteMovieById elimine correctamente una película
     * cuando existe, y que retorne un ResponseEntity con código 204 No Content.
     * También valida que el método deleteById del repositorio haya sido invocado.
     */
    @Test
    void testDeleteMovieById() {
        int movieId = 1;

        when(movieRepository.existsById(movieId)).thenReturn(true);

        ResponseEntity<Void> response = movieService.deleteMovieById(movieId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(movieRepository, times(1)).deleteById(movieId);
    }


    /**
     * Valida que deleteMovieById lance una NotFoundException cuando se intenta
     * eliminar una película que no existe, con el mensaje de error correspondiente.
     */
    @Test
    void testDeleteMovieByIdNotFound() {
        int movieId = 1;

        when(movieRepository.existsById(movieId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            movieService.deleteMovieById(movieId);
        });

        assertEquals("Película no encontrada con id: " + movieId, exception.getMessage());
    }
}
