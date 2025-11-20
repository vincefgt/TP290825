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

  4. **medecin** (Doctor)
     - `id_medecin` (integer, primary key) - Doctor identifier
     - `id_personne` (integer, foreign key) - Reference to person
     - `nb_agreement` (numeric(11,0)) - Agreement number

  5. **mutuelle** (Mutual Insurance)
     - `id_mutuelle` (integer, primary key) - Insurance identifier
     - `id_personne` (integer, foreign key) - Reference to person
     - `tauxRemb` (decimal(5,2)) - Reimbursement rate
     - `state` (numeric(3,0)) - State code

  6. **personne** (Person)
     - `id_personne` (integer, primary key) - Person identifier
     - `id_medecin` (integer, foreign key) - Reference to doctor
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
     - `id_personne` (integer, foreign key) - Reference to person
     - `id_medecin` (integer, foreign key) - Reference to doctor
     - `date_prescription` (timestamp) - Prescription date

  8. **medicament** (Medication)
     - `id_med` (integer, primary key) - Medication identifier
     - `nameMed` (varchar(30)) - Medication name
     - `price_med` (decimal(6,2)) - Medication price
     - `date_market` (date) - Market release date
     - `stock` (numeric(4,0)) - Stock quantity

  9. **contient** (Contains - Prescription Items)
     - `id_med` (integer, foreign key) - Reference to medication
     - `id_prescription` (integer, foreign key) - Reference to prescription
     - `quality` (numeric(3,0)) - Quantity
     - `price_unit` (decimal(6,2)) - Unit price

  10. **purchase**
      - `id_purchase` (integer, primary key) - Purchase identifier
      - `id_prescription` (integer, foreign key) - Reference to prescription
      - `id_personne` (integer, foreign key) - Reference to person
      - `date_purchase` (date) - Purchase date
      - `calc_total` (decimal(6,2)) - Total calculation
      - `calc_remb` (decimal(6,2)) - Reimbursement calculation

  11. **direct**
      - `id_purchase` (integer, foreign key) - Reference to purchase
      - `id_med` (integer, foreign key) - Reference to medication
      - `quantity` (numeric(2,0)) - Quantity
      - `price_unit` (decimal(6,2)) - Unit price

  12. **histo_update** (Update History)
      - `id_hist` (integer, primary key) - History identifier
      - `date` (timestamp) - Update timestamp
      - `old_data` (varchar(30)) - Previous data value
      - `new_data` (varchar(20)) - New data value

*/

-- Create city table
CREATE TABLE IF NOT EXISTS city (
  id_city integer PRIMARY KEY,
  op_city numeric(5,0),
  name_city varchar(20)
);

-- Create address table
CREATE TABLE IF NOT EXISTS address (
  id_address integer PRIMARY KEY,
  id_city integer,
  street varchar(40),
  CONSTRAINT fk_address_city FOREIGN KEY (id_city) REFERENCES city(id_city)
);

-- Create autorisation table
CREATE TABLE IF NOT EXISTS autorisation (
  id_autorisation integer PRIMARY KEY,
  status varchar(20)
);

-- Create medecin table
CREATE TABLE IF NOT EXISTS medecin (
  id_medecin integer PRIMARY KEY,
  id_personne integer,
  nb_agreement numeric(11,0)
);

-- Create mutuelle table
CREATE TABLE IF NOT EXISTS mutuelle (
  id_mutuelle integer PRIMARY KEY,
  id_personne integer,
  tauxRemb decimal(5,2),
  state numeric(3,0)
);

