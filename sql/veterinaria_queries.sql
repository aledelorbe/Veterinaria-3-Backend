-- create database veterinaria;

use veterinaria;

-- Client Entity

select * 
from client;

-- Pet Entity

select * 
from pet;

-- Address Entity

select * 
from address;

-- Queries

-- To get the address of certain client
select ad.*
from client cl
inner join address ad
on cl.id_address = ad.id_address
where cl.id_client = 1;

-- To get all the pets of certain client
select *
from pet
where id_client = 1;
