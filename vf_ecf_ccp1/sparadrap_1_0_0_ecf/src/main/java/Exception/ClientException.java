package Exception;

// Exception pour les erreurs liées aux clients
class ClientException extends Exception {
    public ClientException(String message) {
        super("Erreur Client: " + message);
    }

    public ClientException(String message, Throwable cause) {
        super("Erreur Client: " + message, cause);
    }
}
