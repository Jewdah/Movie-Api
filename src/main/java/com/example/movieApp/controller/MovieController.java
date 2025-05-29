package com.example.movieApp.controller;


import com.example.movieApp.dto.request.MovieRequest;
import com.example.movieApp.dto.response.MovieResponse;
import com.example.movieApp.service.interfaces.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Películas", description = "Operaciones CRUD para películas")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Obtener película por ID
     * @param id ID de la película
     * @return MovieResponse con datos de la película o 404 si no existe
     */
    @Operation(summary = "Obtener película por ID",
            description = "Devuelve los datos de una película dado su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Película encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Película no encontrada",
                            content = @Content)
            })
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Integer id) {
        logger.info("Recibida petición GET para película ID: {}", id);
        MovieResponse response = movieService.getMovieById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar películas ordenadas con paginación básica
     * @param total Cantidad máxima de resultados (opcional, default 10)
     * @param order Orden alfabético asc o desc (opcional, default asc)
     * @return Lista de películas
     */
    @Operation(summary = "Listar películas ordenadas",
            description = "Lista las películas ordenadas alfabéticamente con paginación básica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de películas",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieResponse.class)))
            })
    @GetMapping("/all/ordered")
    public ResponseEntity<List<MovieResponse>> getMoviesOrdered(
            @RequestParam(defaultValue = "10") int total,
            @RequestParam(defaultValue = "asc") String order) {
        logger.info("Recibida petición GET para listar películas, total={}, order={}", total, order);
        List<MovieResponse> movies = movieService.getMoviesOrdered(total, order);
        return ResponseEntity.ok(movies);
    }

    /**
     * Crear una nueva película
     * @param movieRequest DTO con datos de la película a crear, validado automáticamente
     * @return MovieResponse con datos de la película creada
     */
    @Operation(summary = "Crear una nueva película",
            description = "Crea una nueva película con los datos proporcionados",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Película creada exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos",
                            content = @Content)
            })
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        logger.info("Recibida petición POST para crear película: {}", movieRequest.getFilm());
        MovieResponse createdMovie = movieService.createMovie(movieRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMovie.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(createdMovie);
    }


    /**
     * Actualizar una película existente por ID
     * @param id ID de la película a actualizar
     * @param movieRequest DTO con datos actualizados, validado automáticamente
     * @return MovieResponse con datos actualizados
     */
    @Operation(summary = "Actualizar una película existente",
            description = "Actualiza los datos de una película dado su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Película actualizada exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovieResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Película no encontrada",
                            content = @Content),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos",
                            content = @Content)
            })
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable int id,
                                                     @Valid @RequestBody MovieRequest movieRequest) {
        logger.info("Recibida petición PUT para actualizar película ID: {}", id);
        MovieResponse updatedMovie = movieService.updateMovieById(movieRequest, id);
        return ResponseEntity.ok(updatedMovie);
    }

    /**
     * Eliminar película por ID
     * @param id ID de la película a eliminar
     * @return 204 No Content si éxito, 404 si no existe
     */
    @Operation(summary = "Eliminar película por ID",
            description = "Elimina una película dado su ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Película eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Película no encontrada",
                            content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        logger.info("Recibida petición DELETE para película ID: {}", id);
        return movieService.deleteMovieById(id);
    }
}
