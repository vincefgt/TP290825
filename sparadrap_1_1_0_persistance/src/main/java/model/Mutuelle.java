package model;

import controller.PharmacieController;
import controller.Regex;

/**
 * Represents an insurance company (mutuelle) in the Sparadrap pharmacy system.
 * 
 * <p>This class extends {@link Person} and manages insurance company information
 * including reimbursement rates and department location. Insurance companies
 * provide reimbursement calculations for client purchases based on their
 * specific reimbursement rates.</p>
 * 
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Reimbursement rate management (0-100%)</li>
 *   <li>Department location tracking</li>
 *   <li>Automatic reimbursement calculations</li>
 *   <li>Client association for coverage</li>
 * </ul>
 * 
 * <h2>Reimbursement System:</h2>
 * <p>The insurance system calculates reimbursements based on:</p>
 * <ul>
 *   <li><strong>Base Amount</strong>: Total purchase amount</li>
 *   <li><strong>Reimbursement Rate</strong>: Insurance company's coverage percentage</li>
 *   <li><strong>Final Reimbursement</strong>: Base Amount × (Rate / 100)</li>
 * </ul>
 * 
 * <h2>Validation Rules:</h2>
 * <ul>
 *   <li>Reimbursement rate must be between 0 and 100</li>
 *   <li>Department must be a valid {@link Dep} enumeration value</li>
 *   <li>All inherited Person validations apply</li>
 * </ul>
 * 
 * <h2>Constructor Options:</h2>
 * <ul>
 *   <li><strong>Full Constructor</strong>: Complete insurance company information</li>
 *   <li><strong>Simple Constructor</strong>: Name and reimbursement rate only</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Create insurance company
 * Mutuelle mgen = new Mutuelle("MGEN", 70.0);
 * 
 * // Calculate reimbursement for a purchase
 * double purchaseAmount = 25.50;
 * double reimbursement = mgen.calcRemb(purchaseAmount);
 * // Result: 17.85 (70% of 25.50)
 * 
 * // Assign to client
 * client.setMutuelle(mgen);
 * }</pre>
 * 
 * @author Sparadrap Development Team
 * @version 1.0
 * @since 2025-01-15
 * @see Person
 * @see Client
 * @see Dep
 * @see Achat
 */
public class Mutuelle extends Person{
    private Dep dep;
    private int depCode;
    private String nomDep;
    private double tauxRemb =0; // Taux de remboursement en pourcentage (0-100)

    // Constructeur
    public Mutuelle(String firstName,String lastName, String address, String email,int nbState,String city,String phone, double tauxRemb) { //,Dep dep) {
        super("Mutuelle",lastName,address,email,nbState,city,phone);
        this.setTauxRemb(tauxRemb);
       // this.setDep(dep);
    }
    public Mutuelle(String lastName, double tauxRemb) {
        super(lastName);
        this.setFirstName("Mutuelle");
        this.setTauxRemb(tauxRemb);
        //PharmacieController.getListMutuelles().add(this);
    }

    public Dep getDep() {
        return dep;
    }
    public void setDep(Dep dep) {
        if (Regex.testNullObj(dep)){
            throw new IllegalArgumentException("dep required");}
        this.dep = dep;
    }

    public double getTauxRemb() {
        return tauxRemb;
    }
    public void setTauxRemb(double tauxRemb) {
        if (tauxRemb < 0 || tauxRemb > 100) {
            throw new IllegalArgumentException("Le taux de remboursement doit être entre 0 et 100");
        }
        this.tauxRemb = tauxRemb;
    }

    // Calculi refund mutuelle
    public double calcRemb(double montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("montant invalid <0");
        }
        return PharmacieController.formatTwoDec(montant * (tauxRemb / 100.0));
    }

    @Override
    public String toString() {
        return getLastName();//+" (Remb: "+getTauxRemb()+"%)";
    }
}
