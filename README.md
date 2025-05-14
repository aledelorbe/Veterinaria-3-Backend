# Veterinaria (Backend)

Este proyecto consiste en el desarrollo de un backend para gestionar información relacionada con una veterinaria. Utiliza el framework **Spring Boot** y está diseñado para proporcionar una API REST que permita manejar clientes, sus mascotas y direcciones asociadas.

## Tecnologías utilizadas

- **Java**: Lenguaje de programación principal. Para este proyecto en específico se utilizó el `JDK 17`.
- **Spring Boot**: Framework para construir aplicaciones Java. Particularmente en este proyecto se utiliza la versión `3.4.0`.
  - **Hibernate/JPA**: Para la gestión de la base de datos relacional.
  - **Jakarta Validation**: Para la validación de datos de entrada.
  - **Manejo de Excepciones**.
  - **Programación Orientada a Aspectos (POA)**.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **MySQL**: Gestor de base de datos relacional para almacenar la información de los clientes, mascotas y direcciones.
- **Postman**: Para simular ser un cliente que hace peticiones al servidor y probar los endpoints.
- **JUnit**: Framework de pruebas unitarias utilizado para verificar el correcto funcionamiento de los métodos.
- **Mockito**: Framework de mocking usado para simular dependencias y facilitar las pruebas unitarias en aislamiento.

## Características

- **API REST** con rutas organizadas para interactuar con las actividades. Operaciones soportadas:
  - **Client**:
    - Obtener la lista de todos los clientes.
    - Obtener la lista de todos los clientes cuya mascota tenga cierto nombre.
    - Obtener la información de un cliente específico por su ID.
    - Obtener la información de un cliente específico por su nombre.
    - Obtener la información de un cliente específico por su apellido.
    - Crear un nuevo cliente.
    - Actualizar la información de un cliente existente.
    - Eliminar un cliente por su ID.
  - **Pet**:
    - Obtener la lista de todas las mascotas pertenecientes a un cliente específico.
    - Agregar una nueva mascota a un cliente específico.
    - Actualizar la información de una mascota de un cliente específico.
    - Eliminar la información de una mascota de un cliente específico.
  - **Address**:
    - Obtener la dirección perteneciente a un cliente específico.
    - Agregar una dirección a un cliente específico.
    - Actualizar la información de la dirección de un cliente específico.
    - Eliminar la información de dirección de un cliente específico.
- Integración con MySQL para la manipulación de datos.
- La base de datos SQL cuenta con tres tablas que gestionan la información de los clientes, mascotas y direcciones.
- **Restricciones en la base de datos**:
  - No se permite que un mismo cliente se registre dos veces en la base de datos.
  - No se permite que se registre dos veces una misma mascota para un mismo cliente en la base de datos.
- **Manejo de excepciones**:
  - Si se rompe la restricción para la entidad `Client` al intentar registrar dos veces a un mismo cliente, o si se rompe la restricción para la entidad `Pet` al intentar registrar dos veces una misma mascota para un mismo cliente, se dispara la excepción `DataIntegrityViolationException`. Esta excepción se maneja mediante dos clases que, en conjunto, permiten capturarla y generar un mensaje personalizado indicando cuál de las restricciones se ha roto.
- **Implementación de Programación Orientada a Aspectos (POA)**:
  - La clase `ClientAspect` incluye métodos que interceptan, antes de su ejecución, a los métodos encargados de guardar y actualizar clientes en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **apellido** y **email**.
  - La clase `PetAspect` incluye métodos que interceptan, antes de su ejecución, a los métodos encargados de guardar y actualizar mascotas en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **especie**, **raza** y **razón de visita**.
  - La clase `AddressAspect` incluye métodos que interceptan, antes de su ejecución, a los métodos encargados de guardar y actualizar direcciones en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **calle**, **colonia** y **ciudad**.
- **Validación de datos de entrada**:
  - `Client`:
    - No se permite que los atributos **nombre** y **apellido** se reciban vacíos o con solo espacios en blanco.
    - No se permite que el atributo **email** se reciba con un formato inválido.
    - No se permite que el atributo **número telefónico** se reciba con una longitud distinta de 10.
  - `Pet`:
    - No se permite que los atributos **nombre**, **especie** y **razón de visita** se reciban vacíos o con solo espacios en blanco.
    - No se permite que el atributo **edad** se reciba vacío.
  - `Address`:
    - No se permite que los atributos **calle**, **colonia**, **ciudad** y **cp** se reciban vacíos o con solo espacios en blanco.
- Se emplea el patrón de diseño arquitectónico conocido como **MVC**, para separar en diferentes capas el código del proyecto.

## Estructura del proyecto

### Código fuente de la aplicación

- `aop/`: Carpeta donde se almacenan las clases que manejan la lógica relacionada con la programación orientada a aspectos.
- `controllers/`: Carpeta donde se almacenan las clases que manejan las solicitudes HTTP y definen los endpoints de la API.
- `services/`: Carpeta donde se almacenan las clases que contienen el código relacionado con la lógica de negocio.
- `repositories/`: Carpeta donde se almacenan las interfaces que extienden de una interfaz que permite el manejo de datos.
- `entities/`: Carpeta donde se almacenan las clases que se mapean con sus respectivas tablas en la base de datos.
- `utils/`: Carpeta donde se almacenan las clases las cuales tienen métodos utilitarios que se pueden usar de manera transversal en la aplicación.

### Código de pruebas

