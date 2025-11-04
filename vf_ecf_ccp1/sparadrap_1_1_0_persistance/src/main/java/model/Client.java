package model;

import controller.Regex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a pharmacy client (customer) in the Sparadrap system.
 * 
 * <p>This class extends {@link Person} and adds client-specific attributes
 * such as social security number, birth date, insurance information, and
 * treating doctor. Clients are the primary customers of the pharmacy and
 * can make purchases either directly or through prescriptions.</p>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Social Security Number validation (15 digits)</li>
 *   <li>Insurance company association for reimbursements</li>
 *   <li>Treating doctor assignment</li>
 *   <li>Birth date tracking for age-related validations</li>
 *   <li>Automatic registration in global client map</li>
 * </ul>
 * 
 * <h2>Validation Rules:</h2>
 * <ul>
 *   <li>Social Security Number must be exactly 15 digits</li>
 *   <li>Birth date must be a valid past date</li>
 *   <li>All inherited Person validations apply</li>
 * </ul>
 * 
 * <h2>Relationships:</h2>
 * <ul>
 *   <li><strong>Insurance</strong>: Optional association with {@link Mutuelle}</li>
 *   <li><strong>Doctor</strong>: Optional association with treating {@link Medecin}</li>
 *   <li><strong>Purchases</strong>: One-to-many relationship with {@link Achat}</li>
 *   <li><strong>Prescriptions</strong>: One-to-many relationship with {@link Ordonnance}</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Create a new client
 * Client client = new Client(
 *     "John", "Doe", "123 Main St",
 *     75001, "Paris", "0123456789",
 *     "john.doe@email.com", 123456789012345L,
 *     LocalDate.of(1980, 1, 1), insurance, doctor
 * );
 * 
 * // The client is automatically added to the global map
 * Client found = Client.MapClient.get("Doe John");
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see Person
 * @see Mutuelle
 * @see Medecin
 * @see Achat
 */
public class Client extends Person {
    private long nbSS;
    private LocalDate dateBirth;
    private Mutuelle mut;
    private Medecin medecinTraitant;
    //private List<Client> listClient;

    public static Map<String, Client> MapClient = new HashMap<>();

    // Constructeur avec validation (code défensif)
    public Client(String firstName, String lastName, String address, int nbState,
                  String city, String phone, String email, long nbSS,
                  LocalDate dateBirth,Mutuelle mut, Medecin medecinTraitant) {
        super (firstName, lastName,address,email,nbState,city,phone);
        this.setNbSS(nbSS);
        this.setDateBirth(dateBirth);
        this.setMutuelle(mut);
        this.setMedecinTraitant(medecinTraitant);
        MapClient.put(lastName+" "+firstName, this);
    }

    public long getNbSS() {
        return nbSS;
    }
    public void setNbSS(long nbSS) {
        Regex.setParamRegex("^\\d{15}$");
        if (Regex.testDigitLong(nbSS))
            throw new IllegalArgumentException(String.valueOf(nbSS+" format invalid 15 numbers required"));
        this.nbSS = nbSS;
    }

    public String getDateBirth() {
        return dateBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
    public void setDateBirth(LocalDate dateBirth) {
        if (Regex.testDate(dateBirth)||Regex.testNotEmpty(String.valueOf(dateBirth)))
            throw new IllegalArgumentException("dateBirth required");
        this.dateBirth = dateBirth;
    }

    public Mutuelle getMutuelle() {
        return mut;
    }
    public void setMutuelle(Mutuelle mut) {
        this.mut = mut;
    }

    public Medecin getMedecinTraitant() {
        return medecinTraitant;
    }
    public void setMedecinTraitant(Medecin medecinTraitant) {
        this.medecinTraitant = medecinTraitant;
    }


    @Override
    public String toString() {
        if (super.getLastName().equals("Tous Clients")){//PharmacieController.getListClients().getFirst().getLastName())){
            return super.getLastName();
        } else {
            return getLastName()+" "+getFirstName() +" (N° SS: "+getNbSS()+")";
        }
    }
}
