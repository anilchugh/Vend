package machine;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Vending machine that handles unlimited change
 */
public class VendingMachineUnlimited {

	public VendingMachineUnlimited() {
		super();
	}

	/**
	 * Get optimal change for given amount
	 * 
	 * @param pence
	 *            amount in pence
	 * @return change
	 * @throws InvalidCoinException
	 * @throws InsufficientCoinageException
	 */
	public Collection<Coin> getOptimalChangeFor(int pence) throws InvalidCoinException, InsufficientCoinageException {
		Collection<Coin> change = new ArrayList<Coin>();
		if (pence <= 0) {
			throw new InvalidCoinException();
		}
		while (pence > 0 && isAvailableChangePresent()) {
			for (Coin coin : Coin.values()) {
				if (isAvailable(coin) && pence >= coin.denominationAmount) {
					change.add(coin);
					pence = pence - coin.denominationAmount;
					removeCoinFromAvailableChange(coin);
					break;
				}
			}
		}
		if (!isAvailableChangePresent()) {
			// insufficient change
			addCoinsToAvailableChange(change);
			throw new InsufficientCoinageException();
		}
		return change;
	}

	/**
	 * Checks coin is available in vending machine change
	 * 
	 * @param coin
	 * @return flag
	 */
	protected boolean isAvailable(Coin coin) {
		return true;

	}

	/**
	 * Add coins back to available change in vending machine
	 * 
	 * @param coins
	 *            to be added back
	 */
	protected void addCoinsToAvailableChange(Collection<Coin> coins) {
		// do nothing
	}

	/**
	 * Remove coin from available change inside vending machine
	 * 
	 * @param coin
	 *            to be removed
	 */
	protected void removeCoinFromAvailableChange(Coin coin) {
		// do nothing
	}

	/**
	 * Checks there is any available change in vending machine exist
	 * 
	 * @return
	 */
	protected boolean isAvailableChangePresent() {
		return true;
	}

}
