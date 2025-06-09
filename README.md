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

### EndPoint's

Rutas organizadas para interactuar con los clientes, mascotas y direcciones. Operaciones soportadas:
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
 
### Programación Orientada a Aspectos (POA)

- La clase `ClientAspect` incluye métodos que interceptan, antes de su ejecución, a los métodos encargados de guardar y actualizar clientes en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **apellido** y **email**.
- La clase `PetAspect` incluye métodos que interceptan, antes de su ejecución, a los métodos encargados de guardar y actualizar mascotas en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **nombre**, **especie**, **raza** y **razón de visita**.
- La clase `AddressAspect` incluye métodos que interceptan, antes de su ejecución, a los métodos encargados de guardar y actualizar direcciones en la base de datos. Su objetivo es eliminar los espacios en blanco al inicio y al final de los atributos **calle**, **colonia** y **ciudad**.
 
### Gestor de base de datos

- Integración con MySQL para la manipulación de datos.
- La base de datos SQL cuenta con tres tablas que gestionan la información de los clientes, mascotas y direcciones.
- **Restricciones en la base de datos**:
  - No se permite que un mismo cliente se registre dos veces en la base de datos.
  - No se permite que se registre dos veces una misma mascota para un mismo cliente en la base de datos.

### Validaciones y Excepciones

- **Manejo de excepciones**:
  - Si se rompe la restricción para la entidad `Client` al intentar registrar dos veces a un mismo cliente, o si se rompe la restricción para la entidad `Pet` al intentar registrar dos veces una misma mascota para un mismo cliente, se dispara la excepción `DataIntegrityViolationException`. Esta excepción se maneja mediante dos clases que, en conjunto, permiten capturarla y generar un mensaje personalizado indicando cuál de las restricciones se ha roto.
- **Validaciones de datos de entrada**:
  - `Client`:
    - No se permite que los atributos **nombre** y **apellido** se reciban vacíos o con solo espacios en blanco.
    - No se permite que el atributo **email** se reciba con un formato inválido.
    - No se permite que el atributo **número telefónico** se reciba con una longitud distinta de 10.
  - `Pet`:
    - No se permite que los atributos **nombre**, **especie** y **razón de visita** se reciban vacíos o con solo espacios en blanco.
    - No se permite que el atributo **edad** se reciba vacío.
  - `Address`:
    - No se permite que los atributos **calle**, **colonia**, **ciudad** y **cp** se reciban vacíos o con solo espacios en blanco.
   
### Patrones de diseño
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

- Cambiar el nombre de la variable y columna phoneNumber
- Actuazliar el clientSave y clientDelete.
- Realizar pruebas unitarias de la capa de repositorio.
- Una vez implementada la prueba unitaria descrita en el paso anterior, generar una nueva versión del proyecto indicando que este trabajo incluye servicios, pruebas unitarias y pruebas de integración.
- Agregar una tabla llamada `Historial de precios` para registrar las anteriores veces en las que ha venido el paciente.
- Despligue en AWS.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Veterinaria](Veterinaria).

---

# Veterinary Clinic (Backend)

This project involves the development of a backend system to manage information related to a veterinary clinic. It uses the **Spring Boot** framework and is designed to provide a REST API for handling clients, their pets, and associated addresses.

## Technologies Used

- **Java**: Main programming language. This project specifically uses `JDK 17`.
- **Spring Boot**: Framework for building Java applications. This project uses version `3.4.0`.
  - **Hibernate/JPA**: For managing the relational database.
  - **Jakarta Validation**: For input data validation.
  - **Exception Handling**.
  - **Aspect-Oriented Programming (AOP)**.
- **Maven**: For dependency management and project building.
- **MySQL**: Relational database management system used to store information about clients, pets, and addresses.
- **Postman**: Used to simulate a client making requests to the server and to test the endpoints.
- **JUnit**: Unit testing framework used to verify the correct functioning of methods.
- **Mockito**: Mocking framework used to simulate dependencies and facilitate isolated unit testing.

## Features

### Endpoints

Organized routes to interact with clients, pets, and addresses. Supported operations:

- **Client**:
  - Get a list of all clients.
  - Get a list of all clients whose pet has a specific name.
  - Get information of a specific client by ID.
  - Get information of a specific client by name.
  - Get information of a specific client by last name.
  - Create a new client.
  - Update an existing client's information.
  - Delete a client by ID.

- **Pet**:
  - Get the list of all pets belonging to a specific client.
  - Add a new pet to a specific client.
  - Update a pet's information for a specific client.
  - Delete a pet's information for a specific client.

- **Address**:
  - Get the address belonging to a specific client.
  - Add an address to a specific client.
  - Update a client's address information.
  - Delete a client's address information.

### Aspect-Oriented Programming (AOP)

- The `ClientAspect` class includes methods that intercept, before execution, the methods responsible for saving and updating clients in the database. Its purpose is to trim leading and trailing whitespace from the **name**, **last name**, and **email** attributes.
- The `PetAspect` class includes methods that intercept, before execution, the methods responsible for saving and updating pets in the database. Its purpose is to trim whitespace from **name**, **species**, **breed**, and **reason for visit**.
- The `AddressAspect` class includes methods that intercept, before execution, the methods responsible for saving and updating addresses in the database. Its purpose is to trim whitespace from **street**, **neighborhood**, and **city** attributes.

### Database Management

- Integration with MySQL for data manipulation.
- The SQL database has three tables to manage information on clients, pets, and addresses.
- **Database Constraints**:
  - A client cannot be registered more than once.
  - A pet cannot be registered more than once for the same client.

### Validations and Exceptions

- **Exception Handling**:
  - If the `Client` entity constraint is violated by trying to register the same client twice, or if the `Pet` entity constraint is violated by registering the same pet twice for the same client, a `DataIntegrityViolationException` is thrown. This exception is handled by two classes that work together to capture it and generate a custom message indicating which constraint was violated.

- **Input Data Validation**:
  - `Client`:
    - **Name** and **last name** cannot be empty or contain only whitespace.
    - **Email** must have a valid format.
    - **Phone number** must be exactly 10 digits.
  - `Pet`:
    - **Name**, **species**, and **reason for visit** cannot be empty or contain only whitespace.
    - **Age** must not be empty.
  - `Address`:
    - **Street**, **neighborhood**, **city**, and **postal code** must not be empty or contain only whitespace.

### Design Patterns

- The **MVC** architectural design pattern is used to separate the project code into different layers.

## Project Structure

### Application Source Code

- `aop/`: Contains classes handling logic related to aspect-oriented programming.
- `controllers/`: Contains classes handling HTTP requests and defining the API endpoints.
- `services/`: Contains classes with business logic.
- `repositories/`: Contains interfaces that extend a data access interface.
- `entities/`: Contains classes mapped to their respective database tables.
- `utils/`: Contains utility classes with reusable methods across the application.

### Test Code

- `controllers/`: Contains test classes that validate controller method behaviors.
- `services/`: Includes test classes for verifying service method functionality.
- `data/`: Stores mock data classes used during testing.
- `integrations/`: Contains test classes that validate the full behavior of controllers (integration tests).
- `resources/`: Stores SQL data used as input for integration tests and contains configuration properties for an in-memory H2 database used during testing.

## Future Improvements

- Implement unit tests for the repository layer.
- Once the unit test described in the previous step has been implemented, release a new version of the project indicating that it includes services, unit tests, and integration tests.
- Deploy to AWS.

## Demo

You can see a demo of the project at the following link: [Veterinary Clinic](Veterinaria).
