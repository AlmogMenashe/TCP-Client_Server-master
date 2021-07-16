package Handler;

import Topics.AbstractGraph;
import Topics.FuncAlgorithm;
import Topics.Matrix;
import Topics.MatrixInGraph;
import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Task1Handler extends AbstractHandler {

	public Task1Handler(
			@NotNull ObjectInputStream objectInputStream,
			@NotNull ObjectOutputStream objectOutputStream) {
		super(objectOutputStream, objectInputStream);
	}

	void handle() {
		int[][] primitiveMatrix = this.fromClient();
		Matrix matrix = new Matrix(primitiveMatrix);
		AbstractGraph graph = new MatrixInGraph(matrix);

		var connectedComponents = FuncAlgorithm.Task1Handler(graph);

		if (connectedComponents != null) {
			toClient(connectedComponents);
		} else {
			toClient("Some unexpected error had occured in connectedComponentsHandler");
		}
	}
}
