select * from categories;
select name_cat from categories;

select * from prescription p
INNER JOIN `contains` ON `contains`.id_prescription=p.id_prescription
INNER JOIN person ON person.id_person=p.id_person
INNER JOIN doctor ON doctor.id_doctor=p.id_doctor;

select p.id_prescription, date_prescription, p.id_person, person.firstname, person.lastname, p.id_doctor from prescription p
INNER JOIN `contains` ON `contains`.id_prescription=p.id_prescription
INNER JOIN person ON person.id_person=p.id_person
INNER JOIN doctor ON doctor.id_doctor=p.id_doctor
GROUP BY p.id_prescription;

select * from dept;
select * from purchase;

select * from person;
select id_person, firstname, lastname, nSS, phone, email, dob, street, op_city, name_city, dob, id_mutuelle from person
INNER JOIN address ON address.id_address=person.id_address
INNER JOIN city on city.id_city=address.id_city
WHERE person.nSS IS NOT NULL;

select id_drugs,name_drugs,name_cat,price_drugs,date_market,stock from drugs
INNER JOIN categories CA ON ca.id_cat=drugs.id_cat;

select  doctor.id_doctor, firstname, lastname, nb_agreement, phone, email, street, op_city, name_city from doctor
INNER JOIN person ON person.id_person=doctor.id_person
INNER JOIN address ON address.id_address=person.id_address
INNER JOIN city on city.id_city=address.id_city;

select * from city;
select id_address, street, op_city, name_city from address
INNER JOIN city on city.id_city=address.id_city;

select * from  mutuelle;
select mutuelle.id_mutuelle, lastname, tauxRemb, phone, email, street, op_city, name_city from mutuelle
INNER JOIN person ON person.id_person=mutuelle.id_person
INNER JOIN address ON address.id_address=person.id_address
INNER JOIN city on city.id_city=address.id_city;