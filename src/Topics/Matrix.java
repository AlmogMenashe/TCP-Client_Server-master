package Topics;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Matrix<privet> implements Serializable {
    /**
     * Neighboring Indices are up,down, left,right
     *   1 0 0
     *   0 0 1
     *   0 0 0
     *   1 1 1
     *
     * [[(0,0),
     * [(1,1) ,(1,2)],
     * [(3,0),(3,1),(3,2)]]
     *
     *
     * 1 0 0
     * 0 1 1
     * 0 1 0
     * 0 1 1
     *
     *
     */
    private int[][] primitiveMatrix;

    public Matrix(int[][] oArray){
        primitiveMatrix = Arrays
                .stream(oArray)
                .map(row -> row.clone())
                .toArray(value -> new int[value][]);
    }

    public Matrix(int nRows, int nColumn) {
        Random generator = new Random();
        primitiveMatrix = new int[nRows][nColumn];
        for (int i = 0; i < primitiveMatrix.length; i++) {
            for (int j = 0; j < primitiveMatrix[0].length; j++) {
                primitiveMatrix[i][j] = generator.nextInt(2);
            }
        }
    }

    public static void main(String[] args) {
        int[][] source = {
                {0, 1, 0},
                {1, 0, 1},
                {1, 0, 1}
        };
        Matrix matrix = new Matrix(source);
        matrix.printMatrix();

        System.out.println(matrix.hasIndex(new Index(2, 1)));
        System.out.println(matrix.hasIndex(new Index(3, 3)));
    }

    public void printMatrix() {
        for (int[] row : primitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
    }

    public static boolean isValidBySize(int[][] matrix, int maxSize) {
        final var hasInvalidRowNumber = matrix.length > maxSize;
        final var hasInvalidRows = Arrays.stream(matrix).filter(row -> row.length > maxSize).toArray().length != 0;
        return !(hasInvalidRowNumber || hasInvalidRows);
    }

    public final int[][] getPrimitiveMatrix() {
        return primitiveMatrix;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : primitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    public Collection<Index> getNeighbors(final Index index, boolean withDiagonals) {
        var list = getNeighbors(index);
        if (withDiagonals) {
            var row = index.row;
            var column = index.column;
            var bottomRightDiagonal = new Index(row + 1, column + 1);
            var bottomLeftDiagonal = new Index(row + 1, column - 1);
            var topLeftDiagonal = new Index(row - 1, column - 1);
            var topRightDiagonal = new Index(row - 1, column + 1);

            if (hasIndex(bottomRightDiagonal)) list.add(bottomRightDiagonal);
            if (hasIndex(bottomLeftDiagonal)) list.add(bottomLeftDiagonal);
            if (hasIndex(topLeftDiagonal)) list.add(topLeftDiagonal);
            if (hasIndex(topRightDiagonal)) list.add(topRightDiagonal);

        }
        return list;
    }

    public Collection<Index> getNeighbors(final Index index) {
        Collection<Index> list = new ArrayList<>();

        var top = new Index(index.row - 1, index.column);
        var bottom = new Index(index.row + 1, index.column);
        var left = new Index(index.row, index.column - 1);
        var right = new Index(index.row, index.column + 1);

        if (hasIndex(top)) list.add(top);
        if (hasIndex(bottom)) list.add(bottom);
        if (hasIndex(left)) list.add(left);
        if (hasIndex(right)) list.add(right);

        return list;
    }

    private boolean hasIndex(final Index index) {
        int extracted = -1;
        try {
            extracted = primitiveMatrix[index.row][index.column];
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } finally {
            return extracted != -1 ? true : false;
        }
    }

    public int getValue(@NotNull final Index index){
        return primitiveMatrix[index.row][index.column];
    }


    public Collection<Index> getReachables(Index index, boolean withDiagonals) {
        ArrayList<Index> filteredIndices = new ArrayList<>();
        this.getNeighbors(index, withDiagonals)
                .stream()
                .filter(i -> getValue(i) == 1)
                .forEach(neighbor -> filteredIndices.add(neighbor));
        return filteredIndices;
    }

    public Collection<Index> getAllIndices() {
        ArrayList<Index> filteredIndices = new ArrayList<>();
        for (var row = 0; row < this.primitiveMatrix.length; ++row)
            for (var column = 0; column < this.primitiveMatrix[row].length; ++column) {
                if (primitiveMatrix[row][column] == 1) {
                    filteredIndices.add(new Index(row, column));
                }
            }
        return filteredIndices;
    }



}