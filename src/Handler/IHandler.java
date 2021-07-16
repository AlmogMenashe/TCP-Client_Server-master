package Handler;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class IHandler {
    public static void handle(InputStream inClient, OutputStream outClient) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outClient);
        ObjectInputStream objectInputStream = new ObjectInputStream(inClient);
        var taskType = (TaskType) objectInputStream.readObject();
        taskType.getHandler(objectInputStream, objectOutputStream).handle();
    }
}