- `controllers/`: Contiene las clases de prueba que validan el comportamiento de los métodos en los controladores del código fuente.
- `services/`: Incluye las clases de prueba dedicadas a verificar el correcto funcionamiento de los métodos dentro de los servicios de la aplicación.
- `data/`: Almacena clases con datos simulados (mock data) utilizados durante la ejecución de las pruebas.
- `integrations/`: Contiene las clases de prueba que validan el comportamiento completo de los controladores (tests de integración).
- `resources/`: Almacena los datos en formato SQL utilizados como insumos para las pruebas de integración. Ademas contiene las propiedades de configuracion de una base de datos en memoria H2 para que la aplicación la use durante las pruebas de integracion.

## Futuras mejoras

- Realizar pruebas unitarias de la capa de repositorio.
Una vez implementada la prueba unitaria descrita en el paso anterior, generar una nueva versión del proyecto indicando que este trabajo incluye servicios, pruebas unitarias y pruebas de integración.
- Despligue en AWS.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Veterinaria](Veterinaria).

---

# Veterinary Clinic (Backend)

This project consists of the development of a backend system to manage information related to a veterinary clinic. It uses the **Spring Boot** framework and is designed to provide a REST API to manage clients, their pets, and associated addresses.

## Technologies Used

- **Java**: Main programming language. Specifically, `JDK 17` was used for this project.
- **Spring Boot**: Framework for building Java applications. This project uses version `3.4.0`.
  - **Hibernate/JPA**: For managing the relational database.
  - **Jakarta Validation**: For validating input data.
  - **Exception Handling**.
  - **Aspect-Oriented Programming (AOP)**.
- **Maven**: For dependency management and project build.
- **MySQL**: Relational database management system to store data about clients, pets, and addresses.
- **Postman**: Used to simulate a client making requests to the server and testing the endpoints.
- **JUnit**: Unit testing framework used to verify the correct functionality of methods.
- **Mockito**: Mocking framework used to simulate dependencies and enable isolated unit testing.

## Features

- **REST API** with organized routes to interact with veterinary-related activities. Supported operations:
  - **Client**:
    - Retrieve the list of all clients.
    - Retrieve the list of all clients whose pet has a certain name.
    - Retrieve information about a specific client by ID.
    - Retrieve information about a specific client by first name.
    - Retrieve information about a specific client by last name.
    - Create a new client.
    - Update an existing client.
    - Delete a client by ID.
  - **Pet**:
    - Retrieve the list of all pets belonging to a specific client.
    - Add a new pet to a specific client.
    - Update the information of a pet for a specific client.
    - Delete a pet from a specific client.
  - **Address**:
    - Retrieve the address of a specific client.
    - Add an address to a specific client.
    - Update the address of a specific client.
    - Delete the address of a specific client.
- Integration with MySQL for data manipulation.
- The SQL database includes three tables managing information about clients, pets, and addresses.
- **Database constraints**:
  - A client cannot be registered more than once.
  - A pet cannot be registered more than once for the same client.
- **Exception handling**:
  - If a constraint is violated when trying to register a duplicate `Client` or `Pet`, a `DataIntegrityViolationException` is thrown. This exception is handled using two classes that capture it and generate a custom message indicating which constraint was broken.
- **Aspect-Oriented Programming (AOP) implementation**:
  - The `ClientAspect` class contains methods that intercept the execution of client creation and update methods to trim whitespace from the **first name**, **last name**, and **email** attributes.
  - The `PetAspect` class contains methods that intercept the execution of pet creation and update methods to trim whitespace from the **name**, **species**, **breed**, and **reason for visit** attributes.
  - The `AddressAspect` class contains methods that intercept the execution of address creation and update methods to trim whitespace from the **street**, **neighborhood**, and **city** attributes.
- **Input validation**:
  - `Client`:
    - **First name** and **last name** cannot be empty or contain only whitespace.
    - **Email** must be in a valid format.
    - **Phone number** must be exactly 10 digits long.
  - `Pet`:
    - **Name**, **species**, and **reason for visit** cannot be empty or contain only whitespace.
    - **Age** must not be empty.
  - `Address`:
    - **Street**, **neighborhood**, **city**, and **postal code** cannot be empty or contain only whitespace.
- The project follows the **MVC architectural design pattern**, separating the code into different layers.

## Project Structure

### Application Source Code

- `aop/`: Contains the classes that implement aspect-oriented logic.
- `controllers/`: Contains the classes that handle HTTP requests and define the API endpoints.
- `services/`: Contains the classes with the business logic.
- `repositories/`: Contains interfaces that extend from repository interfaces for data handling.
- `entities/`: Contains the classes that map to the database tables.
- `utils/`: Contains utility classes with methods that can be used across the application.

### Test Code

- `controllers/`: Contains test classes that validate the behavior of the controller methods.
- `services/`: Contains test classes that verify the functionality of service methods.
- `data/`: Contains mock data classes used during test execution.
- `integrations/`: Contains integration test classes that validate the full behavior of the controllers.
- `resources/`: Stores SQL data used during integration tests, and configuration properties for an in-memory H2 database used specifically during these tests.

## Future Improvements

- Write unit tests for the repository layer.
- Once the unit test described in the previous step has been implemented, release a new version of the project indicating that it includes services, unit tests, and integration tests.
- Deploy the project on AWS.

## Demo

You can view a demo of the project at the following link: [Veterinaria](Veterinaria).
