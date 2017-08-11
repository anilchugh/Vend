package machine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Vending machine that handles limited change
 */
public class VendingMachineLimited extends VendingMachineUnlimited {

	// available change in vending machine
	private Map<Coin, Integer> availableChange = new HashMap<Coin, Integer>();

	// application configuration file
	private Properties applicationProperties;

	// coin inventory
	private Properties coinInventory;

	private final static String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";
	private final static String COIN_INVENTORY_PROPERTY_FILE_LOCATION_ENTRY = "coin.inventory.property.file.location";

	// flag to indicate whether to update coin inventory file or not
	boolean coinInventoryFileUpdate = false;

	public VendingMachineLimited() throws FileNotFoundException, IOException {
		this(true); // defaults to updating the coin inventory file
	}

	public VendingMachineLimited(boolean coinInventoryFileUpdate) throws FileNotFoundException, IOException {
		// load application config
		applicationProperties = new Properties();
		applicationProperties.load(getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES_FILE_NAME));

		// load coin inventory file location
		String coinInventoryFileLocation = applicationProperties
				.getProperty(COIN_INVENTORY_PROPERTY_FILE_LOCATION_ENTRY);

		loadCoinInventoryFile(coinInventoryFileLocation);

		this.coinInventoryFileUpdate = coinInventoryFileUpdate;
	}

	/**
	 * Load coin inventory file from location into a HashMap instance
	 * 
	 * @param coinInventoryFileLocation
	 * @throws FileNotFoundException
	 *             if file not found
	 * @throws IOException
	 */
	private void loadCoinInventoryFile(String coinInventoryFileLocation) throws FileNotFoundException, IOException {
		// load coin inventory into system
		coinInventory = new Properties();
		FileInputStream fis = new FileInputStream(coinInventoryFileLocation);
		coinInventory.load(fis);
		fis.close();

		this.availableChange = coinInventory.entrySet().stream()
				.collect(Collectors.toMap(
						e -> Coin.getCoinMatchingDenomination(Integer.parseInt((String) e.getKey())).get(),
						e -> Integer.parseInt((String) e.getValue())));
	}

	@Override
	protected void addCoinsToAvailableChange(Collection<Coin> coins) {
		for (Coin coin : coins) {
			int coinQuantity = availableChange.get(coin);
			availableChange.replace(coin, coinQuantity, coinQuantity + 1);
			coinInventory.setProperty(String.valueOf(coin.getDenominationAmount()), String.valueOf(coinQuantity + 1));
		}
	}

	@Override
	protected void removeCoinFromAvailableChange(Coin coin) {
		int coinQuantity = availableChange.get(coin);
		availableChange.replace(coin, coinQuantity - 1);
		coinInventory.setProperty(String.valueOf(coin.getDenominationAmount()), String.valueOf(coinQuantity - 1));
	}

	/**
	 * Get change for amount in pence based on availability of change in vending
	 * machine
	 * 
	 * @param pence
	 * @return change as List of Coin instances
	 * @throws InvalidCoinException
	 * @throws InsufficientCoinageException
	 * @throws FileNotFoundException
	 */
	public Collection<Coin> getChangeFor(int pence)
			throws InvalidCoinException, InsufficientCoinageException, FileNotFoundException {
		Collection<Coin> change = getOptimalChangeFor(pence);
		if (this.coinInventoryFileUpdate) {
			updateCoinInventory();
		}
		return change;
	}

	/**
	 * Update coin inventory
	 * 
	 * @throws FileNotFoundException
	 */
	private void updateCoinInventory() throws FileNotFoundException {
		try (FileOutputStream fos = new FileOutputStream(
				applicationProperties.getProperty(COIN_INVENTORY_PROPERTY_FILE_LOCATION_ENTRY))) {
			coinInventory.store(fos, "Updating coin inventory");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileNotFoundException("Coin inventory file not found");
		}
	}

	@Override
	protected boolean isAvailable(Coin coin) {
		return availableChange.get(coin) > 0;

	}

	@Override
	protected boolean isAvailableChangePresent() {
		return !(availableChange.values().stream().filter(value -> value == 0).count() == availableChange.keySet()
				.size());
	}

	public static void main(String args[]) throws NumberFormatException, FileNotFoundException, InvalidCoinException,
			InsufficientCoinageException, IOException {
		Collection<Coin> coins = new VendingMachineLimited().getChangeFor(Integer.parseInt(args[0]));
		System.out.println(coins.toString());
	}

}
