package Topics;

import java.util.stream.Collectors;

public class MatrixInGraph extends AbstractGraph<Index> {

    public MatrixInGraph(Matrix matrix) {
        super();
        var allAvailableIndices = matrix.getAllIndices();
        //Building a GraphNode for each available index
        allAvailableIndices.forEach(index -> nodes.put((Index) index, new Node(index)));
        //Connecting all the indices
        allAvailableIndices.forEach(index -> {
            // Building a collection of reachable nodes
            var reachableNodes = matrix.getReachables((Index) index, true)
                    .stream()
                    .map(reachableIndex -> nodes.get(reachableIndex))
                    .collect(Collectors.toList());
            //Connecting node to his reachable neighbours
            nodes.get(index).connectTo((Node<Index>) reachableNodes);
        });
    }

    public static void main(String[] args) throws InterruptedException {
        //Connected Component:
//		int[][] primitiveMatrix1 = {
//				{1, 0, 0, 1, 0, 1},
//				{1, 0, 1, 1, 0, 0},
//				{1, 0, 1, 1, 0, 1},
//				{0, 0, 0, 0},
//				{1, 1, 1, 1},
//		};
        int[][] primitiveMatrix1 = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        var matrix1 = new Matrix(primitiveMatrix1);
        var graph1 = new MatrixInGraph(matrix1);
        var result = FuncAlgorithm.Task1Handler(graph1);
        System.out.println("Connected Component found :");
        System.out.println(result);
        //---------------------------

        //Shortest paths:
        int[][] primitiveMatrix2 = {
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1}
        };
//		int[][] primitiveMatrix2 = {};
        final Matrix matrix2 = new Matrix(primitiveMatrix2);
        final MatrixInGraph graph2 = new MatrixInGraph(matrix2);
        final Index source = new Index(0, 0);
        final Index destination = new Index(0, 5);


        var res = FuncAlgorithm.allShortestsPaths(graph2.getGraphNode(source), graph2.getGraphNode(destination));
        System.out.println("Client side print ?");
        System.out.println(res);

        //----------------------------
        int[][] firstMatrixTest = {//Should fail
                {1, 1, 0, 1, 1},
                {1, 0, 0, 1, 1},
                {1, 0, 0, 1, 1},
        };
        final var Graph1 = new MatrixInGraph(new Matrix(firstMatrixTest));
        int[][] secondMatrixTest = {//Should fail
                {1, 0, 0, 1, 1},
                {1, 0, 0, 1, 1},
                {0, 1, 0, 1, 1},
        };
        final var Graph2 = new MatrixInGraph(new Matrix(secondMatrixTest));
        int[][] thirdMatrixTest = {//Should return 2 and success
                {1, 0, 0, 1, 1, 1},
                {1, 0, 0, 1, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 1, 1, 1},
        };
        final var Graph3 = new MatrixInGraph(new Matrix(thirdMatrixTest));
        var res1 = FuncAlgorithm.Task3Handler(Graph1);
        var res2 = FuncAlgorithm.Task3Handler(Graph2);
        var res3 = FuncAlgorithm.Task3Handler(Graph3);

        System.out.println("Submarine games :");
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);

    }
}
