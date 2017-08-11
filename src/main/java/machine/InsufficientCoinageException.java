package machine;

public class InsufficientCoinageException extends Exception {

	private static final long serialVersionUID = 1L;

	public InsufficientCoinageException() {
		super("Insufficient coinage");
	}

}
