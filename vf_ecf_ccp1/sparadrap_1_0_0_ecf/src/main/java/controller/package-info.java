/**
 * Controller package for the Sparadrap Pharmacy Management System.
 * 
 * <p>This package contains the business logic and control flow classes that manage
 * the pharmacy operations including client management, medication inventory,
 * prescription handling, and purchase processing.</p>
 * 
 * <h2>Key Classes:</h2>
 * <ul>
 *   <li>{@link controller.PharmacieController} - Main business logic controller</li>
 *   <li>{@link controller.Regex} - Input validation utilities</li>
 *   <li>{@link controller.DateFilter} - Date filtering enumeration</li>
 * </ul>
 * 
 * <h2>Design Patterns:</h2>
 * <ul>
 *   <li><strong>Singleton Pattern</strong>: PharmacieController uses static lists for centralized data management</li>
 *   <li><strong>Factory Pattern</strong>: Object creation methods in PharmacieController</li>
 *   <li><strong>Strategy Pattern</strong>: Different validation strategies in Regex class</li>
 * </ul>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 */
package controller;