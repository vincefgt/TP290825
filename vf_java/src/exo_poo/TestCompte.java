package exo_poo;

public class TestCompte {
    private double soldeinit;
    private double deposit;
    private double withdraw;

    //builder
    //@param soldeinit
    //@param deposit
    //@param withdraw
    public TestCompte(double soldeinit, double deposit, double withdraw) {
        this.soldeinit = soldeinit;
        this.deposit = deposit;
        this.withdraw = withdraw;

    }

    public static void testCompte(double soldeinit, double deposit, double withdraw) {
        Compte c1 = new Compte(soldeinit);
//        c1.Deposer(deposit);
//        c1.Retirer(withdraw);
        c1.getDeposer(deposit);
        c1.getRetirer(withdraw);
        c1.afficherCompte(c1.getSolde());
        Compte.displayNbObjets();
    }
}
