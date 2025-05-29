package com.example.movieApp.configuration;

import com.example.movieApp.entity.Movie;
import com.example.movieApp.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Componente que se ejecuta al iniciar la aplicación y carga datos iniciales
 * de películas desde un archivo CSV ubicado en recursos, almacenándolos en la base de datos H2.
 *
 * El archivo CSV debe tener el siguiente formato de columnas:
 * ID, Film, Genre, Studio, Score, Year
 *
 * Cada línea representa una película con datos separados por comas.
 *
 * En caso de error durante la carga, se loguean los detalles y la aplicación sigue su ejecución.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final MovieRepository movieRepository;

    public DataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Método que se ejecuta automáticamente al iniciar la aplicación.
     * Lee el archivo "movies.csv" desde recursos y carga los datos en la base.
     *
     * @param args argumentos de línea de comandos (no usados)
     * @throws Exception si ocurre un error crítico durante la lectura o guardado
     */
    @Override
    public void run(String... args) throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/movies.csv")) {
            if (is == null) {
                logger.error("No se encontró el archivo movies.csv en resources");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                String line;
                reader.readLine();
                int count = 0;

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    if (fields.length < 6) {
                        logger.warn("Línea inválida (menos de 6 campos): {}", line);
                        continue;
                    }

                    for (int i = 0; i < fields.length; i++) {
                        fields[i] = fields[i].trim();
                    }

                    try {
                        Movie movie = new Movie();

                        movie.setFilm(fields[1]);
                        movie.setGenre(fields[2]);
                        movie.setStudio(fields[3]);
                        movie.setScore(Integer.parseInt(fields[4]));
                        movie.setReleaseYear(Integer.parseInt(fields[5]));

                        movieRepository.save(movie);
                        count++;
                    } catch (NumberFormatException e) {
                        logger.warn("Error parsing number en línea: {} - {}", line, e.getMessage());
                    }
                }
                logger.info("Carga finalizada, películas cargadas: {}", count);
            }
        } catch (Exception e) {
            logger.error("Error leyendo archivo movies.csv", e);
            throw e;
        }
    }
}
