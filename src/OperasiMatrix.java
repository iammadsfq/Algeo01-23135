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
    // Fungsi untuk melakukan ekspansi kofaktor
    public static double cofactorExpansion(Matrix matrix, int n) {
        double det = 0.0;

        // Jika matrix bersifat 1x1, determinan adalah elemen itu sendiri
        if (n == 1) {
            return matrix.contents[0][0];
        }

        // Jika matrix bersifat 2x2
        if (n == 2) {
            return matrix.contents[0][0] * matrix.contents[1][1] - matrix.contents[0][1] * matrix.contents[1][0];
        }

        // Membuat matriks minor
        Matrix minor = new Matrix(n - 1, n - 1);

        for (int col = 0; col < n; col++) {
            getMinor(matrix, minor, 0, col, n);

            // Ekspansikan kofaktor dengan rumus (-1)^(i+j)
            det += Math.pow(-1, col) * matrix.contents[0][col] * cofactorExpansion(minor, n - 1);
        }
        return det;
    }
    // Fungsi untuk mendapatkan matriks minor
    public static void getMinor(Matrix matrix, Matrix minor, int rowToSkip, int colToSkip, int n) {
        int rowMinor = 0, colMinor = 0;

        for (int i = 0; i < n; i++) {
            if (i == rowToSkip) continue;

            colMinor = 0;
            for (int j = 0; j < n; j++) {
                if (j == colToSkip) continue;

                minor.contents[rowMinor][colMinor] = matrix.contents[i][j];
                colMinor++;
            }
            rowMinor++;
        }
    }
}