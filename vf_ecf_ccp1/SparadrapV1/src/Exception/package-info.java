/**
 * Exception package for the Sparadrap Pharmacy Management System.
 * 
 * <p>This package contains custom exception classes that provide specific
 * error handling for different aspects of the pharmacy management system.
 * Each exception class is designed to handle specific error scenarios
 * with meaningful error messages.</p>
 * 
 * <h2>Exception Classes:</h2>
 * <ul>
 *   <li>{@link Exception.InputException} - Runtime exception for invalid user input</li>
 *   <li>{@link Exception.AchatException} - Purchase-related errors</li>
 *   <li>{@link Exception.ClientException} - Client management errors</li>
 *   <li>{@link Exception.OrdonnanceException} - Prescription-related errors</li>
 *   <li>{@link Exception.StockException} - Medication stock errors</li>
 * </ul>
 * 
 * <h2>Exception Hierarchy:</h2>
 * <pre>
 * RuntimeException
 * └── InputException
 * 
 * Exception
 * ├── AchatException
 * ├── ClientException
 * ├── OrdonnanceException
 * └── StockException
 * </pre>
 * 
 * <h2>Usage Guidelines:</h2>
 * <ul>
 *   <li><strong>InputException</strong>: Use for immediate input validation failures</li>
 *   <li><strong>AchatException</strong>: Use for purchase processing errors</li>
 *   <li><strong>ClientException</strong>: Use for client-related business rule violations</li>
 *   <li><strong>OrdonnanceException</strong>: Use for prescription validation errors</li>
 *   <li><strong>StockException</strong>: Use for inventory management errors</li>
 * </ul>
 * 
 * <h2>Error Handling Strategy:</h2>
 * <p>The system uses a layered exception handling approach:</p>
 * <ol>
 *   <li><strong>Input Layer</strong>: InputException for immediate validation</li>
 *   <li><strong>Business Layer</strong>: Specific exceptions for business rule violations</li>
 *   <li><strong>Presentation Layer</strong>: User-friendly error messages</li>
 * </ol>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 */
package Exception;