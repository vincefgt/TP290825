/*
  # Pharmacy Management Database Schema
  ## Overview
  Complete database schema for a pharmacy management system including:
  - Patient and doctor management
  - Prescription and medication tracking
  - Purchase history
  - Authorization and insurance
  - Audit trail for data updates

  ## New Tables
  1. **city**
     - `id_city` (integer, primary key) - Unique city identifier
     - `op_city` (numeric(5,0)) - City operation code
     - `name_city` (varchar(20)) - City name

  2. **address**
     - `id_address` (integer, primary key) - Unique address identifier
     - `id_city` (integer, foreign key) - Reference to city
     - `street` (varchar(40)) - Street name

  3. **autorisation**
     - `id_autorisation` (integer, primary key) - Authorization identifier
     - `status` (varchar(20)) - Authorization status

  4. **doctor**
     - `id_doctor` (integer, primary key) - Doctor identifier
     - `id_person` (integer, foreign key) - Reference to person
     - `nb_agreement` (numeric(11,0)) - Agreement number

  5. **mutuelle** (Mutual Insurance)
     - `id_mutuelle` (integer, primary key) - Insurance identifier
     - `id_person` (integer, foreign key) - Reference to person
     - `tauxRemb` (decimal(5,2)) - Reimbursement rate
     - `state` (numeric(3,0)) - State code

  6. **person**
     - `id_person` (integer, primary key) - Person identifier
     - `id_doctor` (integer, foreign key) - Reference to doctor
     - `id_autorisation` (integer, foreign key) - Reference to authorization
     - `id_address` (integer, foreign key) - Reference to address
     - `id_mutuelle` (integer, foreign key) - Reference to insurance
     - `firstname` (varchar(30)) - First name
     - `lastname` (varchar(30)) - Last name
     - `phone` (numeric(10,0)) - Phone number
     - `email` (varchar(40)) - Email address
     - `dob` (date) - Date of birth

  7. **prescription**
     - `id_prescription` (integer, primary key) - Prescription identifier
     - `id_person` (integer, foreign key) - Reference to person
     - `id_doctor` (integer, foreign key) - Reference to doctor
     - `date_prescription` (timestamp) - Prescription date

  8. **drugs**
     - `id_med` (integer, primary key) - Medication identifier
     - `nameMed` (varchar(30)) - Medication name
     - `price_med` (decimal(6,2)) - Medication price
     - `date_market` (date) - Market release date
     - `stock` (numeric(4,0)) - Stock quantity

  9. **contains** (Prescription Items)
     - `id_med` (integer, foreign key) - Reference to medication
     - `id_prescription` (integer, foreign key) - Reference to prescription
     - `quality` (numeric(3,0)) - Quantity
     - `price_unit` (decimal(6,2)) - Unit price

  10. **purchase**
      - `id_purchase` (integer, primary key) - Purchase identifier
      - `id_prescription` (integer, foreign key) - Reference to prescription
      - `id_person` (integer, foreign key) - Reference to person
      - `date_purchase` (date) - Purchase date
      - `calc_total` (decimal(6,2)) - Total calculation
      - `calc_remb` (decimal(6,2)) - Reimbursement calculation

  11. **direct**
      - `id_purchase` (integer, foreign key) - Reference to purchase
      - `id_med` (integer, foreign key) - Reference to medication
      - `quantity` (numeric(2,0)) - Quantity
      - `price_unit` (decimal(6,2)) - Unit price

  12. **histo_update**
      - `id_hist` (integer, primary key) - History identifier
      - `date` (timestamp) - Update timestamp
      - `old_data` (varchar(30)) - Previous data value
      - `new_data` (varchar(20)) - New data value

*/
-- Create database
DROP DATABASE IF EXISTS Sparadrap;
CREATE DATABASE Sparadrap; 
USE Sparadrap;

-- Create city table
CREATE TABLE IF NOT EXISTS city (
  id_city integer PRIMARY KEY AUTO_INCREMENT,
  op_city numeric(5,0),
  name_city varchar(20)
);

-- Create address table
CREATE TABLE IF NOT EXISTS address (
  id_address integer PRIMARY KEY AUTO_INCREMENT,
  id_city integer,
  street varchar(40),
  CONSTRAINT fk_address_city FOREIGN KEY (id_city) REFERENCES city(id_city) ON DELETE CASCADE
);

