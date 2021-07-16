package Handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Request {
	abstract void execute(ObjectInputStream ois, ObjectOutputStream oos)
							throws IOException, ClassNotFoundException;

}
