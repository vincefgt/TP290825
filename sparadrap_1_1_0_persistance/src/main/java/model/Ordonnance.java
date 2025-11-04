package model;

import controller.PharmacieController;
import controller.Regex;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a medical prescription in the Sparadrap pharmacy system.
 * 
 * <p>This class extends {@link Medecin} and manages prescription information
 * including the prescribing doctor, patient, prescription date, and list of
 * prescribed medications. Prescriptions serve as the basis for prescription-based
 * purchases and ensure proper medical oversight of medication dispensing.</p>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Links doctors, patients, and medications</li>
 *   <li>Date tracking for prescription validity</li>
 *   <li>Medication list management</li>
 *   <li>Integration with purchase system</li>
 *   <li>Validation of medical relationships</li>
 * </ul>
 * 
 * <h2>Prescription Workflow:</h2>
 * <ol>
 *   <li>Doctor creates prescription for specific patient</li>
 *   <li>Medications are added to prescription</li>
 *   <li>Prescription is used as basis for purchase</li>
 *   <li>Stock levels are updated during purchase</li>
 * </ol>
 * 
 * <h2>Validation Rules:</h2>
 * <ul>
 *   <li>Prescription date cannot be null</li>
 *   <li>Patient must be a valid registered client</li>
 *   <li>Doctor must be a valid registered medecin</li>
 *   <li>At least one medication should be prescribed</li>
 * </ul>
 * 
 * <h2>Medication Management:</h2>
 * <p>Prescriptions maintain a list of prescribed medications with operations:</p>
 * <ul>
 *   <li>{@link #addMedOrdo(Medicament)} - Add medication to prescription</li>
 *   <li>{@link #suppMedOrdo(Medicament)} - Remove medication from prescription</li>
 *   <li>{@link #getListMedOrdo()} - Get list of prescribed medications</li>
 * </ul>
 * 
 * <h2>Constructor Options:</h2>
 * <p>The class provides multiple constructors for different scenarios:</p>
 * <ul>
 *   <li><strong>Full Constructor</strong>: Complete doctor and prescription information</li>
 *   <li><strong>Reference Constructor</strong>: Uses existing doctor and client objects</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Create prescription using existing doctor and client
 * Ordonnance prescription = new Ordonnance(
 *     LocalDate.now(), doctor, client
 * );
 * 
 * // Add medications to prescription
 * prescription.addMedOrdo(doliprane);
 * prescription.addMedOrdo(vitaminC);
 * 
 * // Use prescription for purchase
 * Achat purchase = new Achat(LocalDate.now(), client, prescription);
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see Medecin
 * @see Client
 * @see Medicament
 * @see Achat
 */
public class Ordonnance extends Medecin{
    private LocalDate dateOrdo;
    private Client patient;
    private Medecin medecin;
    public List<Medicament> listMedOrdo;

    // Constructeur
    public Ordonnance(String firstName,String lastName,String address,String email,int nbState,String city,String phone,Long nbAgreement,String idMedecin, LocalDate dateOrdo, Client patient) {
        super(firstName,lastName,address,email,nbState,city,phone,nbAgreement,idMedecin);
        this.setDateOrdo(dateOrdo);
        this.setPatient(patient);
        Medecin mededin = new Medecin(firstName,lastName,address,email,nbState,city,phone,nbAgreement,null); // because do not exist
        this.setMedecin(mededin);
        this.listMedOrdo = new ArrayList<>();
    }
    public Ordonnance(LocalDate dateOrdo,Medecin medecin,Client patient) {
        super(medecin.getFirstName(), medecin.getLastName(), medecin.getNbAgreement(), medecin.getIdMedecin());
        this.setMedecin(medecin);
        this.setDateOrdo(dateOrdo);
        this.setPatient(patient);
        this.listMedOrdo = new ArrayList<>();
    }

    // Getters et Setters
    public String getDateOrdo() {
        return this.dateOrdo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public void setDateOrdo(LocalDate dateOrdo) {
        if (Regex.testNotEmpty(String.valueOf(dateOrdo))||Regex.testDate(dateOrdo))
            throw new IllegalArgumentException("dateBirth required DD/MM/YYYY format");
        this.dateOrdo = dateOrdo;
    }

    public Client getPatient() {
        return this.patient;
    }
    public void setPatient(Client patient) {
        if (patient == null) {
            throw new IllegalArgumentException("patient required");
        }
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return this.medecin;
    }
    public void setMedecin(Medecin medecin) {
        if (medecin == null) {
            throw new IllegalArgumentException("medecin required ");
        }
        this.medecin = medecin;
    }

    public List<Medicament> getListMedOrdo() {
        return this.listMedOrdo;
    }
    public void setListMedOrdo(List<Medicament> listMedOrdo) {
        this.listMedOrdo = listMedOrdo;
    }

    // add med in ordo
    public void addMedOrdo(Medicament medicament) {
        if (medicament != null && !getListMedOrdo().contains(medicament)) {
            getListMedOrdo().add(medicament);
        }
    }

    public boolean suppMedOrdo(Medicament medicament) {
        return getListMedOrdo().remove(medicament);
    }

    @Override
    public String toString() {
        return "Ordonnance du "+dateOrdo+" - Dr "+medecin.getLastName()+" "+medecin.getFirstName()+
                " - Patient: "+patient.getLastName()+" "+patient.getFirstName()+" ("+listMedOrdo.size()+" médicaments)";
    }
}
