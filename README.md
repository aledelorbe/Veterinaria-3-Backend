# Veterinaria (Backend)

Este proyecto consiste en el desarrollo de un backend para gestionar información relacionada con una veterinaria. Utiliza el framework **Spring Boot** y está diseñado para proporcionar una API REST que permita manejar clientes, sus mascotas y direcciones asociadas.

## Tecnologías utilizadas

- **Java**: Lenguaje de programación principal. Para este proyecto en específico se utilizó el `JDK 17`.
- **Spring Boot**: Framework para construir aplicaciones Java. Particularmente en este proyecto se utiliza la versión `3.4.0`.
  - **Hibernate/JPA**: Para la gestión de la base de datos relacional.
  - **Jakarta Validation**: Para la validación de datos de entrada.
  - **Manejo de Excepciones**
  - **Programación orientada a aspectos (POA)**
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **MySQL**: Gestor de base de datos relacional para almacenar la información de los clientes, mascotas y direcciones.
- **Postman**: Para simular ser un cliente que hace peticiones al servidor y probar los endpoints.

## Características

- **API REST** con rutas organizadas para interactuar con las actividades. Operaciones soportadas:
  - **Client**:
    - Obtener la lista de todos los clientes.
    - Obtener la información de un cliente específico por su ID.
    - Crear un nuevo cliente.
    - Actualizar la información de un cliente existente.
    - Eliminar un cliente por su ID.
  - **Pet**:
    - Agregar una nueva mascota a un cliente específico.
    - Actualizar la información de una mascota de un cliente específico.
    - Eliminar la información de una mascota de un cliente específico.
  - **Address**:
    - Agregar una dirección a un cliente específico.
    - Actualizar la información de la dirección de un cliente específico.
    - Eliminar la información de dirección de un cliente específico.
- Integración con MySQL para la manipulación de datos.
- La base de datos SQL cuenta con tres tablas que gestionan la información de los clientes, mascotas y direcciones.
- **Restricciones en la base de datos**:
  - No se permite que un mismo cliente se registre dos veces en la base de datos.
  - No se permite que se registre dos veces una misma mascota para un mismo cliente en la base de datos.
- **Manejo de excepciones**:
  - Si se viola la restricción para la entidad `Client` al intentar registrar dos veces a un mismo cliente, o si se viola la restricción para la entidad `Pet` al intentar registrar dos veces una misma mascota para un mismo cliente, se dispara la excepción `DataIntegrityViolationException`. Esta excepción se maneja mediante dos clases que, en conjunto, permiten capturarla y generar un mensaje personalizado indicando cuál de las restricciones se violó.
- **Implementación de programación orientada a aspectos (POA)**:
  - La clase `ClientAspect` incluye un método que intercepta, antes de su ejecución, el método encargado de guardar nuevos clientes en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **apellido** y **email**.
  - La clase `PetAspect` incluye un método que intercepta, antes de su ejecución, el método encargado de guardar nuevas mascotas en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **especie** y **raza**.
  - La clase `AddressAspect` incluye un método que intercepta, antes de su ejecución, el método encargado de guardar nuevas direcciones en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **calle**, **colonia** y **ciudad**.
- **Validación de datos de entrada**:
  - No se permite que los atributos **nombre** y **apellido** de la clase `Client` se reciban vacíos o con solo espacios en blanco.
  - No se permite que el atributo **email** de la clase `Client` se reciba con un formato inválido.
  - No se permite que el atributo **número telefónico** de la clase `Client` se reciba con una longitud distinta de 10.
  - No se permite que los atributos **nombre**, **especie**, **edad** y **razón de visita** de la clase `Pet` se reciban vacíos o con solo espacios en blanco.
  - No se permite que los atributos **calle**, **colonia**, **ciudad** y **cp** de la clase `Address` se reciban vacíos o con solo espacios en blanco.
- Se emplea el patrón de diseño arquitectónico conocido como **MVC**, para separar en diferentes capas el código del proyecto.

## Estructura del proyecto

