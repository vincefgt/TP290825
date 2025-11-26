package logger;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mylogj2 {

    private static final Logger log4j = LogManager.getLogger(Mylogj2.class);

    public static Logger getLogger() {
        return log4j;
    }

    public Mylogj2() {

    }
}
