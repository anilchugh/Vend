package machine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class VendingMachineUnlimitedTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private VendingMachineUnlimited vendingMachine;

	@Before
	public void setUp() throws Exception {

		vendingMachine = new VendingMachineUnlimited();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOptimalChangeFor2Pounds() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(200);
		assertEquals("Should be 2 one pound coins", 2, coins.size());
		assertTrue("Should be 2 one pound coins", coins.stream().allMatch(coin -> coin == Coin.ONE_POUND));
	}

	@Test
	public void testOptimalChangeFor6Pounds98Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(688);
		assertEquals("Should be 12 coins returned in total", 12, coins.size());
		assertEquals("Should be 6 £1 coins", 6, coins.stream().filter(coin -> coin == Coin.ONE_POUND).count());
		assertEquals("Should be 1 50 pence coin", 1, coins.stream().filter(coin -> coin == Coin.FIFTY_PENCE).count());
		assertEquals("Should be 1 20 pence coin", 1, coins.stream().filter(coin -> coin == Coin.TWENTY_PENCE).count());
		assertEquals("Should be 1 10 pence coin", 1, coins.stream().filter(coin -> coin == Coin.TEN_PENCE).count());
		assertEquals("Should be 1 five pence coin", 1, coins.stream().filter(coin -> coin == Coin.FIVE_PENCE).count());
		assertEquals("Should be 1 two pence coin", 1, coins.stream().filter(coin -> coin == Coin.TWO_PENCE).count());
		assertEquals("Should be 1 one pence coins", 1, coins.stream().filter(coin -> coin == Coin.ONE_PENCE).count());
	}

	@Test
	public void testOptimalChangeFor31Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(31);
		assertEquals("Should be 3 coins returned in total", 3, coins.size());
		assertEquals("Should be 1 20 pence coin", 1, coins.stream().filter(coin -> coin == Coin.TWENTY_PENCE).count());
		assertEquals("Should be 1 10 pence coin", 1, coins.stream().filter(coin -> coin == Coin.TEN_PENCE).count());
		assertEquals("Should be 1 one pence coins", 1, coins.stream().filter(coin -> coin == Coin.ONE_PENCE).count());
	}

	@Test(expected = InvalidCoinException.class)
	public void testOptimalChangeFor0Pence() throws Exception {
		vendingMachine.getOptimalChangeFor(0);
	}

	@Test
	public void testOptimalChangeFor1Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(1);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be one 1 pence coin", coins.contains(Coin.ONE_PENCE));
	}

	@Test
	public void testOptimalChangeFor2Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(2);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be 1 2 pence coin", coins.contains(Coin.TWO_PENCE));
	}

	@Test
	public void testOptimalChangeFor5Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(5);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be 1 5 pence coin", coins.contains(Coin.FIVE_PENCE));
	}

	@Test
	public void testOptimalChangeFor10Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(10);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be 1 10 pence coin", coins.contains(Coin.TEN_PENCE));
	}

	@Test
	public void testOptimalChangeFor20Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(20);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be 1 20 pence coin", coins.contains(Coin.TWENTY_PENCE));
	}

	@Test
	public void testOptimalChangeFor50Pence() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(50);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be 1 50 pence coin", coins.contains(Coin.FIFTY_PENCE));
	}

	@Test
	public void testOptimalChangeForOnePound() throws Exception {
		Collection<Coin> coins = vendingMachine.getOptimalChangeFor(100);
		assertEquals("Should be 1 coin returned in total", 1, coins.size());
		assertTrue("Should be 1 £1 coin", coins.contains(Coin.ONE_POUND));
	}

}
