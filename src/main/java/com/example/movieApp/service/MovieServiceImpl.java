package com.example.movieApp.service;

import com.example.movieApp.dto.request.MovieRequest;
import com.example.movieApp.dto.response.MovieResponse;
import com.example.movieApp.entity.Movie;
import com.example.movieApp.exception.listexception.NotFoundException;
import com.example.movieApp.repository.MovieRepository;
import com.example.movieApp.service.interfaces.MovieService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Crea una nueva película.
     * Se asume que el ID se genera automáticamente en la base de datos.
     * La validación de campos (no nulos, rangos, etc.) debe hacerse en MovieRequest con anotaciones y en el controlador.
     *
     * @param request Datos de la película a crear
     * @return Respuesta con la película creada
     */
    @Transactional
    @Override
    public MovieResponse createMovie(MovieRequest request) {
        // No se verifica ID porque se genera automáticamente

        Movie movie = mapToEntity(request);
        Movie savedMovie = movieRepository.save(movie);

        logger.info("Película creada con ID: {}", savedMovie.getId());

        return new MovieResponse(savedMovie);
    }

    /**
     * Actualiza una película existente por ID.
     * Se recomienda tener un campo @Version en la entidad para control de concurrencia optimista.
     *
     * @param request Datos actualizados de la película
     * @param id      ID de la película a actualizar
     * @return Respuesta con la película actualizada
     */
    @Transactional
    @Override
    public MovieResponse updateMovieById(MovieRequest request, int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Película no encontrada con id: " + id));

        updateEntityFromRequest(movie, request);

        Movie updatedMovie = movieRepository.save(movie);

        logger.info("Película actualizada con ID: {}", updatedMovie.getId());

        return new MovieResponse(updatedMovie);
    }

    /**
     * Obtiene una película por su ID.
     *
     * @param id ID de la película
     * @return Respuesta con la película encontrada
     */
    @Override
    public MovieResponse getMovieById(int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Película no encontrada con id: " + id));

        logger.info("Película obtenida con ID: {}", id);

        return new MovieResponse(movie);
    }

    /**
     * Obtiene una lista de películas ordenadas y limitadas.
     * La paginación y orden se hacen a nivel de base de datos para optimizar rendimiento.
     *
     * @param total Número máximo de películas a obtener
     * @param order Orden ("asc" o "desc") por nombre de película
     * @return Lista de respuestas con las películas
     */
    @Override
    public List<MovieResponse> getMoviesOrdered(int total, String order) {
        Sort sort = Sort.by("film");
        if ("desc".equalsIgnoreCase(order)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        int limit = total > 0 ? total : Integer.MAX_VALUE;

        PageRequest pageRequest = PageRequest.of(0, limit, sort);

        List<Movie> movies = movieRepository.findAll(pageRequest).getContent();

        logger.info("Obtenidas {} películas ordenadas {}", movies.size(), order);

        return movies.stream()
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Elimina una película por su ID.
     * Retorna ResponseEntity<Void> con código 204 No Content para ser más RESTful.
     *
     * @param id ID de la película a eliminar
     * @return ResponseEntity sin contenido
     */
    @Override
    public ResponseEntity<Void> deleteMovieById(int id) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Película no encontrada con id: " + id);
        }
        movieRepository.deleteById(id);

        logger.info("Película eliminada con ID: {}", id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza los campos editables de una entidad Movie desde un MovieRequest.
     * Evita duplicación de código.
     *
     * @param movie   Entidad a actualizar
     * @param request Datos nuevos
     */
    private void updateEntityFromRequest(Movie movie, MovieRequest request) {
        movie.setFilm(request.getFilm());
        movie.setGenre(request.getGenre());
        movie.setStudio(request.getStudio());
        movie.setScore(request.getScore());
        movie.setReleaseYear(request.getYear());
    }

    /**
     * Mapea un MovieRequest a una entidad Movie.
     * En creación no se asigna ID, ya que es generado automáticamente.
     *
     * @param request Datos de la película
     * @return Entidad Movie
     */
    private Movie mapToEntity(MovieRequest request) {
        Movie movie = new Movie();
        // No seteamos ID aquí si usamos @GeneratedValue
        movie.setFilm(request.getFilm());
        movie.setGenre(request.getGenre());
        movie.setStudio(request.getStudio());
        movie.setScore(request.getScore());
        movie.setReleaseYear(request.getYear());
        return movie;
    }
}
