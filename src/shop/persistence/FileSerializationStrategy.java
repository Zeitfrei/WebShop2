package shop.persistence;
import java.io.IOException;

import java.io.Serializable;

import fpt.com.SerializableStrategy;

/**
 * an abstract strategy to serialize an object and save it into a file on disk
 * 
 * The abstract FileSerializationStrategy provides serialize() and unserialize()
 * to encapsulate all the required actions within a concrete strategy to load or
 * save an object to disk. It also adds the requirement to set a default filename
 * each strategy uses.
 */
public abstract class FileSerializationStrategy implements SerializableStrategy {
	String filename;

	/**
	 * serialize an Object into a file with given name
	 * 
	 * @param object    an <code>Object</code> to serialize 
	 * @param filename  a filename to store <code>object</code> in 
	 * @throws Exception
	 */
	public final void serialize(Serializable object, String filename) throws Exception {
		this.filename = filename;
		this.open(SerializableStrategy.Type.WRITE);
		this.writeObject(object);
		this.close(SerializableStrategy.Type.WRITE);
	}

	/**
	 * serialize an Object into a file with a default name, depending on the strategy
	 * 
	 * @param object    an <code>Object</code> to serialize 
	 * @throws Exception
	 */
	public final void serialize(Serializable object) throws Exception {
		this.serialize(object, this.defaultFilename());
	}

	/**
	 * deserialize an Object from a file
	 * 
	 * @param object    an <code>Object</code> to serialize 
	 * @param filename  a filename to store <code>object</code> in 
	 * @throws Exception
	 */
	public final Object deserialize(String filename) throws Exception {
		this.filename = filename;
		this.open(SerializableStrategy.Type.READ);
		Object return_value = this.readObject();
		this.close(SerializableStrategy.Type.READ);
		return return_value;
	}

	/**
	 * deserialize an Object from a file with a default name, depending on the strategy
	 * 
	 * @param object    an <code>Object</code> to serialize 
	 * @throws Exception
	 */
	public final Object deserialize() throws Exception {
		return this.deserialize(this.defaultFilename());
	}

	@Override
	public final void restart(Type type) throws IOException {
		close(type);
		open(type);
	}

	/**
	 * return a default filename the strategy uses if none provided
	 */
	protected abstract String defaultFilename();
}