-- Create personne table
CREATE TABLE IF NOT EXISTS personne (
  id_personne integer PRIMARY KEY,
  id_medecin integer,
  id_autorisation integer,
  id_address integer,
  id_mutuelle integer,
  firstname varchar(30),
  lastname varchar(30),
  phone numeric(10,0),
  email varchar(40),
  dob date,
  CONSTRAINT fk_personne_medecin FOREIGN KEY (id_medecin) REFERENCES medecin(id_medecin),
  CONSTRAINT fk_personne_autorisation FOREIGN KEY (id_autorisation) REFERENCES autorisation(id_autorisation),
  CONSTRAINT fk_personne_address FOREIGN KEY (id_address) REFERENCES address(id_address),
  CONSTRAINT fk_personne_mutuelle FOREIGN KEY (id_mutuelle) REFERENCES mutuelle(id_mutuelle)
);

-- Add foreign key constraint to medecin after personne is created
ALTER TABLE medecin 
  DROP CONSTRAINT IF EXISTS fk_medecin_personne;
ALTER TABLE medecin 
  ADD CONSTRAINT fk_medecin_personne FOREIGN KEY (id_personne) REFERENCES personne(id_personne);

-- Add foreign key constraint to mutuelle after personne is created
ALTER TABLE mutuelle 
  DROP CONSTRAINT IF EXISTS fk_mutuelle_personne;
ALTER TABLE mutuelle 
  ADD CONSTRAINT fk_mutuelle_personne FOREIGN KEY (id_personne) REFERENCES personne(id_personne);

-- Create prescription table
CREATE TABLE IF NOT EXISTS prescription (
  id_prescription integer PRIMARY KEY,
  id_personne integer,
  id_medecin integer,
  date_prescription timestamp DEFAULT now(),
  CONSTRAINT fk_prescription_personne FOREIGN KEY (id_personne) REFERENCES personne(id_personne),
  CONSTRAINT fk_prescription_medecin FOREIGN KEY (id_medecin) REFERENCES medecin(id_medecin)
);

-- Create medicament table
CREATE TABLE IF NOT EXISTS medicament (
  id_med integer PRIMARY KEY,
  nameMed varchar(30),
  price_med decimal(6,2),
  date_market date,
  stock numeric(4,0) DEFAULT 0
);

-- Create contient table (prescription items)
CREATE TABLE IF NOT EXISTS contient (
  id_med integer,
  id_prescription integer,
  quality numeric(3,0),
  price_unit decimal(6,2),
  PRIMARY KEY (id_med, id_prescription),
  CONSTRAINT fk_contient_medicament FOREIGN KEY (id_med) REFERENCES medicament(id_med),
  CONSTRAINT fk_contient_prescription FOREIGN KEY (id_prescription) REFERENCES prescription(id_prescription)
);

-- Create purchase table
CREATE TABLE IF NOT EXISTS purchase (
  id_purchase integer PRIMARY KEY,
  id_prescription integer,
  id_personne integer,
  date_purchase date DEFAULT CURRENT_DATE,
  calc_total decimal(6,2),
  calc_remb decimal(6,2),
  CONSTRAINT fk_purchase_prescription FOREIGN KEY (id_prescription) REFERENCES prescription(id_prescription),
  CONSTRAINT fk_purchase_personne FOREIGN KEY (id_personne) REFERENCES personne(id_personne)
);

-- Create direct table (direct purchase items)
CREATE TABLE IF NOT EXISTS direct (
  id_purchase integer,
  id_med integer,
  quantity numeric(2,0),
  price_unit decimal(6,2),
  PRIMARY KEY (id_purchase, id_med),
  CONSTRAINT fk_direct_purchase FOREIGN KEY (id_purchase) REFERENCES purchase(id_purchase),
  CONSTRAINT fk_direct_medicament FOREIGN KEY (id_med) REFERENCES medicament(id_med)
);

-- Create histo_update table (audit trail)
CREATE TABLE IF NOT EXISTS histo_update (
  id_hist integer PRIMARY KEY,
  date timestamp DEFAULT now(),
  old_data varchar(30),
  new_data varchar(20)
);

