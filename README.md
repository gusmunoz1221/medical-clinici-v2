# 🏥 Medical Clinic Management System API (v2)

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **Versión Refactorizada:** Evolución completa del sistema legacy `clinic-v1`. Esta versión introduce una arquitectura modular basada en el dominio, optimización de consultas SQL y patrones de diseño modernos.

## 📖 Descripción

Este proyecto es una **RESTful API** robusta diseñada para la gestión integral de una clínica médica. A diferencia de un CRUD tradicional, este sistema implementa lógica de negocio compleja para garantizar la integridad de los datos médicos, la privacidad del paciente y el rendimiento en grandes volúmenes de datos.

El núcleo del sistema ha sido reescrito para desacoplar las capas de persistencia y presentación, utilizando **Records (DTOs)** inmutables y una estrategia de **Mapeo Manual** para un control total sobre la transferencia de datos.


## 🚀 Características Clave (v2)

### 🏗 Arquitectura & Diseño
* **Domain-Driven Package Structure:** Organización modular (`/doctor`, `/patient`, `/person`) en lugar de capas horizontales, facilitando la escalabilidad y mantenimiento.
* **DTO Pattern con Java Records:** Uso de objetos inmutables para la transferencia de datos, separando los contratos de API de las entidades de base de datos.
* **Custom Mappers:** Implementación de mappers manuales para transformaciones complejas y control granular (sin magia negra de librerías externas).

### ⚡ Rendimiento & Optimización
* **Consultas Optimizadas:** Uso de `@EntityGraph` y `JOIN FETCH` en JPQL para resolver el problema N+1 en relaciones JPA.
* **Respuestas Adaptativas:** Diferenciación entre `ListResponse` (ligero para tablas) y `DetailResponse` (completo para perfiles), reduciendo la carga de red.

### 🛡 Seguridad & Integridad
* **Soft Delete:** Implementación de borrado lógico (campo `is_deleted`) con capacidad de restauración y auditoría.
* **Validaciones Robustas:** Reglas de negocio estrictas (DNI/Email únicos) validadas antes de llegar a la base de datos.
* **Transaccionalidad:** Manejo preciso de `@Transactional` para asegurar la consistencia en operaciones de escritura complejas.

## 🛠 Tech Stack

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3 (Web, Data JPA, Validation)
* **Base de Datos:** PostgreSQL 16 (Contenerizada)
* **Infraestructura:** Docker & Docker Compose
* **Herramientas:** Lombok, Maven

## 📂 Estructura del Proyecto

La estructura sigue un enfoque de **"Package by Feature"**:

```text
src/main/java/com/clinic
├── doctor              # Módulo de Doctores
│   ├── DoctorEntity.java
│   ├── DoctorRepository.java
│   ├── DoctorService.java
│   ├── DoctorController.java
│   └── ...
├── patient             # Módulo de Pacientes
│   ├── PatientEntity.java
│   ├── PatientRepository.java
│   ├── PatientService.java
│   └── ...
├── person              # Módulo Base (Shared Kernel)
│   └── ...
└── infrastructure      # Configuración global (Exceptions, Swagger, etc.)
```

## 🐳 Instalación y Ejecución (Docker)

El proyecto incluye una configuración de **Docker Compose** para levantar la base de datos PostgreSQL automáticamente, sin necesidad de instalar el motor de base de datos localmente.

### Prerrequisitos

* **Java 17** o superior (JDK).
* **Docker** y **Docker Compose** instalados y corriendo.
* **Maven** (opcional, el proyecto incluye el wrapper `mvnw`).

