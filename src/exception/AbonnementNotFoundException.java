package exception;

// Exception levee quand un abonnement n'est pas trouve
public class AbonnementNotFoundException extends Exception {

    public AbonnementNotFoundException(String message) {
        super(message); // on transmet le message a la classe parente Exception
    }
}