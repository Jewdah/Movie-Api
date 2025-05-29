package com.example.movieApp.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor  // Genera el constructor con todos los parámetros
@NoArgsConstructor
@Data
public class MovieRequest {

    @NotNull(message = "El título de la película es obligatorio")
    @NotBlank(message = "El título de la película no puede estar vacío")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    private String film;

    @NotNull(message = "El género es obligatorio")
    @NotBlank(message = "El género no puede estar vacío")
    @Size(max = 50, message = "El género no puede tener más de 50 caracteres")
    private String genre;

    @NotNull(message = "El estudio es obligatorio")
    @NotBlank(message = "El estudio no puede estar vacío")
    @Size(max = 50, message = "El estudio no puede tener más de 50 caracteres")
    private String studio;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 0, message = "La puntuación debe ser al menos 0")
    @Max(value = 10, message = "La puntuación no puede ser mayor que 10")
    private Integer score;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1888, message = "El año debe ser válido (desde 1888)")
    @Max(value = 2100, message = "El año debe ser válido (hasta 2100)")
    private Integer year;

}

