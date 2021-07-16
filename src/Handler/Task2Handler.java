package Handler;

import Topics.*;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Task2Handler extends AbstractHandler {
	private int MAX_SIZE = 50;

	public Task2Handler(
			@NotNull ObjectInputStream objectInputStream,
			@NotNull ObjectOutputStream objectOutputStream) {
		super(objectOutputStream, objectInputStream);
	}

	public static void main(String[] args) {

	}

	public void handle() {
		int[][] primitiveMatrix = fromClient();
		var matrix = new Matrix(primitiveMatrix);
		var graph = new MatrixInGraph(matrix);

		Index sourceIndex = fromClient();
		Index destinationIndex = fromClient();

		Node<Index> sourceNode = graph.getGraphNode(sourceIndex);
		Node<Index> destinationNode = graph.getGraphNode(destinationIndex);

		if (!Matrix.isValidBySize(primitiveMatrix, MAX_SIZE) || sourceNode == null || destinationNode == null) {
			toClient(false);
			return;
		}
		toClient(true);
		System.out.println("Inside the handler");
		var result = FuncAlgorithm.allShortestsPaths(sourceNode, destinationNode);
		toClient(result);
	}


}
