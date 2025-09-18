# Sparadrap Pharmacy Management System - Use Cases

## 1. System Overview
The Sparadrap system is a comprehensive pharmacy management application that handles clients, doctors, medications, prescriptions, and purchases.

## 2. Actors
- **Pharmacist**: Primary user who manages the pharmacy operations
- **Client**: Customer who purchases medications
- **Doctor**: Medical professional who prescribes medications

## 3. Use Cases

### 3.1 Client Management
**UC-001: Add New Client**
- **Actor**: Pharmacist
- **Description**: Register a new client in the system
- **Preconditions**: Pharmacist is logged into the system
- **Main Flow**:
  1. Pharmacist selects "Add Client"
  2. System displays client registration form
  3. Pharmacist enters client details (name, address, social security number, birth date, insurance, treating doctor)
  4. System validates the information
  5. System saves the client and displays confirmation
- **Alternative Flows**:
  - 4a. Invalid data: System displays error message and returns to step 3
- **Postconditions**: New client is registered in the system

**UC-002: Search Client**
- **Actor**: Pharmacist
- **Description**: Find a client by social security number
- **Main Flow**:
  1. Pharmacist enters social security number
  2. System searches for matching client
  3. System displays client information
- **Alternative Flows**:
  - 2a. Client not found: System displays "Client not found" message

**UC-003: Update Client Information**
- **Actor**: Pharmacist
- **Description**: Modify existing client details
- **Main Flow**:
  1. Pharmacist searches for client
  2. System displays client details
  3. Pharmacist modifies information
  4. System validates and saves changes

### 3.2 Doctor Management
**UC-004: Add New Doctor**
- **Actor**: Pharmacist
- **Description**: Register a new doctor in the system
- **Main Flow**:
  1. Pharmacist selects "Add Doctor"
  2. System displays doctor registration form
  3. Pharmacist enters doctor details (name, address, agreement number)
  4. System validates and saves doctor information

**UC-005: Search Doctor**
- **Actor**: Pharmacist
- **Description**: Find a doctor by agreement number
- **Main Flow**:
  1. Pharmacist enters agreement number
  2. System searches for matching doctor
  3. System displays doctor information

### 3.3 Medication Management
**UC-006: Add New Medication**
- **Actor**: Pharmacist
- **Description**: Add a new medication to inventory
- **Main Flow**:
  1. Pharmacist selects "Add Medication"
  2. System displays medication form
  3. Pharmacist enters medication details (name, category, price, stock)
  4. System validates and saves medication

**UC-007: Update Medication Stock**
- **Actor**: Pharmacist
- **Description**: Modify medication stock levels
- **Main Flow**:
  1. Pharmacist searches for medication
  2. System displays current stock
  3. Pharmacist updates stock quantity
  4. System saves new stock level

### 3.4 Prescription Management
**UC-008: Create Prescription**
- **Actor**: Doctor (via Pharmacist)
- **Description**: Create a new prescription for a client
- **Main Flow**:
  1. Pharmacist selects "New Prescription"
  2. System displays prescription form
  3. Pharmacist selects doctor and client
  4. Pharmacist adds medications to prescription
  5. System saves prescription

**UC-009: Add Medication to Prescription**
- **Actor**: Pharmacist
- **Description**: Add medications to an existing prescription
- **Main Flow**:
  1. Pharmacist selects prescription
  2. System displays available medications
  3. Pharmacist selects medication to add
  4. System adds medication to prescription

### 3.5 Purchase Management
**UC-010: Process Direct Purchase**
- **Actor**: Pharmacist
- **Description**: Process a purchase without prescription
- **Main Flow**:
  1. Pharmacist selects "Direct Purchase"
  2. System displays purchase form
  3. Pharmacist selects client and medications
  4. System calculates total and insurance reimbursement
  5. System processes payment and updates stock

**UC-011: Process Prescription Purchase**
- **Actor**: Pharmacist
- **Description**: Process a purchase with prescription
- **Main Flow**:
  1. Pharmacist selects "Prescription Purchase"
  2. System displays prescription selection
  3. Pharmacist selects prescription
  4. System calculates total and insurance reimbursement
  5. System processes payment and updates stock

**UC-012: Calculate Insurance Reimbursement**
- **Actor**: System
- **Description**: Automatically calculate insurance reimbursement
- **Main Flow**:
  1. System retrieves client's insurance information
  2. System calculates reimbursement based on insurance rate
  3. System displays reimbursement amount

### 3.6 Insurance Management
**UC-013: Add New Insurance**
- **Actor**: Pharmacist
- **Description**: Register a new insurance company
- **Main Flow**:
  1. Pharmacist selects "Add Insurance"
  2. System displays insurance form
  3. Pharmacist enters insurance details (name, reimbursement rate)
  4. System saves insurance information

### 3.7 Reporting and Analytics
**UC-014: View Purchase History**
- **Actor**: Pharmacist
- **Description**: View client's purchase history
- **Main Flow**:
  1. Pharmacist searches for client
  2. System displays client's purchase history
  3. Pharmacist can filter by date range

**UC-015: Generate Sales Report**
- **Actor**: Pharmacist
- **Description**: Generate sales reports for analysis
- **Main Flow**:
  1. Pharmacist selects "Sales Report"
  2. System displays report options
  3. Pharmacist selects date range and filters
  4. System generates and displays report

## 4. System Requirements
- **Functional Requirements**:
  - Client registration and management
  - Doctor registration and management
  - Medication inventory management
  - Prescription creation and management
  - Purchase processing (direct and prescription-based)
  - Insurance reimbursement calculation
  - Stock management and tracking
  - Sales reporting and analytics

- **Non-Functional Requirements**:
  - Data validation and integrity
  - User-friendly interface
  - Real-time stock updates
  - Secure data handling
  - Performance optimization for large datasets