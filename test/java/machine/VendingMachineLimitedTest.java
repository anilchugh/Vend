package machine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class VendingMachineLimitedTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private VendingMachineLimited vendingMachine;

	@Before
	public void setUp() throws Exception {
		vendingMachine = new VendingMachineLimited(false);
	}

	@Test
	public void testChangeFor15Pounds() throws Exception {
		Collection<Coin> coins = vendingMachine.getChangeFor(1500);
		assertEquals("Should be 19 coins", 19, coins.size());
		assertEquals("Should be 11 £1 coins", 11, coins.stream().filter(coin -> coin == Coin.ONE_POUND).count());
		assertEquals("Should be 8 50 pence coins", 8, coins.stream().filter(coin -> coin == Coin.FIFTY_PENCE).count());
	}

	@Test
	public void testOptimalChangeFor1Pence() throws Exception {
		vendingMachine = new VendingMachineLimited(true);
		Collection<Coin> coins = vendingMachine.getChangeFor(1);
		assertEquals("Should be 1 coin", 1, coins.size());
		assertTrue("Should be 1 1p coin", coins.contains(Coin.ONE_PENCE));
	}

	@Test
	public void testChangeFor90Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getChangeFor(90);
		assertEquals("Should be 5 coins", 5, coins.size());
		assertEquals("Should be 1 50p coins", 1, coins.stream().filter(coin -> coin == Coin.FIFTY_PENCE).count());
		assertEquals("Should be 4 10p coins", 4, coins.stream().filter(coin -> coin == Coin.TEN_PENCE).count());
	}

	@Test
	public void testChangeFor40Pounds() throws Exception {
		Collection<Coin> coins = vendingMachine.getChangeFor(4000);
		assertEquals("Should be 276 coins", 276, coins.size());
		assertEquals("Should be 11 £1 coins", 11, coins.stream().filter(coin -> coin == Coin.ONE_POUND).count());
		assertEquals("Should be 24 50 pence coins", 24,
				coins.stream().filter(coin -> coin == Coin.FIFTY_PENCE).count());
		assertEquals("Should be 0 20 pence coins", 0, coins.stream().filter(coin -> coin == Coin.TWENTY_PENCE).count());
		assertEquals("Should be 99 10 pence coins", 99, coins.stream().filter(coin -> coin == Coin.TEN_PENCE).count());
		assertEquals("Should be 142 5 pence coins", 142,
				coins.stream().filter(coin -> coin == Coin.FIVE_PENCE).count());
	}

	@Test(expected = InsufficientCoinageException.class)
	public void testChangeFor50Pounds() throws Exception {
		vendingMachine.getChangeFor(5000);
	}

}
