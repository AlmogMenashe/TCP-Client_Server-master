package Handler;

import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Task4Handler extends AbstractHandler {

    public Task4Handler(
            @NotNull ObjectInputStream objectInputStream,
            @NotNull ObjectOutputStream objectOutputStream) {
        super(objectOutputStream, objectInputStream);
    }
}