-- Create indexes for performance optimization
CREATE INDEX IF NOT EXISTS idx_address_city ON address(id_city);
CREATE INDEX IF NOT EXISTS idx_personne_medecin ON personne(id_medecin);
CREATE INDEX IF NOT EXISTS idx_personne_address ON personne(id_address);
CREATE INDEX IF NOT EXISTS idx_personne_autorisation ON personne(id_autorisation);
CREATE INDEX IF NOT EXISTS idx_personne_mutuelle ON personne(id_mutuelle);
CREATE INDEX IF NOT EXISTS idx_prescription_personne ON prescription(id_personne);
CREATE INDEX IF NOT EXISTS idx_prescription_medecin ON prescription(id_medecin);
CREATE INDEX IF NOT EXISTS idx_prescription_date ON prescription(date_prescription);
CREATE INDEX IF NOT EXISTS idx_purchase_personne ON purchase(id_personne);
CREATE INDEX IF NOT EXISTS idx_purchase_date ON purchase(date_purchase);
CREATE INDEX IF NOT EXISTS idx_medicament_name ON medicament(nameMed);

-- Enable Row Level Security on all tables
ALTER TABLE city ENABLE ROW LEVEL SECURITY;
ALTER TABLE address ENABLE ROW LEVEL SECURITY;
ALTER TABLE autorisation ENABLE ROW LEVEL SECURITY;
ALTER TABLE medecin ENABLE ROW LEVEL SECURITY;
ALTER TABLE mutuelle ENABLE ROW LEVEL SECURITY;
ALTER TABLE personne ENABLE ROW LEVEL SECURITY;
ALTER TABLE prescription ENABLE ROW LEVEL SECURITY;
ALTER TABLE medicament ENABLE ROW LEVEL SECURITY;
ALTER TABLE contient ENABLE ROW LEVEL SECURITY;
ALTER TABLE purchase ENABLE ROW LEVEL SECURITY;
ALTER TABLE direct ENABLE ROW LEVEL SECURITY;
ALTER TABLE histo_update ENABLE ROW LEVEL SECURITY;

-- RLS Policies for city table
CREATE POLICY "Authenticated users can read cities"
  ON city FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert cities"
  ON city FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for address table
CREATE POLICY "Authenticated users can read addresses"
  ON address FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert addresses"
  ON address FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for autorisation table
CREATE POLICY "Authenticated users can read authorizations"
  ON autorisation FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can manage authorizations"
  ON autorisation FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for medecin table
CREATE POLICY "Authenticated users can read doctors"
  ON medecin FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert doctors"
  ON medecin FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for mutuelle table
CREATE POLICY "Authenticated users can read insurance"
  ON mutuelle FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can manage insurance"
  ON mutuelle FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for personne table
CREATE POLICY "Authenticated users can read persons"
  ON personne FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert persons"
  ON personne FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Authenticated users can update persons"
  ON personne FOR UPDATE
  TO authenticated
  USING (true)
  WITH CHECK (true);

-- RLS Policies for prescription table
CREATE POLICY "Authenticated users can read prescriptions"
  ON prescription FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can create prescriptions"
  ON prescription FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for medicament table
CREATE POLICY "Authenticated users can read medications"
  ON medicament FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can manage medications"
  ON medicament FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Authenticated users can update medications"
  ON medicament FOR UPDATE
  TO authenticated
  USING (true)
  WITH CHECK (true);

-- RLS Policies for contient table
CREATE POLICY "Authenticated users can read prescription items"
  ON contient FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can create prescription items"
  ON contient FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for purchase table
CREATE POLICY "Authenticated users can read purchases"
  ON purchase FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can create purchases"
  ON purchase FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for direct table
CREATE POLICY "Authenticated users can read direct purchases"
  ON direct FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can create direct purchases"
  ON direct FOR INSERT
  TO authenticated
  WITH CHECK (true);

-- RLS Policies for histo_update table
CREATE POLICY "Authenticated users can read update history"
  ON histo_update FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "System can insert update history"
  ON histo_update FOR INSERT
  TO authenticated
  WITH CHECK (true);