-- Create autorisation table
CREATE TABLE IF NOT EXISTS autorisation (
  id_autorisation integer PRIMARY KEY AUTO_INCREMENT,
  status varchar(20)
);

-- Create doctor table
CREATE TABLE IF NOT EXISTS doctor (
  id_doctor integer PRIMARY KEY AUTO_INCREMENT,
  id_person integer,
  nb_agreement numeric(11,0)
);

-- Create mutuelle table
CREATE TABLE IF NOT EXISTS mutuelle (
  id_mutuelle integer PRIMARY KEY AUTO_INCREMENT,
  id_person integer,
  tauxRemb decimal(5,2),
  state numeric(3,0)
);

-- Create person table
CREATE TABLE IF NOT EXISTS person (
  id_person integer PRIMARY KEY AUTO_INCREMENT,
  id_doctor integer,
  id_autorisation integer,
  id_address integer,
  id_mutuelle integer,
  firstname varchar(30),
  lastname varchar(30),
  nSS NUMERIC(15,0),
  phone varchar(10),
  email varchar(40),
  dob date,
  CONSTRAINT fk_person_doctor FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor) ON DELETE SET NULL,
  CONSTRAINT fk_person_autorisation FOREIGN KEY (id_autorisation) REFERENCES autorisation(id_autorisation),
  CONSTRAINT fk_person_address FOREIGN KEY (id_address) REFERENCES address(id_address) ON DELETE SET NULL,
  CONSTRAINT fk_person_mutuelle FOREIGN KEY (id_mutuelle) REFERENCES mutuelle(id_mutuelle) ON DELETE SET NULL
);


-- Create prescription table
CREATE TABLE IF NOT EXISTS prescription (
  id_prescription integer PRIMARY KEY AUTO_INCREMENT,
  id_person integer,
  id_doctor integer,
  date_prescription timestamp DEFAULT now(),
  CONSTRAINT fk_prescription_person FOREIGN KEY (id_person) REFERENCES person(id_person),
  CONSTRAINT fk_prescription_doctor FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor)
);

CREATE TABLE IF NOT EXISTS categories(
	id_cat integer PRIMARY KEY AUTO_INCREMENT,
    name_cat VARCHAR(30)
);

-- Create drugs table
CREATE TABLE IF NOT EXISTS drugs (
  id_drugs integer PRIMARY KEY AUTO_INCREMENT,
  name_drugs varchar(30),
  id_cat integer,
  price_drugs decimal(6,2),
  date_market date,
  stock numeric(4,0) DEFAULT 0,
CONSTRAINT fk_drugs_cat FOREIGN KEY (id_cat) REFERENCES categories(id_cat)
);

-- Create contains table (prescription items)
CREATE TABLE IF NOT EXISTS contains (
  id_drugs integer,
  id_prescription integer,
  quatity numeric(3,0),
  price_unit decimal(6,2),
  PRIMARY KEY (id_drugs, id_prescription),
  CONSTRAINT fk_contains_drugs FOREIGN KEY (id_drugs) REFERENCES drugs(id_drugs),
  CONSTRAINT fk_contains_prescription FOREIGN KEY (id_prescription) REFERENCES prescription(id_prescription)
);

-- Create purchase table
CREATE TABLE IF NOT EXISTS purchase (
  id_purchase integer PRIMARY KEY AUTO_INCREMENT,
  id_prescription integer,
  id_person integer,
  date_purchase date,
  calc_total decimal(6,2),
  calc_remb decimal(6,2),
  CONSTRAINT fk_purchase_prescription FOREIGN KEY (id_prescription) REFERENCES prescription(id_prescription),
  CONSTRAINT fk_purchase_person FOREIGN KEY (id_person) REFERENCES person(id_person)
);

-- Create direct table (direct purchase items)
CREATE TABLE IF NOT EXISTS direct (
  id_purchase integer,
  id_drugs integer,
  quantity numeric(2,0),
  price_unit decimal(6,2),
  PRIMARY KEY (id_purchase, id_drugs),
  CONSTRAINT fk_direct_purchase FOREIGN KEY (id_purchase) REFERENCES purchase(id_purchase),
  CONSTRAINT fk_direct_drugs FOREIGN KEY (id_drugs) REFERENCES drugs(id_drugs)
);

