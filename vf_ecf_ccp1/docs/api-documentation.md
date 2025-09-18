# Sparadrap API Documentation

## PharmacieController API Reference

### Client Management

#### addClient(Client client)
```java
public static boolean addClient(Client client)
```
**Description**: Adds a new client to the pharmacy system.

**Parameters**:
- `client` - The client object to add (must not be null or already exist)

**Returns**: `true` if client was successfully added, `false` otherwise

**Example**:
```java
Client newClient = new Client("John", "Doe", ...);
boolean success = PharmacieController.addClient(newClient);
```

#### SearchSS(long numeroSS)
```java
public Client SearchSS(long numeroSS)
```
**Description**: Searches for a client by social security number.

**Parameters**:
- `numeroSS` - 15-digit social security number

**Returns**: Client object if found, `null` otherwise

---

### Doctor Management

#### addMedecin(Medecin medecin)
```java
public static boolean addMedecin(Medecin medecin)
```
**Description**: Adds a new doctor to the system.

**Parameters**:
- `medecin` - The doctor object to add

**Returns**: `true` if doctor was successfully added, `false` otherwise

#### searchAgreement(long nbAgreement)
```java
public Medecin searchAgreement(long nbAgreement)
```
**Description**: Searches for a doctor by agreement number.

**Parameters**:
- `nbAgreement` - 11-digit medical agreement number

**Returns**: Medecin object if found, `null` otherwise

---

### Medication Management

#### addMed(Medicament medicament)
```java
public static boolean addMed(Medicament medicament)
```
**Description**: Adds a new medication to inventory.

**Parameters**:
- `medicament` - The medication object to add

**Returns**: `true` if medication was successfully added, `false` otherwise

#### searchLastName(String lastName)
```java
public Medicament searchLastName(String lastName)
```
**Description**: Searches for a medication by name (case-insensitive).

**Parameters**:
- `lastName` - Medication name to search for

**Returns**: Medicament object if found, `null` otherwise

---

### Purchase Management

#### createNewAchatWithOrdonnance(LocalDate dateAchat, Client client, LocalDate dateOrdo, Medecin medecin)
```java
public static boolean createNewAchatWithOrdonnance(
    LocalDate dateAchat, 
    Client client, 
    LocalDate dateOrdo, 
    Medecin medecin
)
```
**Description**: Creates a new prescription-based purchase.

**Parameters**:
- `dateAchat` - Purchase date
- `client` - Client making the purchase
- `dateOrdo` - Prescription date
- `medecin` - Prescribing doctor

**Returns**: `true` if purchase was successfully created, `false` otherwise

#### savingAchat(Achat achat)
```java
public static boolean savingAchat(Achat achat)
```
**Description**: Processes and saves a purchase transaction.

**Parameters**:
- `achat` - The purchase object to process

**Returns**: `true` if purchase was successfully processed, `false` otherwise

**Side Effects**:
- Reduces medication stock levels
- Calculates total amounts and reimbursements
- Validates stock availability

---

### Utility Methods

#### formatTwoDec(double nb)
```java
public static double formatTwoDec(double nb)
```
**Description**: Formats a double value to 2 decimal places for currency display.

**Parameters**:
- `nb` - Number to format

**Returns**: Formatted number with 2 decimal places

---

## Model Classes API

### Client Class

#### Constructor
```java
public Client(String firstName, String lastName, String address, 
              int nbState, String city, String phone, String email, 
              long nbSS, LocalDate dateBirth, Mutuelle mut, 
              Medecin medecinTraitant)
```

#### Key Methods
- `getNbSS()`: Returns social security number
- `setNbSS(long)`: Sets social security number (validates 15 digits)
- `getDateBirth()`: Returns formatted birth date
- `getMutuelle()`: Returns associated insurance
- `getMedecinTraitant()`: Returns treating doctor

### Medicament Class

#### Constructor
```java
public Medicament(String nameMed, catMed cat, double price, 
                  String datOnMarket, int stock)
```

#### Key Methods
- `reduireQuantite(int quantiteVendue)`: Reduces stock by specified amount
- `getStock()`: Returns current stock level
- `getPrice()`: Returns medication price
- `getCat()`: Returns medication category

### Achat Class

#### Constructors
```java
// Direct purchase
public Achat(LocalDate dateAchat, Client client)

// Prescription-based purchase
public Achat(LocalDate dateAchat, Client client, Ordonnance ordonnance)
```

#### Key Methods
- `calMontants()`: Calculates total and reimbursement amounts
- `addMedAchat(Medicament)`: Adds medication to purchase
- `IsAchatDirect(Ordonnance)`: Checks if purchase is direct or prescription-based

---

## Exception Handling

### InputException
```java
public class InputException extends RuntimeException
```
**Usage**: Thrown for immediate input validation failures

### Business Exceptions
- `AchatException`: Purchase processing errors
- `ClientException`: Client management errors
- `OrdonnanceException`: Prescription validation errors
- `StockException`: Inventory management errors

---

## Validation API (Regex Class)

### Common Validation Methods
```java
public static boolean testNotEmpty(T input)
public static boolean testEmail(String input)
public static boolean testChar(String input)
public static boolean testDigitLong(long input)
public static boolean testDate(LocalDate input)
```

**Note**: All validation methods return `true` if validation **fails** (error found).

### Configuration
```java
public static void setParamRegex(String pattern)
```
**Description**: Sets the regex pattern for digit validation methods.