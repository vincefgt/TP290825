# Sparadrap System - Class Diagram Documentation

## Class Hierarchy and Relationships

### Core Classes

#### Person (Abstract Base Class)
```
Person
├── Client
├── Medecin
└── Mutuelle
```

#### Business Logic Classes
```
PharmacieController (Main Controller)
├── manages → Client
├── manages → Medecin
├── manages → Medicament
├── manages → Mutuelle
├── manages → Ordonnance
└── manages → Achat
```

## Detailed Class Descriptions

### 1. Person (Base Class)
**Purpose**: Abstract base class for all person-related entities
**Attributes**:
- `firstName: String` - Person's first name
- `lastName: String` - Person's last name
- `address: String` - Physical address
- `email: String` - Email address
- `nbState: int` - Postal code
- `city: String` - City name
- `phone: String` - Phone number

**Key Methods**:
- `capitalize(String): String` - Utility method for name formatting

### 2. Client (extends Person)
**Purpose**: Represents pharmacy customers
**Additional Attributes**:
- `nbSS: long` - Social Security Number (15 digits)
- `dateBirth: LocalDate` - Date of birth
- `mut: Mutuelle` - Associated insurance company
- `medecinTraitant: Medecin` - Treating doctor

**Relationships**:
- **Association** with `Mutuelle` (0..1) - A client may have insurance
- **Association** with `Medecin` (0..1) - A client may have a treating doctor
- **Composition** with `Achat` (1..*) - A client can make multiple purchases

### 3. Medecin (extends Person)
**Purpose**: Represents medical doctors
**Additional Attributes**:
- `nbAgreement: Long` - Medical agreement number (11 digits)
- `patients: List<Client>` - List of patients
- `idMedecin: String` - Unique doctor identifier

**Relationships**:
- **Association** with `Client` (1..*) - A doctor can have multiple patients
- **Composition** with `Ordonnance` (1..*) - A doctor creates prescriptions

### 4. Mutuelle (extends Person)
**Purpose**: Represents insurance companies
**Additional Attributes**:
- `dep: Dep` - Department (enum)
- `tauxRemb: double` - Reimbursement rate (0-100%)

**Key Methods**:
- `calcRemb(double): double` - Calculate reimbursement amount

**Relationships**:
- **Association** with `Client` (1..*) - An insurance can cover multiple clients
- **Association** with `Dep` (1..1) - Each insurance belongs to a department

### 5. Medicament
**Purpose**: Represents pharmaceutical products
**Attributes**:
- `nameMed: String` - Medication name
- `cat: catMed` - Category (enum)
- `price: double` - Unit price
- `datOnMarket: LocalDate` - Market introduction date
- `stock: int` - Available quantity

**Key Methods**:
- `reduireQuantite(int): boolean` - Reduce stock quantity

**Relationships**:
- **Association** with `catMed` (1..1) - Each medication has a category
- **Association** with `Ordonnance` (*..*) - Many-to-many relationship
- **Association** with `Achat` (*..*) - Many-to-many relationship

### 6. Ordonnance
**Purpose**: Represents medical prescriptions
**Attributes**:
- `dateOrdo: LocalDate` - Prescription date
- `patient: Client` - Patient receiving prescription
- `medecin: Medecin` - Prescribing doctor
- `listMedOrdo: List<Medicament>` - Prescribed medications

**Relationships**:
- **Association** with `Client` (1..1) - Each prescription belongs to one client
- **Association** with `Medecin` (1..1) - Each prescription is written by one doctor
- **Composition** with `Medicament` (1..*) - A prescription contains medications

### 7. Achat
**Purpose**: Represents purchase transactions
**Attributes**:
- `dateAchat: LocalDate` - Purchase date
- `client: Client` - Purchasing client
- `listMedAchat: List<Medicament>` - Purchased medications
- `ordonnance: Ordonnance` - Associated prescription (nullable for direct purchases)
- `Total: double` - Total amount
- `Remb: double` - Reimbursement amount

**Key Methods**:
- `calMontants(): void` - Calculate total and reimbursement amounts
- `IsAchatDirect(Ordonnance): boolean` - Check if purchase is direct or prescription-based

**Relationships**:
- **Association** with `Client` (1..1) - Each purchase belongs to one client
- **Association** with `Ordonnance` (0..1) - Purchase may be based on prescription
- **Composition** with `Medicament` (1..*) - A purchase contains medications

### 8. PharmacieController
**Purpose**: Main business logic controller
**Static Attributes**:
- `listClients: List<Client>` - All registered clients
- `listMedecins: List<Medecin>` - All registered doctors
- `listMed: List<Medicament>` - All medications
- `listMutuelles: List<Mutuelle>` - All insurance companies
- `listOrdonnances: List<Ordonnance>` - All prescriptions
- `listAchats: List<Achat>` - All purchases

**Key Methods**:
- `addClient(Client): boolean` - Add new client
- `addMedecin(Medecin): boolean` - Add new doctor
- `addMed(Medicament): boolean` - Add new medication
- `createNewAchatWithOrdonnance(...)` - Create prescription-based purchase
- `savingAchat(Achat): boolean` - Process and save purchase
- `formatTwoDec(double): double` - Format currency to 2 decimal places

## Enumerations

### catMed
**Purpose**: Medication categories
**Values**: ANTALGIQUE, ANALGESIQUE, ANTIINFLAMMATOIRE, ANTIBIOTIQUE, ANTIVIRAL, VITAMINE, ANTIDEPRESSEUR, DIABETIQUE, etc.

### Dep
**Purpose**: French departments
**Attributes**:
- `codeDep: int` - Department code
- `nameDep: String` - Department name

### DateFilter
**Purpose**: Date filtering options for reports
**Values**: TODAY, THIS_WEEK, THIS_MONTH, THIS_YEAR, ALL_TIME, etc.

## Design Patterns Used

### 1. Singleton Pattern
- `PharmacieController` uses static lists to ensure single instance of data

### 2. Factory Pattern
- Object creation is centralized in controller methods

### 3. Observer Pattern
- UI components observe data changes through controller

### 4. Strategy Pattern
- Different calculation strategies for insurance reimbursement

## Key Relationships Summary

1. **Inheritance Hierarchy**:
   - `Person` → `Client`, `Medecin`, `Mutuelle`

2. **Composition Relationships**:
   - `Achat` contains `Medicament` objects
   - `Ordonnance` contains `Medicament` objects

3. **Association Relationships**:
   - `Client` ↔ `Mutuelle` (0..1 to 1..*)
   - `Client` ↔ `Medecin` (0..1 to 1..*)
   - `Ordonnance` → `Client` (1..1)
   - `Ordonnance` → `Medecin` (1..1)
   - `Achat` → `Client` (1..1)
   - `Achat` → `Ordonnance` (0..1)

4. **Aggregation Relationships**:
   - `PharmacieController` aggregates all entity lists

## Data Flow

1. **Client Registration**: Person → Client → PharmacieController
2. **Prescription Creation**: Medecin + Client → Ordonnance → PharmacieController
3. **Purchase Processing**: Client + (Ordonnance | Medicament) → Achat → PharmacieController
4. **Stock Management**: Achat → Medicament.reduireQuantite()
5. **Reimbursement Calculation**: Achat → Mutuelle.calcRemb()