-- Create histo_update table (audit trail)
CREATE TABLE IF NOT EXISTS histo_update (
  id_hist integer PRIMARY KEY AUTO_INCREMENT,
  date timestamp DEFAULT now(),
  old_data varchar(30),
  new_data varchar(20)
);

CREATE TABLE IF NOT EXISTS dept(
	id_dept integer PRIMARY KEY,
    name_dept VARCHAR(30)
);

-- Add foreign key constraint to doctor after person is created
ALTER TABLE doctor
  ADD CONSTRAINT fk_doctor_person2 FOREIGN KEY (id_person) REFERENCES person(id_person)
  ON DELETE SET NULL;

-- Add foreign key constraint to mutuelle after person is created
ALTER TABLE mutuelle
  ADD CONSTRAINT fk_mut_per FOREIGN KEY (id_person) REFERENCES person(id_person)
  ON DELETE SET NULL;

-- lower case firstname lastname --
DELIMITER $$
CREATE TRIGGER lowerCaseLastnameInsert
BEFORE INSERT ON person
FOR EACH ROW
BEGIN
SET NEW.lastname = LOWER(NEW.lastname);
SET NEW.firstname = LOWER(NEW.firstname);
END $$
DELIMITER ;

-- lower case firstname lastname --
DELIMITER $$
CREATE TRIGGER lowerCaseLastnameUpdate
BEFORE UPDATE ON person
FOR EACH ROW
BEGIN
SET NEW.lastname = LOWER(NEW.lastname);
SET NEW.firstname = LOWER(NEW.firstname);
END $$
DELIMITER ;

  -- Insert deo
INSERT INTO dept (id_dept, name_dept) VALUES
  (1, 'Ain'), (2, 'Aisne'),(3, 'Allier'),(4, 'Alpes-de-Haute-Provence'),(5, 'Hautes-Alpes'),(6, 'Alpes-Maritimes'),
  (7, 'Ardèche'),(8, 'Ardennes'),(9, 'Ariège'),(10, 'Aube'),(11, 'Aude'),(12, 'Aveyron'),(13, 'Bouches-du-Rhône'),
  (14, 'Calvados'),(15, 'Cantal'),(16, 'Charente'),(17, 'Charente-Maritime'),(18, 'Cher'),(19, 'Corrèze'),
  (201, 'Corse-du-Sud'),(202, 'Haute-Corse'),(21, 'Côte-d''Or'),(22, 'Côtes-d''Armor'),(23, 'Creuse'),(24, 'Dordogne'),
  (25, 'Doubs'),(26, 'Drôme'),(27, 'Eure'),(28, 'Eure-et-Loir'),(29, 'Finistère'),(30, 'Gard'),(31, 'Haute-Garonne'),
  (32, 'Gers'),(33, 'Gironde'),(34, 'Hérault'),(35, 'Ille-et-Vilaine'),(36, 'Indre'),(37, 'Indre-et-Loire'),
  (38, 'Isère'),(39, 'Jura'),(40, 'Landes'),(41, 'Loir-et-Cher'),(42, 'Loire'),(43, 'Haute-Loire'),(44, 'Loire-Atlantique'),
  (45, 'Loiret'),(46, 'Lot'),(47, 'Lot-et-Garonne'),(48, 'Lozère'),(49, 'Maine-et-Loire'),(50, 'Manche'),
  (51, 'Marne'),(52, 'Haute-Marne'),(53, 'Mayenne'),(54, 'Meurthe-et-Moselle'),(55, 'Meuse'),(56, 'Morbihan'),
  (57, 'Moselle'),(58, 'Nièvre'),(59, 'Nord'),(60, 'Oise'),(61, 'Orne'),(62, 'Pas-de-Calais'),(63, 'Puy-de-Dôme'),
  (64, 'Pyrénées-Atlantiques'),(65, 'Hautes-Pyrénées'),(66, 'Pyrénées-Orientales'),(67, 'Bas-Rhin'),(68, 'Haut-Rhin'),
  (69, 'Rhône'),(70, 'Haute-Saône'),(71, 'Saône-et-Loire'),(72, 'Sarthe'),(73, 'Savoie'),(74, 'Haute-Savoie'),
  (75, 'Paris'),(76, 'Seine-Maritime'),(77, 'Seine-et-Marne'),(78, 'Yvelines'),(79, 'Deux-Sèvres'),(80, 'Somme'),
  (81, 'Tarn'),(82, 'Tarn-et-Garonne'),(83, 'Var'),(84, 'Vaucluse'),(85, 'Vendée'),(86, 'Vienne'),(87, 'Haute-Vienne'),
  (88, 'Vosges'),(89, 'Yonne'),(90, 'Territoire de Belfort'),(91, 'Essonne'),(92, 'Hauts-de-Seine'),(93, 'Seine-Saint-Denis'),
  (94, 'Val-de-Marne'),(95, 'Val-d''Oise'),(971, 'Guadeloupe'),(972, 'Martinique'),(973, 'Guyane'),(974, 'La Réunion'),
  (976, 'Mayotte');

