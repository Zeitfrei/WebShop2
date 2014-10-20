package shop.model;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import fpt.com.Product;

import shop.persistence.ProductListConverter;

public class ProductList extends ArrayList<Product> implements fpt.com.ProductList {
	// TODO: making this work
	//@XStreamConverter(ProductListConverter.class);

	private static final long serialVersionUID = -4369230182489466162L;

	public void add(int arg0, fpt.com.Product p) {
		super.add(p);
	}

	public boolean addAll(Collection<? extends fpt.com.Product> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(int arg0, Collection<? extends fpt.com.Product> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public fpt.com.Product set(int arg0, fpt.com.Product arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	public boolean add(Product p) {
		super.add(p);
		return false;
	}

	@Override
	public fpt.com.Product findProductById(long id) {
		Product product = null;
		Product p;

		Iterator<Product> iterator = this.iterator();
		while (iterator.hasNext()) {
			p = iterator.next();
			if (p.getId() == id) {
				product = p;
				break;
			}
		};

		return product;
	}

	@Override
	public fpt.com.Product findProductByName(String name) {
		Product product = null;
		Product p;

		Iterator<Product> iterator = this.iterator();
		while (iterator.hasNext()) {
			p = iterator.next();
			if (p.getName() == name) {
				product = p;
				break;
			}
		}

		return product;
	}

	public long maxId() {
		Product p;
		long max = 0;

		Iterator<Product> iterator = this.iterator();
		while (iterator.hasNext()) {
			p = iterator.next();
			if (p.getId() > max) {
				max = p.getId();
			}
		};

		return max;
	}
}
