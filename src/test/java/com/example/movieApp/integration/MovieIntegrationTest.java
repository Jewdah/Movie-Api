package com.example.movieApp.integration;

import com.example.movieApp.dto.request.MovieRequest;
import com.example.movieApp.dto.response.MovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Verifica que el endpoint POST /api/movies
     * permita crear una nueva película con datos válidos.
     * Se espera respuesta con código 201 Created y que el objeto
     * retornado contenga un id numérico positivo y los campos correctos.
     */
    @Test
    void testCreateMovie() throws Exception {
        MovieRequest request = new MovieRequest("Parasite", "Drama", "Barunson E&A", 8, 2019);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(org.hamcrest.Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.film").value("Parasite"))
                .andExpect(jsonPath("$.score").value(8));
    }

    /**
     * Verifica que se pueda obtener una película por su ID
     * usando GET /api/movies/{id}. Primero crea una película,
     * luego la consulta y valida que los datos devueltos coincidan.
     * Se espera código 200 OK y JSON con los campos correctos.
     */
    @Test
    void testGetMovieById() throws Exception {

        MovieRequest request = new MovieRequest("Inception", "Sci-Fi", "Warner Bros", 9, 2010);

        String postResponse = mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        MovieResponse createdMovie = objectMapper.readValue(postResponse, MovieResponse.class);
        int movieId = createdMovie.getId();

        mockMvc.perform(get("/api/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.film").value("Inception"))
                .andExpect(jsonPath("$.id").value(movieId));
    }


    /**
     * Verifica que el endpoint GET /api/movies/all/ordered
     * retorne una lista ordenada de películas con la cantidad solicitada.
     * En este caso, se solicita un total de 3 películas en orden ascendente.
     * Se espera código 200 OK y lista con tamaño 3.
     */
    @Test
    void testListMoviesOrderedAsc() throws Exception {
        mockMvc.perform(get("/api/movies/all/ordered")
                        .param("total", "3")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    /**
     * Verifica el manejo de un ID inválido en GET /api/movies.
     * Solicita una película con un ID que no existe (9999),
     * y espera un código 4xx (cliente error) con un mensaje de error
     * en formato JSON para informar que no se encontró la película.
     */
    @Test
    void testInvalidMovieId() throws Exception {
        mockMvc.perform(get("/api/movies")
                        .param("id", "9999"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").exists());
    }
}