-- Insert cities
INSERT IGNORE INTO city (id_city, op_city, name_city) VALUES
  (1, 75000, 'Paris'),(2, 69000, 'Lyon'),(3, 54000, 'Nancy');

-- Insert addresses
INSERT IGNORE INTO address (id_address, id_city, street) VALUES
  (1, 1, '10 Rue Médicale'),(2, 1, '123 Rue de la République'),
  (3, 2, '25 Avenue Santé'),(4, 2, '456 Boulevard Voltaire'),(5, 3, '12 Rue de la République'),(6, 1, '89 Avenue Victor Hugo'),(7, 3, '27 Boulevard Saint-Michel'),
(8, 1, '63 Rue du Faubourg Saint-Antoine'),(9, 3, '102 Avenue de la Gare'),(10, 3, '7 Rue des Lilas'),(11, 3, '45 Boulevard Haussmann'),
(12, 3, '33 Rue de la Paix'),(13, 2, '19 Avenue des Champs-Élysées'),(14, 1, '56 Rue de la Liberté'),(15, 2, '90 Boulevard Pasteur'),
(16, 3, '11 Rue du Moulin'),(17, 3, '73 Avenue Jean Jaurès'),(18, 3, '120 Boulevard de la Mer'),(19, 3, '62 Rue des Écoles'),
(20, 3, '31 Avenue du Parc'),(21, 3, '98 Rue de la Fontaine'),(22, 3, '24 Boulevard Gambetta'),(23, 3, '15 Rue du Château');

-- Insert authorizations
INSERT IGNORE INTO autorisation (id_autorisation, status) VALUES
  (1, 'ACTIVE'),(2, 'ACTIVE');

-- Insert  person
INSERT IGNORE INTO person (id_person, id_doctor, id_autorisation, id_address, id_mutuelle, firstname, lastname, nSs, phone, email, dob) VALUES
  -- patient --
  (1, NULL, 1, 1, NULL, 'Pierre', 'Dupont', 178054501234567, '0140506070', 'dr.dupont@hopital.fr', '2005-04-24'),
  (2, NULL, 1, 3, NULL, 'Paul', 'Martin', 265032145678901, '0472345678', 'dr.martin@clinique.fr', '2010-06-09'),
  (3, NULL, 1, 2, NULL, 'Jean', 'Durand', 153116789012345, '0111223344', 'jean.durand@email.com', '1980-06-15'),
  (4, NULL, 1, 4, NULL, 'Marie', 'Moreau', 282097823456789, '0455667788', 'marie.moreau@email.com', '1975-03-22'),
  (5, NULL, 1, 5, NULL, 'Pierre', 'Moreau', 169081234567890, '4455667788', 'pierre.moreau@email.com', '1975-03-22'),
  (6, NULL, 1, 6, NULL, 'Lucas', 'Bernard', 254029456789012, '6123456789', 'lucas.bernard@email.com', '1988-07-10'),
  (7, NULL, 1, 7, NULL, 'Camille', 'Dubois', 171103678901234, '6748392011', 'camille.dubois@email.com', '1992-01-15'),
  -- doctors --
  (8, NULL, 1, 8, NULL, 'Julien', 'Lefèvre', NULL, '6893201457', 'julien.lefevre@email.com', '1985-09-03'),
  (9, NULL, 1, 9, NULL, 'Chloé', 'Martin', NULL, '6012345678', 'chloe.martin@email.com', '1990-04-28'),
  (10, NULL, 1, 10, NULL, 'Thomas', 'Laurent', NULL, '6987456321', 'thomas.laurent@email.com', '1983-11-19'),
  (11, NULL, 1, 11, NULL, 'Élise', 'Garcia', NULL, '6723410987', 'elise.garcia@email.com', '1995-05-07'),
  (12, NULL, 1, 12, NULL, 'Antoine', 'Roux', NULL, '6778899001', 'antoine.roux@email.com', '1979-08-16'),
  (13, NULL, 1, 13, NULL, 'Sophie', 'Petit', NULL, '6034587219', 'sophie.petit@email.com', '1986-02-25'),
