# User Management API

 Spring Boot REST API permite el mantenimiento de usuarios usando UUID-based token para autenticacion. Incluye Swagger, validacion, H2 database.

---

##  Tecnologias

- Java 17
- Spring Boot 3.5.3
- Spring Web, Security, JPA, Validation
- H2 in-memory database
- Lombok
- Swagger UI (`springdoc-openapi-starter`)
- JUnit + Mockito for unit tests

---

## Features

- Registro de Usuario
- Generacion de Token basados en UUID
- Proteccion de enpoints usando UUID tokens
- Encriptacion de Contraseñas usando BCrypt  
-  Swagger UI 
-  H2 in-memory database
-  Custom exception handling

---

##  Autenticacion

La API usa UUID token, debe ser enviada por `Authorization` header:

Los token son retornados despues de registrar un usuario y son requeridos en los endpoints protegidos como `/info`

---

## Como Testear la API

###  Usando Swagger UI

1. Iniciar la app:
   ```bash
   mvn spring-boot:run

2. Abrir el browser y dirigirse a: http://localhost:8080/swagger-ui.html

3. Probar los endpoints desde la UI, primero registrar un usuario
   * POST /api/users/register 
4. Copiar el token retornado en la respuesta 
5. Click en el boton de Authorize y pegar el token: Bearer {your-uuid-token}
6. Finalmente probar los endpoints que requieren el token:
    * GET /api/users/info
---

## H2 Database
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- User: sa, Password: (dejar en blanco)

---
## TODO / Mejoras

- Utilizar JWT en vez de UUID

---

## Autor

Walter Barrantes  
correo: wbarrantesrios@gmail.com


