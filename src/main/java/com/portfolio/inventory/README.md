# Inventory API

API REST para la gestión de inventario de productos. Proyecto desarrollado como parte de una arquitectura basada en microservicios, implementando el patrón de diseño de 3 capas.

## Stack tecnológico
- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 Database (In-memory)
- Maven

## Arquitectura
El proyecto sigue una arquitectura estándar MVC (sin vistas):
- `Controller`: Manejo de peticiones HTTP y respuestas REST.
- `Service`: Lógica de negocio y validaciones.
- `Repository`: Interfaz con la base de datos a través de Spring Data JPA.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/products` | Retorna el listado completo de productos. |
| GET | `/api/products/{id}` | Busca un producto específico por su ID. |
| POST | `/api/products` | Registra un nuevo producto en el sistema. |
| PUT | `/api/products/{id}` | Actualiza precio, nombre y stock de un producto. |
| DELETE | `/api/products/{id}` | Elimina el registro del producto. |

## Ejecución local
Para levantar la aplicación en un entorno de desarrollo:

1. Clonar el repositorio.
2. Ejecutar `./mvnw spring-boot:run` en la raíz del proyecto.
3. El servicio estará disponible en el puerto 8080.
4. La consola de H2 se encuentra habilitada en `/h2-console` (URL JDBC: `jdbc:h2:mem:inventorydb`).