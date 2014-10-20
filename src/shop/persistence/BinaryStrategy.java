package shop.persistence;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BinaryStrategy extends FileSerializationStrategy {
	FileInputStream file_input_stream;
	ObjectInputStream object_input_stream;
	FileOutputStream file_output_stream;
	ObjectOutputStream object_output_stream;

	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		return object_input_stream.readObject();
	}

	@Override
	public void writeObject(Serializable obj) throws IOException {
		object_output_stream.writeObject(obj);
	}

	@Override
	public boolean isOpen() {
		// We don't even know which streams to check ...
		return false;
	}

	@Override
	public void close(Type type) throws IOException {
		if (type == Type.READ || type == Type.READ_WRITE) {
			object_input_stream.close();
			try {file_input_stream.close();} catch(Exception e) {}
		}
		if (type == Type.WRITE || type == Type.READ_WRITE) {
			object_output_stream.close();
			try {file_output_stream.close();} catch(Exception e) {}
		}
	}

	@Override
	public void open(Type type) throws IOException {
		if (type == Type.READ || type == Type.READ_WRITE) {
			file_input_stream = new FileInputStream(filename);
			object_input_stream = new ObjectInputStream(file_input_stream);
		}
		if (type == Type.WRITE || type == Type.READ_WRITE) {
			file_output_stream = new FileOutputStream(filename);
			object_output_stream = new ObjectOutputStream(file_output_stream);
		}
	}

	@Override
	protected String defaultFilename() {
		return "products.ser";
	}
}
