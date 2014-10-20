package shop.persistence;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import shop.model.Product;
import shop.model.ProductList;

import com.thoughtworks.xstream.XStream;

public class XStreamStrategy extends FileSerializationStrategy {
	XStream xstream;
	FileReader file_reader;
	FileWriter file_writer;

	public XStreamStrategy() {
		xstream = new XStream();
		xstream.registerConverter(new ProductListConverter());

	}

	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		registerAlias();
		return (ProductList) xstream.fromXML(file_reader);
	}

	@Override
	public void writeObject(Serializable obj) throws IOException {
		registerAlias();
		xstream.toXML(obj, file_writer);
	}

	@Override
	public boolean isOpen() {
		// We don't even know which streams to check ...
		return false;
	}

	@Override
	public void close(Type type) throws IOException {
		if (type == Type.READ || type == Type.READ_WRITE) {
			file_reader.close();
		}
		if (type == Type.WRITE || type == Type.READ_WRITE) {
			file_writer.close();
		}
	}

	@Override
	public void open(Type type) throws IOException {
		if (type == Type.READ || type == Type.READ_WRITE) {
			file_reader = new FileReader(filename);
		}
		if (type == Type.WRITE || type == Type.READ_WRITE) {
			file_writer = new FileWriter(filename);
		}
	}

	@Override
	protected String defaultFilename() {
		return "products.xstream.xml";
	}
	public void registerAlias(){
		xstream.alias("waren", ProductList.class);
		xstream.useAttributeFor("id", Product.class);
	}
}
