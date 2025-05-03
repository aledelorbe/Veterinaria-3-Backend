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
  - La clase `ClientAspect` incluye un método que intercepta, antes de su ejecución, el método encargado de guardar nuevos clientes en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **apellido** y **email**.
  - La clase `PetAspect` incluye un método que intercepta, antes de su ejecución, el método encargado de guardar nuevas mascotas en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **especie** y **raza**.
  - La clase `AddressAspect` incluye un método que intercepta, antes de su ejecución, el método encargado de guardar nuevas direcciones en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **calle**, **colonia** y **ciudad**.
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

- `aop/`: Carpeta donde se almacenan las clases que manejan la lógica relacionada con la programación orientada a aspectos.
- `controllers/`: Carpeta donde se almacenan las clases que manejan las solicitudes HTTP y definen los endpoints de la API.
- `services/`: Carpeta donde se almacenan las clases que contienen el código relacionado con la lógica de negocio.
- `repositories/`: Carpeta donde se almacenan las interfaces que extienden de una interfaz que permite el manejo de datos.
- `entities/`: Carpeta donde se almacenan las clases que se mapean con sus respectivas tablas en la base de datos.

## Futuras mejoras

- Implementar un endpoint que permita recuperar la información de un cliente específico y todas sus mascotas a partir del nombre de alguna de sus mascotas.
- Agregar un metodo de intercepcion (antes de su ejecucion) a los metodos actualizar de las entidades clientes, mascotas y direcciones.
- Implementar la manera correcta de hacer CRUD sobre los objetos de las entidades mascotas y direcciones.
- Despligue en AWS.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Veterinaria](Veterinaria).

---

# Veterinary Clinic (Backend)

This project involves the development of a backend to manage information related to a veterinary clinic. It uses the **Spring Boot** framework and is designed to provide a REST API for managing clients, their pets, and associated addresses.

## Technologies Used

- **Java**: Main programming language. For this specific project, `JDK 17` was used.
- **Spring Boot**: Framework for building Java applications. This project uses version `3.4.0`.
  - **Hibernate/JPA**: For managing the relational database.
  - **Jakarta Validation**: For input data validation.
  - **Exception Handling**.
  - **Aspect-Oriented Programming (AOP)**.
- **Maven**: For dependency management and project building.
- **MySQL**: Relational database management system to store information about clients, pets, and addresses.
- **Postman**: To simulate client requests to the server and test the API endpoints.

## Features

- **REST API** with organized routes to interact with activities. Supported operations:
  - **Client**:
    - Retrieve a list of all clients.
    - Retrieve the details of a specific client by their ID.
    - Create a new client.
    - Update an existing client's information.
    - Delete a client by their ID.
  - **Pet**:
    - Retrieve a list of all pets belonging to a specific client.
    - Add a new pet to a specific client.
    - Update a specific client's pet information.
    - Delete a specific client's pet information.
  - **Address**:
    - Retrieve the address of a specific client.
    - Add an address to a specific client.
    - Update a specific client's address information.
    - Delete a specific client's address information.
- MySQL integration for data manipulation.
- The SQL database includes three tables to manage information about clients, pets, and addresses.
- **Database Constraints**:
  - Duplicate client registrations are not allowed.
  - Duplicate pet registrations for the same client are not allowed.
- **Exception Handling**:
  - If a constraint is violated for the `Client` entity by attempting to register the same client twice, or for the `Pet` entity by attempting to register the same pet twice for a specific client, a `DataIntegrityViolationException` is triggered. This exception is handled using two classes that together capture and generate a custom message indicating which constraint was violated.
- **Aspect-Oriented Programming (AOP) Implementation**:
  - The `ClientAspect` class includes a method that intercepts the execution of the method responsible for saving new clients in the database. Its purpose is to trim whitespace from the **name**, **surname**, and **email** attributes.
  - The `PetAspect` class includes a method that intercepts the execution of the method responsible for saving new pets in the database. Its purpose is to trim whitespace from the **name**, **species**, and **breed** attributes.
  - The `AddressAspect` class includes a method that intercepts the execution of the method responsible for saving new addresses in the database. Its purpose is to trim whitespace from the **street**, **neighborhood**, and **city** attributes.
- **Input Data Validation**:
  - `Client`:
    - The **name** and **surname** attributes cannot be empty or contain only whitespace.
    - The **email** attribute must have a valid format.
    - The **phone number** attribute must be exactly 10 characters long.
  - `Pet`:
    - The **name**, **species**, and **reason for visit** attributes cannot be empty or contain only whitespace.
    - The **age** attribute cannot be empty.
  - `Address`:
    - The **street**, **neighborhood**, **city**, and **postal code** attributes cannot be empty or contain only whitespace.
- The **MVC architectural design pattern** is employed to separate the project's code into different layers.

## Project Structure

- `aop/`: Folder where classes handling logic related to aspect-oriented programming are stored.
- `controllers/`: Folder where classes handling HTTP requests and defining API endpoints are stored.
- `services/`: Folder where classes containing business logic are stored.
- `repositories/`: Folder where interfaces extending data management functionality are stored.
- `entities/`: Folder where classes mapping to their respective database tables are stored.

## Future Improvements

- Implement an endpoint to retrieve a specific client's information using their full name.
- Implement an endpoint to retrieve a specific client's information and all their pets using the name of one of their pets.
- Add an interception method (before execution) to the update methods of the client, pet, and address entities.
- Implement the correct way to perform CRUD operations on the objects of the pet and address entities.
- Deploy on AWS.

## Demo

You can view a demo of the project at the following link: [Veterinary Clinic](Veterinaria).
