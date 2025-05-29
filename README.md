# Movie-Api

API RESTful para la gestión de películas, desarrollada con Java y Spring Boot. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre películas, con pruebas unitarias e integración incluidas.

---

## Tecnologías usadas

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- H2 (Base de datos en memoria para pruebas)
- Maven
- Mockito (Pruebas unitarias)
- MockMvc (Pruebas de integración)
- Springdoc OpenAPI (Swagger) para documentación automática

---

## Requisitos

- Java 17 o superior instalado
- Maven instalado
- Git (opcional, para clonar el repositorio)
- Postman (Opcional)

---

## Cómo clonar el proyecto

```bash
git clone https://github.com/Jewdah/Movie-Api.git
cd Movie-Api
```
## Para ejecutar el proyecto

```bash
mvn spring-boot:run
```
## Configuración y acceso a base de datos H2

Para facilitar el desarrollo y pruebas, el proyecto usa una base de datos en memoria H2.

- **URL JDBC:** `jdbc:h2:mem:moviesdb`

- **Consola web H2:**  
  Puedes acceder al panel web para consultar la base de datos en:  
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **Credenciales por defecto:**  
  - Usuario: `sa`  
  - Contraseña: *(vacía)*

> **Nota:** Si abres la consola H2, recuerda configurar el JDBC URL exactamente como `jdbc:h2:mem:moviesdb` y usar el usuario `sa` sin contraseña para conectarte.

---

## Documentación de la API con Swagger UI

Después de ejecutar la aplicación, puedes acceder a la documentación y probar los endpoints desde:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

sta interfaz te permite ver todos los endpoints, sus parámetros y respuestas, y hacer pruebas interactivas.

---

## Endpoints principales

| Método | Ruta                    | Descripción              |
|--------|-------------------------|--------------------------|
| GET    | `/api/movies/{id}`      | Obtener película por ID   |
| GET    | `/api/movies/all/ordered` | Listar películas ordenadas |
| POST   | `/api/movies`           | Crear una nueva película  |
| PUT    | `/api/movies/{id}`      | Actualizar película por ID|
| DELETE | `/api/movies/{id}`      | Eliminar película por ID  |

---

## Pruebas

Para ejecutar todas las pruebas unitarias e integración:

```bash
mvn test


