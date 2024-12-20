# Veterinaria (Backend)

Este proyecto consiste en el desarrollo de un backend para gestionar información relacionada con una veterinaria. Utiliza el framework **Spring Boot** y está diseñado para proporcionar una API REST que permita manejar clientes, sus mascotas y direcciones asociadas.

## Tecnologías utilizadas

- **Java**: Lenguaje de programación principal. Para este proyecto en específico se utilizó el `JDK 17`.
- **Spring Boot**: Framework para construir aplicaciones Java. Particularmente en este proyecto se utiliza la versión `3.4.0`.
  - **Hibernate/JPA**: Para la gestión de la base de datos relacional.
  - **Jakarta Validation**: Para la validación de datos de entrada.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **MySQL**: Gestor de base de datos relacional para almacenar la información de los clientes, mascotas y direcciones.
- **Postman**: Para simular ser un cliente que hace peticiones al servidor y probar los endpoints.

## Características

- API REST con rutas organizadas para interactuar con actividades. Operaciones soportadas:
  - `Client`:
    - Obtener la lista de todos los clientes.
    - Obtener la información de un cliente específico por su ID.
    - Crear un nuevo cliente.
    - Actualizar la información de un cliente existente.
    - Eliminar un cliente por su ID.
  - `Pet`:
    - Agregar una nueva mascota a un cliente específico.
    - Actualizar la información de una mascota de un cliente específico.
    - Eliminar la información de una mascota de un cliente específico.
  - `Address`:
    - Agregar una dirección a un cliente específico.
    - Actualizar la información de la dirección de un cliente específico.
    - Eliminar la información de dirección de un cliente específico.
- Integración con MySQL para la manipulación de datos.
- La base de datos SQL cuenta con tres tablas, las cuales gestionan la información de los clientes, mascotas y direcciones.
- Restricciones en la base de datos. Se emplean las siguientes restricciones:
  - No se permite que un mismo cliente se registre dos veces en la base de datos.
  - No se permite que se registre dos veces una misma mascota para un mismo cliente en la base de datos.
- Validación de datos de entrada. Se emplean las siguientes validaciones:
  - No se permite que los atributos **nombre** y **apellido** de la clase `Client` se reciban vacíos o con puros espacios en blanco.
  - No se permite que el atributo **email** de la clase `Client` se reciba con un formato inválido.
  - No se permite que el atributo **número telefónico** de la clase `Client` se reciba con una longitud distinta de 10.
  - No se permite que los atributos **nombre**, **especie**, **edad** y **razón de visita** de la clase `Pet` se reciban vacíos o con puros espacios en blanco.
  - No se permite que los atributos **calle**, **colonia**, **ciudad** y **cp** de la clase `Address` se reciban vacíos o con puros espacios en blanco.
- Se emplea el patrón de diseño arquitectónico conocido como **MVC**, para separar en diferentes capas el código del proyecto.

## Estructura del proyecto

- `controllers/`: Carpeta donde se almacenan las clases que manejan las solicitudes HTTP y definen los endpoints de la API.
- `services/`: Carpeta donde se almacenan las clases que contienen el código relacionado con la lógica de negocio.
- `repositories/`: Carpeta donde se almacenan las interfaces que extienden de una interfaz que permite el manejo de datos.
- `entities/`: Carpeta donde se almacenan las clases que se mapean con sus respectivas colecciones en la base de datos.

## Demo

Puedes ver una demo del proyecto en el siguiente enlace: [Veterinaria](Veterinaria).

---

# Veterinary Clinic (Backend)

This project involves the development of a backend to manage information related to a veterinary clinic. It uses the **Spring Boot** framework and is designed to provide a REST API to handle clients, their pets, and associated addresses.

## Technologies Used

- **Java**: Main programming language. For this specific project, `JDK 17` was used.
- **Spring Boot**: Framework for building Java applications. In this project, version `3.4.0` is used.
  - **Hibernate/JPA**: For relational database management.
  - **Jakarta Validation**: For input data validation.
- **Maven**: For dependency management and project building.
- **MySQL**: Relational database management system to store information about clients, pets, and addresses.
- **Postman**: To simulate a client making requests to the server and test the endpoints.

## Features

- REST API with organized routes to interact with activities. Supported operations:
  - `Client`:
    - Retrieve the list of all clients.
    - Retrieve information of a specific client by ID.
    - Create a new client.
    - Update information of an existing client.
    - Delete a client by ID.
  - `Pet`:
    - Add a new pet to a specific client.
    - Update information of a pet for a specific client.
    - Delete information of a pet for a specific client.
  - `Address`:
    - Add an address to a specific client.
    - Update the address information of a specific client.
    - Delete the address information of a specific client.
- Integration with MySQL for data manipulation.
- The SQL database has three tables to manage the information of clients, pets, and addresses.
- Database constraints. The following restrictions are applied:
  - A client cannot be registered more than once in the database.
  - A pet cannot be registered more than once for the same client in the database.
- Input data validation. The following validations are applied:
  - The **first name** and **last name** attributes of the `Client` class cannot be empty or contain only spaces.
  - The **email** attribute of the `Client` class must have a valid format.
  - The **phone number** attribute of the `Client` class must have a length of 10.
  - The **name**, **species**, **age**, and **reason for visit** attributes of the `Pet` class cannot be empty or contain only spaces.
  - The **street**, **neighborhood**, **city**, and **postal code** attributes of the `Address` class cannot be empty or contain only spaces.
- The architectural design pattern **MVC** is used to separate the code into different layers.

## Project Structure

- `controllers/`: Folder where classes handling HTTP requests and defining API endpoints are stored.
- `services/`: Folder where classes containing business logic are stored.
- `repositories/`: Folder where interfaces extending data management capabilities are stored.
- `entities/`: Folder where classes mapped to their respective collections in the database are stored.

## Demo

You can view a project demo at the following link: [Veterinary Clinic](Veterinary).
