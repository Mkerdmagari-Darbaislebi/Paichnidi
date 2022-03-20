package math;

public class Matrix4 {
    private int[][] matrix;

    public Matrix4(int[][] m) {
        this.matrix = m;
    }

    public int[][] getMatrix4() {
        return matrix;
    }

    public void setMatrix4(int[][] m) {
        this.matrix = m;
    }

    public int[][] identity4() {
        int[][] im = new int[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (i == j)
                    im[i][j] = 1;
                else
                    im[i][j] = 0;
        return im;
    }

    public void setValue(int value, int x, int y){
        this.matrix[x][y] = value;
    }

    public void setRow(int rowIndex, int[] row){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                if(i == rowIndex)
                    this.matrix[i][j] = row[j];
    }

    public void setColumn(int columnIndex, int[] column){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                if(j == columnIndex)
                    this.matrix[i][j] = column[i];
    }
}
