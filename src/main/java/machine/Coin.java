package machine;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enumeration to represent coin denomination amount
 * 
 * @author anil
 *
 */
public enum Coin {
	ONE_POUND(100), FIFTY_PENCE(50), TWENTY_PENCE(20), TEN_PENCE(10), FIVE_PENCE(5), TWO_PENCE(2), ONE_PENCE(1);

	int denominationAmount;

	public int getDenominationAmount() {
		return denominationAmount;
	}

	Coin(int denominationAmount) {
		this.denominationAmount = denominationAmount;
	}

	public static Optional<Coin> getCoinMatchingDenomination(int denomination) {
		return Arrays.asList(values()).stream().filter(coin -> coin.denominationAmount == denomination).findFirst();
	}
}
