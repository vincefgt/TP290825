package Exception;

// Exception pour les erreurs liées aux achats
class AchatException extends Exception {
    public AchatException(String message) {
        super("Erreur Achat: " + message);
    }

    public AchatException(String message, Throwable cause) {
        super("Erreur Achat: " + message, cause);
    }
}