### Pasos para correr el proyecto

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/tu-usuario/medical-clinic-api-v2.git](https://github.com/tu-usuario/medical-clinic-api-v2.git)
    cd medical-clinic-api-v2
    ```

2.  **Levantar la Base de Datos:**
    Ejecuta el siguiente comando en la raíz del proyecto para descargar la imagen de PostgreSQL y levantar el contenedor:
    ```bash
    docker-compose up -d
    ```
    *Esto iniciará un contenedor de PostgreSQL en el puerto `5432` con las credenciales configuradas en `application.properties`.*


La API estará disponible en: `http://localhost:8080`

---

## 🔌 API Endpoints (Ejemplos)

La API sigue los principios RESTful. A continuación, algunos de los endpoints principales:

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/patients` | Obtiene una lista paginada de pacientes (Formato `ListResponse`). |
| **GET** | `/patients/{id}` | Obtiene el detalle completo de un paciente (Formato `DetailResponse` con historial). |
| **POST** | `/patients` | Registra un nuevo paciente (Valida DNI y Email únicos). |
| **PUT** | `/doctors/{id}` | Actualiza la información profesional y personal de un doctor. |
| **DELETE**| `/persons/{id}` | Realiza un **Soft Delete** (marcado lógico) de una persona. |
| **PATCH** | `/persons/{id}/restore` | Restaura una persona eliminada lógicamente. |

> **Nota:** Para probar los endpoints, se recomienda usar **Postman** o **Insomnia**.

---
## 💡 Decisiones de Arquitectura y Refactorización (V2)

Esta versión 2.0 no es solo una limpieza de código; es un rediseño completo basado en los problemas de escalabilidad y mantenimiento que encontré en la versión anterior. Aquí explico el "porqué" detrás de los cambios más importantes:

### 1. Dockerización: "Adiós al 'En mi local funciona'"
En la versión anterior, configurar la base de datos requería instalaciones manuales de PostgreSQL que variaban según el sistema operativo.
* **La Solución:** Implementé `docker-compose` para orquestar la base de datos.
* **El impacto:** Ahora el entorno de desarrollo es efímero y reproducible. Cualquier desarrollador puede levantar la infraestructura exacta en segundos con un solo comando, eliminando inconsistencias entre entornos de desarrollo y producción.

### 2. Normalización con `PersonEntity` (DRY en Base de Datos)
Noté que `Doctor` y `Patient` repetían constantemente los mismos campos: *nombre, apellido, email, dni*. Esto violaba el principio DRY (Don't Repeat Yourself) y hacía pesadillas las validaciones cruzadas (ej: ¿puede un doctor ser paciente con el mismo email?).
* **La Solución:** Extraje la identidad base a una entidad `Person` y utilicé una relación `@OneToOne` fuerte.
* **El impacto:** Centralizo la lógica de identidad. Si necesito validar un DNI único o cambiar el formato de teléfonos, lo hago en un solo lugar. La integridad de los datos está garantizada a nivel estructural.

### 3. Java Records vs. Clases (Inmutabilidad por defecto)
Los DTOs antiguos eran clases llenas de *boilerplate* (getters, setters, constructores) y eran mutables, lo cual es un riesgo en la transferencia de datos.
* **La Solución:** Migré el 100% de los DTOs a **Java Records**.
* **El impacto:** Reduje el código repetitivo en un 60%. Gané inmutabilidad automáticamente (los datos no cambian mágicamente mientras viajan del Controller al Service) y el código es mucho más declarativo y limpio.

### 4. SQL Tuning: Matando el problema "N+1"
Al listar pacientes, Hibernate ejecutaba una consulta para traer la lista y luego **una consulta extra por cada paciente** para traer sus datos personales (el famoso problema N+1), saturando la base de datos.
* **La Solución:** Reescribí los repositorios usando **JPQL** con `JOIN FETCH` y `@EntityGraph`.
* **El impacto:** Pasamos de ejecutar 51 consultas para listar 50 pacientes, a ejecutar **solo 1 consulta**. El tiempo de respuesta en listados masivos bajó drásticamente.

### 5. Soft Delete (Borrado Lógico)
En un sistema médico, borrar datos físicamente es peligroso y a menudo ilegal por temas de auditoría.
* **La Solución:** Implementé un sistema de "Borrado Suave" interceptando los comandos de Hibernate. Los datos no se eliminan (`DELETE`), solo se ocultan (`UPDATE is_deleted = true`).
* **El impacto:** Tenemos una "papelera de reciclaje" nativa. Podemos restaurar usuarios eliminados por error y mantener la integridad referencial histórica de las citas médicas, incluso si el paciente "ya no existe" en el sistema activo.
## 🔄 Roadmap (Mejoras Futuras)

El desarrollo es continuo. Las próximas características planificadas son:

* [ ] **Seguridad:** Implementación de Spring Security + JWT para autenticación y autorización basada en roles (Admin/Doctor/User).
* [ ] **Documentación:** Integración de Swagger/OpenAPI (`springdoc-openapi`) para documentación interactiva.
* [ ] **Testing:** Cobertura de tests unitarios y de integración con JUnit 5 y Mockito.
* [ ] **Citas Médicas:** Módulo completo para gestión de turnos (Appointments).

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar la arquitectura o nuevas funcionalidades:

1.  Haz un Fork del proyecto.
2.  Crea una rama con tu nueva funcionalidad (`git checkout -b feature/AmazingFeature`).
3.  Haz Commit de tus cambios (`git commit -m 'Add some AmazingFeature'`).
4.  Haz Push a la rama (`git push origin feature/AmazingFeature`).
5.  Abre un Pull Request.

---

---
**Desarrollado con ❤️ y mucho cafe **
