package Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public abstract class AbstractHandler {

	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	public AbstractHandler(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
		this.objectOutputStream = objectOutputStream;
	}

	protected <T> T fromClient() {
		try {
			return (T) this.objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException ioException) {
			ioException.printStackTrace();
		}
		return null;
	}
	protected <T> void toClient(T someObject){
		try{
			this.objectOutputStream.writeObject(someObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	abstract void handle();

}
