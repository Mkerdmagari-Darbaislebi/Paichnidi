package math;

public class Matrix4f {

    private float[][] matrix;

    public Matrix4f() {

    }

    public Matrix4f(float[][] m) {
        this.matrix = m;
    }

    public float[][] getMatrix4() {
        return matrix;
    }

    public void setMatrix4(float[][] m) {
        this.matrix = m;
    }

    public static Matrix4f identity4() {
        Matrix4f im = new Matrix4f();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (i == j)
                    im.matrix[i][j] = 1;
                else
                    im.matrix[i][j] = 0;
        return im;
    }

    public void setValue(float value, int x, int y){
        this.matrix[x][y] = value;
    }

    public void setRow(int rowIndex, float[] row){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                if(i == rowIndex)
                    this.matrix[i][j] = row[j];
    }

    public void setColumn(int columnIndex, float[] column){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                if(j == columnIndex)
                    this.matrix[i][j] = column[i];
    }
}
