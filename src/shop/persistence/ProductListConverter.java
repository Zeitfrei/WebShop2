package shop.persistence;
import shop.model.ModelShop;
import shop.model.Product;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import fpt.com.ProductList;

public class ProductListConverter implements Converter {

	@Override
	public boolean canConvert(Class klasse) {
		return true;
		//return klasse.equals(ProductList.class);
	}

	@Override
	public void marshal(Object wert, HierarchicalStreamWriter hsw,
			MarshallingContext mc) {
		ProductList pl = (ProductList) wert;

		for(fpt.com.Product pro : pl){
			hsw.startNode("ware");
			hsw.addAttribute("id", String.valueOf(pro.getId()));
			hsw.startNode("name");
			hsw.setValue(pro.getName());
			hsw.endNode();
			hsw.startNode("preis");
			hsw.setValue(String.valueOf(pro.getPrice()));
			hsw.endNode();
			if(pro.getQuantity()!=0){
				hsw.startNode("quantity");
				hsw.setValue(String.valueOf(pro.getQuantity()));
				hsw.endNode();
			}
			hsw.endNode();
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		ProductList products = new shop.model.ProductList();
		int counter = 0;

		while(reader.hasMoreChildren()) {
			reader.moveDown();
			Product product = new Product();
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				if (counter == 0) {
					product.setName(reader.getValue());
				} else if (counter == 1) {
					product.setPrice(Double.valueOf(reader.getValue()));
				} else if (counter == 2) {
					product.setQuantity(Integer.valueOf(reader.getValue()));
				}
				counter++;
				reader.moveUp();
			}
			products.add(product);
			counter = 0;
			reader.moveUp();
		}
		return products;
	}
}
