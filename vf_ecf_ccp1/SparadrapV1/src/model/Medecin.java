package model;

import controller.PharmacieController;
import controller.Regex;

import static java.lang.String.format;

/**
 * Represents a medical doctor in the Sparadrap pharmacy system.
 * 
 * <p>This class extends {@link Person} and adds doctor-specific attributes
 * such as medical agreement number, patient list, and unique identifier.
 * Doctors can prescribe medications through {@link Ordonnance} objects and
 * serve as treating doctors for {@link Client} objects.</p>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Medical agreement number validation (11 digits)</li>
 *   <li>Patient management (add/remove patients)</li>
 *   <li>Automatic ID generation</li>
 *   <li>Prescription creation capabilities</li>
 * </ul>
 * 
 * <h2>Validation Rules:</h2>
 * <ul>
 *   <li>Agreement number must be exactly 11 digits</li>
 *   <li>All inherited Person validations apply</li>
 *   <li>Patient list maintains referential integrity</li>
 * </ul>
 * 
 * <h2>Patient Management:</h2>
 * <p>Doctors maintain a list of their patients with the following operations:</p>
 * <ul>
 *   <li>{@link #addPatient(Client)} - Add a new patient</li>
 *   <li>{@link #deletePatient(Client)} - Remove a patient</li>
 *   <li>{@link #getPatients()} - Get immutable copy of patient list</li>
 * </ul>
 * 
 * <h2>ID Generation:</h2>
 * <p>Doctor IDs are automatically generated using the format "MED####" where
 * #### is a sequential number based on the total number of doctors in the system.</p>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Create a new doctor
 * Medecin doctor = new Medecin(
 *     "Pierre", "Dupont", "10 Medical St",
 *     "dr.dupont@hospital.fr", 75008, "Paris",
 *     "0140506070", 12345678910L, "MED001"
 * );
 * 
 * // Add a patient
 * doctor.addPatient(client);
 * 
 * // Get patient list (immutable copy)
 * List<Client> patients = doctor.getPatients();
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see Person
 * @see Client
 * @see Ordonnance
 */
public class Medecin extends Person {

    private Long nbAgreement;
    private java.util.List<Client> patients; // List of patients
    private String idMedecin;

    // Constructor
    public Medecin(String firstName, String lastName, String address,String email, int nbState,String city,
                   String phone, Long nbAgreement,String idMedecin) {
        super(firstName,lastName,address,email,nbState,city,phone);
        this.setNbAgreement(nbAgreement);
        this.idMedecin = generateId(idMedecin);
        this.patients = new java.util.ArrayList<>();
    }
    public Medecin(String lastName, String firstName, String city, long nbAgreement,String idMedecin) {
        super(firstName,lastName,city);
        this.setNbAgreement(nbAgreement);
        this.setIdMedecin(idMedecin);
        this.patients = new java.util.ArrayList<>();
    }
    public Medecin(String lastName,String firstName, long nbAgreement, String idMedecin) {
        super(firstName,lastName);
        this.setNbAgreement(nbAgreement);
        this.setIdMedecin(idMedecin);
        this.patients = new java.util.ArrayList<>();
    }

    public String getIdMedecin() {
        return this.idMedecin;
    }
    public void setIdMedecin(String idMedecin) {
        this.idMedecin = generateId(idMedecin);
    }

    public long getNbAgreement() {
        return this.nbAgreement;
    }
    public void setNbAgreement(long nbAgreement) {
        Regex.setParamRegex("^\\d{11}$");
        if (Regex.testDigitLong(nbAgreement)||Regex.testNotEmpty(String.valueOf(nbAgreement)))
            throw new IllegalArgumentException("nbAgreement format invalid 11numbers required");
        this.nbAgreement = nbAgreement;
    }

    public java.util.List<Client> getPatients() {
        return new java.util.ArrayList<>(patients); // Retour d'une copie pour la sécurité
    }

    // add patient
    public void addPatient(Client patient) {
        if (patient != null && !patients.contains(patient)) {
            patients.add(patient);
        }
    }

    // delete un patient
    public boolean deletePatient(Client patient) {
        return patients.remove(patient);
    }

    // delete medecin
    public boolean deleteMedecin(Medecin pMedecin) {
        return PharmacieController.getListMedecins().remove(pMedecin);
    }
    // add patient
    public void addMedecin(Medecin pMedecin) {
            PharmacieController.getListMedecins().add(pMedecin);
    }

    // Méthodes utilitaires
    private String generateId(String idMedecin) {
        if (Regex.testNotEmpty(idMedecin)) {
            return "MED" + format("%04d", PharmacieController.getListMedecins().size() + 1); //Date.now().toString() + '_' + Math.random().toString(36).substr(2, 9);
        } else { return idMedecin; }
    }


    @Override
    public String toString() {
        if (super.getLastName().equals(PharmacieController.getListMedecins().getFirst().getLastName())) {
            return super.getLastName();
        } else  {
            return "Dr "+super.getLastName() + " " + super.getFirstName(); //+" (Agrement: "+nbAgreement+")";
        }
    }
}
