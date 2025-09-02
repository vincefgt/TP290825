package Exception;

// Exception pour les erreurs liées aux stocks de médicaments
class StockException extends Exception {
    public StockException(String message) {
        super("Erreur Stock: " + message);
    }

    public StockException(String message, Throwable cause) {
        super("Erreur Stock: " + message, cause);
    }
}
