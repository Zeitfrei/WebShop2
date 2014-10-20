package shop.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.WindowConstants;

import shop.model.ModelShop;
import shop.model.Product;
import shop.model.ProductIdGenerator;
import shop.model.ProductList;
import shop.persistence.BinaryStrategy;
import shop.persistence.FileSerializationStrategy;
import shop.persistence.XMLStrategy;
import shop.persistence.XStreamStrategy;
import shop.view.ViewShop;

public class ControllerShop implements ActionListener {
	private ModelShop shop_model;
	private ViewShop shop_view;
	private FileSerializationStrategy file_serialization_strategy;

	/**
	 * initialize the controller and related classes
	 *
	 * 1. initializes the model
	 * 2. initializes a default serialization strategy
	 * 3. tries to unserialize existing data
	 * 4. initializes the view
	 * 5. attaches action listeners to controls (TODO: refactoring)
	 * 6. attaches the view as an observer to the model
	 * 7. makes the view visible
	 */
	public ControllerShop() {
		setModel(new ModelShop());
		chooseSerializationStrategyAndLoadProducts();
		ProductIdGenerator.initializeWithProductList(getModel().getProducts());

		shop_view = new ViewShop(getModel());
		shop_view.getAddProductButton().addActionListener(this);
		getModel().addObserver(shop_view);
		this.startView();
	}

	public void startView() {
		shop_view.viewShop();
		getModel().addObserver(shop_view);
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.equals(WindowConstants.EXIT_ON_CLOSE)) {
			// do nothing
		}

		try {
			String name = shop_view.newProductsName();
			if (name.length() < 3)  {throw new Exception("Der Name des Produktes ist zu kurz.");};

			Double price = shop_view.newProductsPrice();
			if (price < 0)          {throw new Exception("Der Preis des Produktes muss positiv sein.");};

			Integer quantity = shop_view.newProductsQuantity();
			if (quantity < 0)       {throw new Exception("Die Anzahl der Produkte muss positiv sein.");};
			if (quantity > 1000000) {throw new Exception("Die Anzahl der Produkte muss ins Lager passen.");};

			getModel().addProduct(new Product(name, price, quantity));

			shop_view.resetForm();
		} catch (NumberFormatException e) {
			System.err.println("Ungueltige Eingabe");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Ein Produkt konnte nicht erzeugt werden.");
			System.err.println(e.getMessage());
		}
	}

	void update(Observable subject, Object additional) {
		// do nothing
	}

	/**
	 * chooses an initial serialization strategy by trying one after another
	 *
	 * As a side effect the serialized products are loaded OR an initial
	 * collection of demo products is created as a fall-back. Oh dear.
	 *
	 * TODO: This method implements about every anti-pattern you can think of.
	 *       Refactoring it would certainly be a good idea.
	 */
	public void chooseSerializationStrategyAndLoadProducts() {
		try {
			file_serialization_strategy = new XStreamStrategy();
			loadProducts(false);
		} catch (Exception e1) {
			try {
				file_serialization_strategy = new XMLStrategy();
				loadProducts(false);
			} catch (Exception e2) {
				file_serialization_strategy = new BinaryStrategy();
				try {
					loadProducts(true);
				} catch (Exception e) {
					// This should never happen. Yeah, I know.
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * restore existing product data or create some demo products
	 *
	 * @throws Exception  if no products were deserialized or created at all
	 */
	public void loadProducts(Boolean fallback_to_demo_products) throws Exception {
		try {
			getModel().setProducts((ProductList) file_serialization_strategy.deserialize());
			// accessing a member of products to trigger an exception in case of invalid data
			int count = getModel().countProducts();
			System.out.println(count + " Produkte wurden mittels " + file_serialization_strategy.getClass().getSimpleName() + " geladen.");
		} catch(Exception e) {
			System.err.println("Produkte konnten mittels " + file_serialization_strategy.getClass().getSimpleName() + " nicht geladen werden.");
			if (fallback_to_demo_products) {
				System.out.println("Erzeuge statt dessen einige Demo-Produkte.");
				getModel().loadDemoProducts();
				int count = getModel().countProducts();
				System.out.println(count + " Produkte wurden erzeugt.");
			} else {
				throw new Exception("no products loaded");
			}
		}
	}

	/**
	 * save product data
	 */
	public void saveProducts() {
		try {
			file_serialization_strategy.serialize(getModel().getProducts());
			int count = getModel().countProducts();
			System.out.println(count + " Produkte wurden mittels " + file_serialization_strategy.getClass().getSimpleName() + " gespeichert.");
		} catch (Exception e) {
			System.err.println("Die Produkte konnten nicht gespeichert werden:");
			e.printStackTrace();
		}
	}

	public ModelShop getModel() {
		return shop_model;
	}

	private void setModel(ModelShop model) {
		this.shop_model = model;
	}

	public FileSerializationStrategy getFileSerializationStrategy() {
		return file_serialization_strategy;
	}

	public void setFileSerializationStrategy(FileSerializationStrategy file_serialization_strategy) {
		this.file_serialization_strategy = file_serialization_strategy;
	}
}