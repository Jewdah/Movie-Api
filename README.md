# Movie-Api

API RESTful para la gestión de películas, desarrollada con Java y Spring Boot. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre películas, con pruebas unitarias e integración incluidas.

---

## Tecnologías usadas

- 🟢 **Java 17**  
- 🌱 **Spring Boot 3.5.0**  
- 💾 **Spring Data JPA**  
- 🛠️ **H2 (Base de datos en memoria para pruebas)**  
- 📦 **Maven**  
- 🧪 **Mockito** (Pruebas unitarias)  
- ⚙️ **MockMvc** (Pruebas de integración)  
- 📖 **Springdoc OpenAPI (Swagger)** para documentación automática  

---

## Requisitos

- ☕ **Java 17** o superior  
- 📦 **Maven**  
- 🐙 **Git** (opcional, para clonar el repositorio)  
- 🔌 **Postman** (opcional, para probar endpoints)  
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
## 🗄️ Configuración y acceso a base de datos H2

Este proyecto usa una base de datos en memoria H2 para facilitar el desarrollo y las pruebas.

- **URL JDBC:**  
  `jdbc:h2:mem:moviesdb`

- **Acceso a la consola H2 (interfaz web):**  
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **Credenciales por defecto:**  
  - Usuario: `sa`  
  - Contraseña: *(vacía)*

> ⚠️ Recuerda configurar el JDBC URL exactamente como arriba cuando accedas a la consola.

---

## 📚 Documentación interactiva con Swagger UI

Puedes explorar y probar todos los endpoints con Swagger UI en:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Una manera sencilla e intuitiva de explorar la API y realizar pruebas rápidas, o bien, 
puedes consumir los endpoints usando Postman para mayor flexibilidad.
---

## 🔍 Endpoints principales

| Método | Ruta                     | Descripción               |
|--------|--------------------------|---------------------------|
| 🔹 GET | `/api/movies/{id}`       | Obtener película por ID    |
| 🔹 GET | `/api/movies/all/ordered`| Listar películas ordenadas |
| 🔹 POST| `/api/movies`            | Crear una nueva película   |
| 🔹 PUT | `/api/movies/{id}`       | Actualizar película por ID |
| 🔹 DELETE | `/api/movies/{id}`     | Eliminar película por ID   |

---

## 🧪 Pruebas

Para ejecutar todas las pruebas unitarias e integración:

```bash
mvn test


