/**
 * Model package for the Sparadrap Pharmacy Management System.
 * 
 * <p>This package contains all the domain model classes representing the core
 * business entities of a pharmacy management system. The classes follow object-oriented
 * principles with proper encapsulation, inheritance, and polymorphism.</p>
 * 
 * <h2>Core Entities:</h2>
 * <ul>
 *   <li>{@link model.Person} - Abstract base class for all person entities</li>
 *   <li>{@link model.Client} - Pharmacy customers</li>
 *   <li>{@link model.Medecin} - Medical doctors</li>
 *   <li>{@link model.Mutuelle} - Insurance companies</li>
 *   <li>{@link model.Medicament} - Pharmaceutical products</li>
 *   <li>{@link model.Ordonnance} - Medical prescriptions</li>
 *   <li>{@link model.Achat} - Purchase transactions</li>
 * </ul>
 * 
 * <h2>Enumerations:</h2>
 * <ul>
 *   <li>{@link model.catMed} - Medication categories</li>
 *   <li>{@link model.Dep} - French departments</li>
 * </ul>
 * 
 * <h2>Class Hierarchy:</h2>
 * <pre>
 * Person (abstract)
 * ├── Client
 * ├── Medecin
 * └── Mutuelle
 * 
 * Medecin
 * └── Ordonnance (extends Medecin)
 * </pre>
 * 
 * <h2>Key Relationships:</h2>
 * <ul>
 *   <li>Client has optional Mutuelle (insurance)</li>
 *   <li>Client has optional treating Medecin</li>
 *   <li>Ordonnance links Medecin, Client, and Medicaments</li>
 *   <li>Achat represents purchases (with or without Ordonnance)</li>
 * </ul>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 */
package model;