public class OperasiMatrix {
    public static Matrix copyMatrix(Matrix m)
    {
        Matrix result = new Matrix(m.rows,m.cols);
        int i, j;
        for (i = 0; i < result.rows; i++) {
            for (j = 0; j < result.cols; j++) {
                result.contents[i][j] = m.contents[i][j];
            }
        }
        return result;
    }
    public static double returnDetBySarrus(Matrix m) //Untuk Determinan Sarrus
    {
        if (m.rows == 3 && m.cols == 3) {
            return m.contents[0][0]*m.contents[1][1]*m.contents[2][2]
                    + m.contents[0][1]*m.contents[1][2]*m.contents[2][0]
                    + m.contents[0][2]*m.contents[1][0]*m.contents[2][1]
                    - m.contents[0][0]*m.contents[1][2]*m.contents[2][1]
                    - m.contents[0][1]*m.contents[1][0]*m.contents[2][2]
                    - m.contents[0][2]*m.contents[1][1]*m.contents[2][0];
        }
        else {
            return 0;
        }

    }
    public static Matrix getMatrixKoefisien(Matrix m) //Untuk SPL Cramer
    {
        Matrix result = new Matrix(m.rows, m.cols - 1);
        int i, j;
        for (i = 0; i < result.rows; i++) {
            for (j = 0; j < result.cols; j++) {
                result.contents[i][j] = m.contents[i][j];
            }
        }
        return result;
    }
    public static Matrix getVektorKonstanta(Matrix m) //Untuk SPL Cramer
    {
        Matrix result = new Matrix(m.rows, 1);
        int i;
        for (i = 0; i < result.rows; i++) {
            result.contents[i][0] = m.contents[i][m.getLastCol()];
        }
        return result;
    }
    public static Matrix replaceColWithVector(Matrix m, Matrix vector, int col_index)
    {
        //asumsi m.rows = vector.rows, col_index valid untuk m
        Matrix result = copyMatrix(m);
        int i;
        for (i = 0; i < m.rows; i++) {
            result.contents[i][col_index] = vector.contents[i][0];
        }
        return result;
    }

}