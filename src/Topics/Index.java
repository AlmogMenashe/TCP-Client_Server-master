package Topics;

import java.io.Serializable;
import java.util.Objects;

public class Index implements Serializable {
    int row, column, dist;

    // Constructor
    public Index(int oRow, int oColumn){
        this.row = oRow;
        this.column = oColumn;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    //for task 2 daniel added this getters and setter of dist
    public void setdist(int dist){
        this.dist=dist;
    }
    public int getdist(){
        return this.dist;
    }


    @Override
    public String toString(){
        return "(" + row + "," + column + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return row == index.row &&
                column == index.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public static void main(String[] args) {
        Index myIndex = new Index(2,2);
        System.out.println(myIndex);
    }

}