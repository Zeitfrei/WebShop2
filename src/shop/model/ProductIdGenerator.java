package shop.model;
public class ProductIdGenerator {
	static long current_id;
	static long MAXIMUM_ID = 999999;
	static boolean initialized = false;

	public static void initialize() {
		System.err.println("Warning: ProductIdGenerator initialized with product id 0");
		current_id = 0;
		initialized = true;
	}

	public static void initializeWithProductList(ProductList products) {
		current_id = products.maxId();
		initialized = true;
	}

	/**
	 * @throws Exception in case there aren't any more IDs available
	 */
	public static String uniqueId() throws Exception {
		if (! initialized) {
			throw new Exception("ProductIdGenerator must be initialized before first use");
		}

		current_id += 1;
		if (current_id >= MAXIMUM_ID) {
			throw new Exception("pool of available product IDs depleted");
		}
		return String.format("%06d", current_id);
	}
}