- `aop/`: Carpeta donde se almacenan las clases que manejan la lógica relacionada con la programación orientada a aspectos.
- `controllers/`: Carpeta donde se almacenan las clases que manejan las solicitudes HTTP y definen los endpoints de la API.
- `services/`: Carpeta donde se almacenan las clases que contienen el código relacionado con la lógica de negocio.
- `repositories/`: Carpeta donde se almacenan las interfaces que extienden de una interfaz que permite el manejo de datos.
- `entities/`: Carpeta donde se almacenan las clases que se mapean con sus respectivas tablas en la base de datos.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Veterinaria](Veterinaria).


---

# Veterinary (Backend)

This project involves the development of a backend to manage information related to a veterinary clinic. It uses the **Spring Boot** framework and is designed to provide a REST API for managing clients, their pets, and associated addresses.

## Technologies Used

- **Java**: Main programming language. This project specifically uses `JDK 17`.
- **Spring Boot**: Framework for building Java applications. In this project, version `3.4.0` is used.
  - **Hibernate/JPA**: For managing the relational database.
  - **Jakarta Validation**: For validating input data.
  - **Exception Handling**  
  - **Aspect-Oriented Programming (AOP)**  
- **Maven**: For dependency management and project build.
- **MySQL**: Relational database management system to store information about clients, pets, and addresses.
- **Postman**: Used to simulate a client making requests to the server and to test the endpoints.

## Features

- **REST API** with organized routes to handle activities. Supported operations:
  - **Client**:
    - Retrieve a list of all clients.
    - Retrieve details of a specific client by their ID.
    - Create a new client.
    - Update an existing client's information.
    - Delete a client by their ID.
  - **Pet**:
    - Add a new pet to a specific client.
    - Update the information of a specific client's pet.
    - Delete the information of a specific client's pet.
  - **Address**:
    - Add an address to a specific client.
    - Update the address information of a specific client.
    - Delete the address information of a specific client.
- Integration with MySQL for data manipulation.
- The SQL database includes three tables to manage client, pet, and address information.
- **Database Constraints**:
  - Duplicate client registrations are not allowed.
  - Duplicate pet registrations for the same client are not allowed.
- **Exception Handling**:
  - If the `Client` entity constraint is violated by attempting to register the same client twice, or if the `Pet` entity constraint is violated by attempting to register the same pet twice for a single client, a `DataIntegrityViolationException` is triggered. This exception is handled through two classes that work together to catch it and generate a custom message indicating which constraint was violated.
- **Aspect-Oriented Programming (AOP) Implementation**:
  - The `ClientAspect` class includes a method that intercepts, before execution, the method responsible for saving new clients to the database. Its purpose is to trim leading and trailing whitespace from the **name**, **surname**, and **email** attributes.
  - The `PetAspect` class includes a method that intercepts, before execution, the method responsible for saving new pets to the database. Its purpose is to trim leading and trailing whitespace from the **name**, **species**, and **breed** attributes.
  - The `AddressAspect` class includes a method that intercepts, before execution, the method responsible for saving new addresses to the database. Its purpose is to trim leading and trailing whitespace from the **street**, **neighborhood**, and **city** attributes.
- **Input Data Validation**:
  - The **name** and **surname** attributes of the `Client` class cannot be empty or contain only whitespace.
  - The **email** attribute of the `Client` class must follow a valid email format.
  - The **phone number** attribute of the `Client` class must have exactly 10 characters.
  - The **name**, **species**, **age**, and **reason for visit** attributes of the `Pet` class cannot be empty or contain only whitespace.
  - The **street**, **neighborhood**, **city**, and **postal code** (`cp`) attributes of the `Address` class cannot be empty or contain only whitespace.
- The **MVC** architectural design pattern is used to separate the project's code into different layers.

## Project Structure

- `aop/`: Folder where classes handling logic related to aspect-oriented programming are stored.
- `controllers/`: Folder where classes handling HTTP requests and defining the API endpoints are stored.
- `services/`: Folder where classes containing business logic are stored.
- `repositories/`: Folder where interfaces extending a data management interface are stored.
- `entities/`: Folder where classes mapped to their respective database tables are stored.

## Demo

You can view a demo of the project at the following link: [Veterinary](Veterinaria).
