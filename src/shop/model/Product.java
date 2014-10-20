package shop.model;
import shop.HardwareShop;

public class Product implements fpt.com.Product {
	private static final long serialVersionUID = -5139899367072017310L;

	private String name;
	private double price = 0;
	private int quantity = 0;
	private String id;

	public Product(String name, double price, int quantity) throws Exception {
		String id = ProductIdGenerator.uniqueId();
		this.setId(id);

		this.name     = name;
		this.quantity = quantity;
		this.price    = price;
	}

	/**
	 * create a Product without an id
	 */
	public Product() {
	}

	@Override
	public long getId() {
		return serialVersionUID;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public void setPrice(double preis) {
		this.price = preis;
	}

	@Override
	public void setQuantity(int anzahl) {
		this.quantity = anzahl;
	}

	@Override
	public void stepQuantityDown(int down) {
		this.quantity -= down;
	}

	@Override
	public void stepQuantityUp(int up) {
		this.quantity += up;
	}
}
