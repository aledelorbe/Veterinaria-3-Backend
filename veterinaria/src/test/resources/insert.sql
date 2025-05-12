DELETE FROM pet;
DELETE FROM client;
DELETE FROM address;

INSERT INTO address (id_address, street, state, city, cp) VALUES
(1001, 'ignacio zaragoza', 'ixtapaluca', 'estado de mexico', 56585),
(2001, 'av. siempre viva', 'venustiano carranza', 'cdmx', 56512),
(3001, 'calle false 123', 'iztapalapa', 'cdmx', 12585),
(4001, 'candelaria', 'gustavo madero', 'cdmx', 56546);
INSERT INTO client (id_client, name, lastname, email, phonenumber, id_address) VALUES
(61, 'Alejandro', 'Granados', 'alejandro.magb@gmail.com', 1538977020, 1001),
(21, 'Hueto', 'Navejas', 'hekevim148@idoidraw.com', 1538971230, 2001),
(31, 'Celia', 'Bello', 'cazador19@idoidraw.com', 1234977026, 3001),
(41, 'Esteban', 'Gonzalez', 'pastor34@idoidraw.com', 1234567890, 4001),
(51, 'John', 'Lennon', 'lennon@idoidraw.com', 4520895423, null);
INSERT INTO pet (id_pet, name, specie, breed, age, reason_for_visit, id_client) VALUES
(101, 'rayas', 'gato', 'rayado', 11, 'tiene mucho sueño', 61),
(201, 'goliath', 'perro', 'chihuahua', 4, 'vomita mucho', 21),
(301, 'baguira', 'perro', 'pastor aleman', 15, 'le duele el estomago', 21),
(901, 'guana', 'gato', NULL, 7, 'esta emarazada', 21),
(501, 'nala', 'perro', NULL, 4, 'no quiere comer', 31),
(601, 'pennywise', 'gato', 'manchado', 5, 'no puede ir al baño', 31),
(701, 'goliath', 'perro', 'chihuahua', 4, 'tiene mucho sueño', 41),
(801, 'quick', 'perro', 'labrador', 10, 'tiene mucho sueño', 41);
