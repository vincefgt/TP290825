# Sparadrap System Architecture Documentation

## 1. System Overview

The Sparadrap Pharmacy Management System is a comprehensive Java-based application designed to manage all aspects of pharmacy operations. The system follows a layered architecture with clear separation of concerns.

## 2. Architecture Layers

### 2.1 Presentation Layer (View)
- **Package**: `view`
- **Responsibility**: User interface and user interaction
- **Key Components**:
  - `SparadrapMainInterface` - Main GUI application
  - `SparadrapLauncher` - Application startup and initialization
  - `PharmacieView` - Console-based interface for testing
  - `SparadrapDialogs` - Modal dialogs for data entry

### 2.2 Business Logic Layer (Controller)
- **Package**: `controller`
- **Responsibility**: Business rules and application logic
- **Key Components**:
  - `PharmacieController` - Main business logic controller
  - `Regex` - Input validation utilities
  - `DateFilter` - Date filtering enumeration

### 2.3 Data Model Layer (Model)
- **Package**: `model`
- **Responsibility**: Domain entities and data structures
- **Key Components**:
  - `Person` (abstract) - Base class for all person entities
  - `Client`, `Medecin`, `Mutuelle` - Person specializations
  - `Medicament` - Pharmaceutical products
  - `Ordonnance` - Medical prescriptions
  - `Achat` - Purchase transactions

### 2.4 Exception Handling Layer
- **Package**: `Exception`
- **Responsibility**: Custom exception handling
- **Key Components**:
  - `InputException` - Input validation errors
  - `AchatException` - Purchase-related errors
  - `ClientException` - Client management errors
  - `OrdonnanceException` - Prescription errors
  - `StockException` - Inventory errors

## 3. Design Patterns

### 3.1 Model-View-Controller (MVC)
- **Model**: Domain entities in `model` package
- **View**: UI components in `view` package
- **Controller**: Business logic in `controller` package

### 3.2 Singleton Pattern
- `PharmacieController` uses static collections for centralized data management
- Ensures single source of truth for all pharmacy data

### 3.3 Factory Pattern
- Object creation methods in `PharmacieController`
- Centralized object instantiation and validation

### 3.4 Strategy Pattern
- Different validation strategies in `Regex` class
- Configurable validation patterns

### 3.5 Observer Pattern
- UI components observe data changes through controller
- Automatic UI updates when data changes

## 4. Data Flow

### 4.1 Client Registration Flow
```
User Input → View → Controller → Validation → Model → Storage
```

### 4.2 Purchase Processing Flow
```
Purchase Request → Controller → Stock Validation → 
Price Calculation → Insurance Calculation → 
Stock Update → Transaction Storage
```

### 4.3 Prescription Workflow
```
Doctor Selection → Client Selection → Medication Selection → 
Prescription Creation → Purchase Processing
```

## 5. Key Features

### 5.1 Data Management
- **CRUD Operations**: Complete Create, Read, Update, Delete for all entities
- **Data Validation**: Comprehensive input validation using regex patterns
- **Data Integrity**: Foreign key relationships and referential integrity
- **Search Functionality**: Multiple search criteria for all entities

### 5.2 Business Logic
- **Purchase Types**: Support for direct and prescription-based purchases
- **Insurance Integration**: Automatic reimbursement calculations
- **Stock Management**: Real-time inventory tracking and validation
- **Financial Calculations**: Precise monetary calculations with proper rounding

### 5.3 User Interface
- **Modern GUI**: Professional Swing-based interface
- **Data Tables**: Sortable and filterable data presentation
- **Modal Dialogs**: Context-specific data entry forms
- **Real-time Updates**: Immediate UI refresh after data changes

## 6. Technology Stack

### 6.1 Core Technologies
- **Java SE**: Core programming language
- **Swing**: GUI framework
- **JUnit 5**: Unit testing framework
- **Maven**: Build and dependency management

### 6.2 Development Tools
- **IntelliJ IDEA**: Primary IDE with UI Designer
- **Git**: Version control
- **PowerDesigner**: UML modeling

## 7. Testing Strategy

### 7.1 Unit Testing
- Comprehensive test coverage for all model classes
- Business logic validation testing
- Exception handling verification

### 7.2 Integration Testing
- Controller and model integration
- UI and controller integration
- End-to-end workflow testing

## 8. Security Considerations

### 8.1 Data Validation
- Input sanitization using regex patterns
- Type safety with proper exception handling
- Business rule enforcement

### 8.2 Data Integrity
- Referential integrity between related entities
- Transaction consistency for purchases
- Stock level validation

## 9. Performance Considerations

### 9.1 Memory Management
- Efficient collection usage
- Proper object lifecycle management
- Garbage collection optimization

### 9.2 Search Optimization
- Indexed collections for fast lookups
- Efficient search algorithms
- Lazy loading where appropriate

## 10. Future Enhancements

### 10.1 Planned Features
- Database persistence layer
- Multi-user support with authentication
- Advanced reporting and analytics
- Web-based interface
- Mobile application support

### 10.2 Scalability Considerations
- Database integration for large datasets
- Caching mechanisms for frequently accessed data
- Distributed architecture for multiple pharmacy locations