package exo_poo;

public class Compte {
    // declaration
    // attibut ---------------------------------------------------
    private double solde; // money

    // static ----------------------------------------------------------
    private static int nbOjects; //create var count
    static {nbOjects = 0;} // init counter

    //constance ------------------------------------------------------

    //builder --------------------------------------------------------
    public Compte(){
        nbOjects++;
        afficherCompte(this.solde);
    } // count nb objet

    public Compte(double pSolde){
        this.solde = pSolde;
        nbOjects++;
        afficherCompte(this.solde);
    }


    //getter setter -----------------------------------------------

    //method static ------------------------------------------------
    public static void displayNbObjets() {
        System.out.println("NumberOfObjet: " + nbOjects);
    }

    public double getSolde() {
        return this.solde;
    }

    public static int getNbOjects() {
        return nbOjects;
    }

    private double Deposer(double deposit){
        System.out.println("|"+getSolde()+" + "+deposit+"|");
        this.solde  = solde + deposit;
        //afficher(this.solde);
        return this.solde;
    }
    public double getDeposer(double deposit){
        return Deposer(deposit);
    }

    private double Retirer(double withdraw){
        System.out.println("|"+getSolde()+" - "+withdraw+"|");
        this.solde = solde - withdraw;
        //afficher(this.solde);
        return getSolde();
    }
    public double getRetirer(double withdraw){
        return Retirer(withdraw);
    }

    public void afficherCompte(double getSolde){
        System.out.println(getSolde);
    }

    //method private
    //method public

    //metier

}
