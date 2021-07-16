package Handler;

import Topics.FuncAlgorithm;
import Topics.Matrix;
import Topics.MatrixInGraph;
import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Task3Handler extends AbstractHandler {

	public Task3Handler(
			@NotNull ObjectInputStream objectInputStream,
			@NotNull ObjectOutputStream objectOutputStream) {
		super(objectOutputStream, objectInputStream);
	}

	@Override
	void handle() {
		int[][] primitiveMatrix = fromClient();
		MatrixInGraph graph = new MatrixInGraph(new Matrix(primitiveMatrix));
		try {
			var res = FuncAlgorithm.Task3Handler(graph);
			if (res != Integer.MIN_VALUE) {
				toClient(true);
				toClient(res);
			} else {
				toClient(false);
			}

		} catch (InterruptedException e) {
			toClient(false);
		}
	}
}
