package machine;

public class InvalidCoinException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCoinException() {
		super("Invalid change inserted");
	}

}
