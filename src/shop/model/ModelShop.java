package shop.model;
import java.util.Observable;

public class ModelShop extends Observable {
	String filename = "products.bin";
	ProductList products = null;

	public ProductList getProducts() {
		return products;
	}

	public void setProducts(ProductList products) {
		this.products = products;
	}

	public int countProducts() {
		return this.products.size();
	}

	public void addProduct(Product p) {
		this.products.add(p);
		this.setChanged();
		this.notifyObservers(p);
	}

	public void loadDemoProducts() {
		products = new ProductList();
		ProductIdGenerator.initialize();
		try {
			this.addProduct(new Product("Kopfmassagegerät", 4.99, 115));
			this.addProduct(new Product("Lavalampe", 34.79, 87));
			this.addProduct(new Product("Siegelwachskerze", 3.49, 43));
		} catch (Exception e) {
			System.err.println("Das Erzeugen eines Produktes schlug fehl.");
			e.printStackTrace();
		}
	}
}