-- mutuelles --
  (14, NULL, 1, 14, NULL, 'Mutuelle', 'MALAKOFF HUMANIS', NULL, 6234567890, 'info@malakoff-humanis.com', NULL),
  (15, NULL, 1, 15, NULL, 'Mutuelle', 'MUTEX', NULL, 6123456789, 'contact@mutex.fr', NULL),
  (16, NULL, 1, 16, NULL, 'Mutuelle', 'APRIL', NULL, 6987654321, 'service@april.fr', NULL),
  (17, NULL, 1, 17, NULL, 'Mutuelle', 'PACIFICA', NULL, 6876543210, 'hello@pacifica.fr', NULL),
  (18, NULL, 1, 18, NULL, 'Mutuelle', 'ALLIANZ', NULL, 6765432198, 'contact@allianz.fr', NULL),
  (19, NULL, 1, 19, NULL, 'Mutuelle', 'AXA', NULL, 6654321987, 'support@axa.fr', NULL),
  (21, NULL, 1, 21, NULL, 'Mutuelle', 'AESIO', NULL, 6998765432, 'bonjour@aesio.com', NULL),
  (22, NULL, 1, 22, NULL, 'Mutuelle', 'SWISSLIFE', NULL, 6765432109, 'contact@swisslife.ch', NULL),
  (23, NULL, 1, 23, NULL, 'Mutuelle', 'HARMONIE', NULL, 6678901234, 'hello@harmonie.com', NULL),
  (24, NULL, 1, 22, NULL, 'Mutuelle', 'MGEN', NULL, 6345678901, 'contact@mgen.com', NULL);

  -- Insert mutual insurance companies (mutuelles)
INSERT IGNORE INTO mutuelle (id_mutuelle, id_person, tauxRemb, state) VALUES
  (1, 22, 60.0, 1),(2, 23, 70.0, 1),(3,24,50.0,54),(4,21,99.0,57),
  (5, 15, 62.0, 1),(6, 16, 78.0, 1),(7,17,58.0,54),(8,18,65.0,88),
  (9, 19, 54.0, 51);

-- Insert doctors
INSERT IGNORE INTO doctor (id_doctor, id_person, nb_agreement) VALUES
  (1, 8, 12345678910),(2, 9, 98765432110),(3, 10, 18976543210),(4, 11, 27865432109),
  (5, 12, 36754321098),(6, 13, 45643210987);

-- Update clients to reference their doctors
UPDATE person SET id_doctor = 1 WHERE id_person = 3;
UPDATE person SET id_doctor = 2 WHERE id_person = 4;

-- Insert categories
INSERT IGNORE INTO categories (id_cat, name_cat) VALUES
  (1, 'ANTALGIQUE'),(2, 'ANALGESIQUE'), -- Soulage la douleur
  (3, 'ANTIINFLAMMATOIRE'), -- Réduit l'inflammation
  (4, 'ANTIBIOTIQUE'),    -- Contre les infections bactériennes
  (5, 'ANTIVIRAL'),        -- Contre les infections virales
  (6, 'VITAMINE'),         -- Compléments vitaminiques
  (7, 'ANTIDEPRESSEUR'),   -- Pour la dépression
  (8, 'DIABETIQUE'),    -- Pour le diabète
  (9, 'ANTIBACTERIENS'),(10, 'ANTITUBERCULEUX'),(11, 'ANTILEPREUX'),(12, 'ANTIMYCOSIQUE'),(13, 'CARDIOLOGIE'),
  (14, 'DERMATOLOGIE'),(15, 'NUTRITION'),(16, 'ENDOCRINOLOGIE'),(17, 'GASTROENTEROLOGIE'),(18, 'HEPATOLOGIE'),
  (19, 'GYNECOLOGIE_CONTRACEPTION'),(20, 'HEMATOLOGIE'),(21, 'IMMUNOLOGIE'),(22, 'ALLERGOLOGIE'),(23, 'NEUROLOGIE'),
  (24, 'OPHTALMOLOGIE'),(25, 'PARASITOLOGIE'),(26, 'OTORHINOLARYNGOLOGIE'),(27, 'PNEUMOLOGIE'),
  (28, 'PSYCHIATRIE'),(29, 'RHUMATOLOGIE'),(30, 'UROLOGIE'),(31, 'STOMATOLOGIE'),(32, 'VACCINS'),(33, 'CANCEROLOGIE');

