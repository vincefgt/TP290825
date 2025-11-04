package Exception;

// Exception pour les erreurs liées aux ordonnances
class OrdonnanceException extends Exception {
    public OrdonnanceException(String message) {
        super("Erreur Ordonnance: " + message);
    }

    public OrdonnanceException(String message, Throwable cause) {
        super("Erreur Ordonnance: " + message, cause);
    }
}
