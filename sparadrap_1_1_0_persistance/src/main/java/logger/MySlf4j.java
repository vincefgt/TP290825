package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class MySlf4j {

    public static final Logger logSlf4j = LogManager.getLogger(MySlf4j.class.getName());

    public static Logger getLogger() {
        return logSlf4j;
    }

    /**
     * Méthode 1: Utiliser ThreadContext (MDC - Mapped Diagnostic Context)
     * C'est la méthode recommandée car thread-safe
     */
    public void processWithUser(String username, String action) {
        try {
            // Ajouter l'utilisateur au contexte du thread
            ThreadContext.put("user", username);
            ThreadContext.put("sessionId", generateSessionId());

            MySlf4j.getLogger().info("Début du traitement: {}", action);
            // Votre logique métier ici
            performBusinessLogic(action);
            MySlf4j.getLogger().info("Fin du traitement avec succès");

        } catch (Exception e) {
            MySlf4j.getLogger().error("Erreur lors du traitement", e);
        } finally {
            // Important: nettoyer le contexte à la fin
            ThreadContext.clearAll();
        }

    }
    // Méthodes utilitaires
    private void performBusinessLogic(String action) throws Exception {
        MySlf4j.getLogger().debug("Exécution de la logique métier");
        // Logique ici
    }

    private String generateSessionId() {
        return java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}