-- Insert drugs
INSERT IGNORE INTO drugs (id_drugs, name_drugs, id_cat, price_drugs, date_market, stock) VALUES
(1, 'Doliprane 1000mg', 1, 15.20, '2023-01-01', 50),
(2, 'Aspirine 500mg', 2, 3.80, '2024-09-01', 30),
(3, 'Ibuprofène 200mg', 3, 4.50, '2025-01-01', 25),
(4, 'Vitamine C', NULL, 8.90, '2023-01-01', 40),
(5, 'Paracétamol 500mg', 1, 6.30, '2023-01-23', 60),
(6, 'Amoxicilline 1g', 4, 12.70, '2023-01-01', 35),
(7, 'Efferalgan 500mg', 1, 5.10, '2023-01-01', 55),
(8, 'Spasfon Lyoc', 5, 7.80, '2023-01-01', 28),
(9, 'Doliprane Codéiné', 1, 18.40, '2023-01-01', 15),
(10, 'Nurofen 400mg', 3, 9.60, '2023-11-01', 32),
(11, 'Gaviscon Menthe', 6, 11.20, '2023-01-01', 42),
(12, 'Smecta', 6, 8.10, '2023-01-01', 27),
(13, 'Imodium', 6, 10.90, '2023-01-01', 33),
(14, 'Clarix Rhume', 7, 6.70, '2023-01-01', 38),
(15, 'Strepsils Miel Citron', 7, 5.90, '2022-01-01', 45),
(16, 'Dolirhume Paracétamol', 7, 7.40, '2023-01-01', 22),
(17, 'Biafine', 8, 9.20, '2023-01-01', 41),
(18, 'Rhinadvil 400mg', 3, 8.80, '2023-01-01', 26),
(19, 'Magnesium B6', NULL, 11.50, '2023-01-01', 37),
(20, 'Omeprazole 20mg', 9, 14.30, '2023-01-01', 29);

-- Insert prescription
INSERT IGNORE INTO prescription (id_prescription, id_person, id_doctor, date_prescription) VALUES
	(1,5,2,'2025-01-01'),(2,2,1,'2023-10-25'),(3,6,2,'2022-03-18');

-- Insert contains
INSERT IGNORE INTO `contains`(id_drugs, id_prescription, quatity, price_unit) VALUES
	(16,1,2,null),
    (12,1,3,null),
    (7,1,1,null),
    (4,3,1,null),
    (9,2,5,null);

UPDATE `contains` c
INNER JOIN drugs d ON c.id_drugs = d.id_drugs
SET c.price_unit = d.price_drugs
WHERE c.price_unit IS NULL;

DELIMITER $
CREATE PROCEDURE updatePerson(
  IN pid_person integer,
--  IN pid_autorisation integer,
  IN pfirstname varchar(30),
  IN plastname varchar(30),
  IN pnSS NUMERIC(15,0),
  IN pphone varchar(10),
  IN pemail varchar(40),
  IN pdob date,
  IN pmut integer
  )
BEGIN
START TRANSACTION;
SELECT * FROM person
WHERE id_person=pid_person
LOCK IN SHARE MODE; -- FOR UPDATE;
UPDATE person
SET firstname=pfirstname,
	lastname=plastname,
  --  id_autorisation=pid_autorisation,
    nSS=pnSS,
    phone=pphone,
    email=pemail,
    dob=DATE_FORMAT(pdob,'%Y-%m-%d'),
    id_mutuelle=pmut
WHERE id_person=pid_person;
COMMIT;
END $
DELIMITER ;

