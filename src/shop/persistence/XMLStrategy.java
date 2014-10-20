package shop.persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class XMLStrategy extends FileSerializationStrategy {
	XMLEncoder enc = null;
	XMLDecoder dec = null;

	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		return dec.readObject();
	}

	@Override
	public void writeObject(Serializable obj) throws IOException {
		enc.writeObject(obj);
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close(Type type) throws IOException {
		if (type == Type.WRITE || type == Type.READ_WRITE) {
			enc.close();
		}
		if (type == Type.READ || type == Type.READ_WRITE) {
			dec.close();
		}
	}

	@Override
	public void open(Type type) throws IOException {
		if (type == Type.WRITE || type == Type.READ_WRITE) {
			enc = new XMLEncoder(new FileOutputStream(filename));
		}
		if (type == Type.READ || type == Type.READ_WRITE) {
			dec = new XMLDecoder(new FileInputStream(filename));
		}
	}

	@Override
	protected String defaultFilename() {
		return "products.xml";
	}
}