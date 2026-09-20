package exception;

// Exception levee quand un paiement n'est pas trouve
public class PaiementNotFoundException extends Exception {

    public PaiementNotFoundException(String message) {
        super(message);
    }
}