# 🏟 Proyecto Semestral: Arquitectura de Microservicios - Complejo Deportivo

##  Integrantes
* **Kevis Howard Gonzalez Gonzalez**
* **Virgilio Herrera**

---

##  Descripción del Proyecto
El proyecto consiste en el diseño y desarrollo de una **Arquitectura Distribuida Basada en Microservicios** para la gestión integral de un **Complejo Deportivo**. La solución está diseñada para automatizar, desacoplar y escalar las operaciones críticas del negocio, garantizando modularidad y separación funcional entre sus componentes.

El ecosistema de la aplicación se estructura bajo el patrón de diseño **CSR (Controller-Service-Repository)**, asegurando que cada microservicio maneje de forma aislada e independiente su propia lógica de negocio, persistencia de datos relacional y exposición de endpoints RESTful.

---

## 🛠️ Microservicios Implementados
* **UsuariosMs
* ** PagosMs
* ** CanchasMs
* ** ReservasMs
* ** HorariosMs
* ** InventarioMs
* **  ListaNegraMs
* ** NotificacionesMs
* ** SeguridadMs
* ** EstadisticasMs

Cada módulo cuenta con una separación estricta de responsabilidades en paquetes por capa:
* **UsuariosMs:** Administración de perfiles de clientes, gestión de datos personales y roles en la plataforma.
* **CanchasMs:** Control de inventario de canchas, definición de características, atributos del dominio y disponibilidad en tiempo real.
* **PagosMs:** Procesamiento de transacciones financieras, control y auditoría de estados de pago.
* **ReservasMs:** Orquestación de la lógica transaccional de agendamiento e integridad referencial.

###  Características Técnicas Destacadas
* **Persistencia Real:** Conexión independiente mediante **JPA + Hibernate** y scripts de migración inicial de base de datos.
* **Validaciones Robustas:** Uso de **Bean Validation (JSR 380)** con separación estricta entre DTOs y entidades.
* **Manejo de Excepciones:** Control centralizado de errores con `@ControllerAdvice` y códigos de estado HTTP semánticos (`ResponseEntity`).
* **Comunicación Remota:** Interoperabilidad entre componentes mediante el uso de **WebClient**.
* **Trazabilidad:** Inserción de logs estructurados con **SLF4J** en puntos estratégicos del flujo.

---

## ⚙ Pasos para la Ejecución

### 📋 Prerrequisitos
* Java 21 o superior instalado.
* Motor de Base de Datos (XAMPP).
* IntelliJ IDEA o VS Code.

### 🏃‍♂️ Levantamiento del Proyecto
1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/kevgonzalezg-collab/complejo-deportivo-microservicios.git](https://github.com/kevgonzalezg-collab/complejo-deportivo-microservicios.git)
    ```
2.  **Configurar Base de Datos:** Ajustar las credenciales del *datasource* en el archivo `application.properties` de cada microservicio.
3.  **Compilar y Ejecutar:** Abrir el proyecto en el IDE y levantar la clase principal (`@SpringBootApplication`) de cada uno de los microservicios de forma independiente.
4.  **Pruebas de Endpoints:** Utilizar Postman u otra herramienta REST para conocer e interactuar con los endpoints cargados en el sistema.