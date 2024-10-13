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

    // Fungsi untuk menghasilkan Invers Adjoint
    public static Matrix returnInversByAdjoint(Matrix matrix) {
        int n = matrix.rows;
        Matrix adjoint = resultAdjoint(matrix);
        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);

        Matrix invers = new Matrix(n, n);

        // Adjoin dibagi dengan determinan untuk mendapat nilai invers
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invers.contents[i][j] = adjoint.contents[i][j] / determinant;
            }
        }

        return invers;
    }
    public static Matrix resultAdjoint(Matrix matrix) {
        int n = matrix.rows;
        Matrix adjoint = new Matrix(n, n);
        double determinant = OperasiMatrix.cofactorExpansion(matrix, matrix.rows);
        if (determinant == 0) { // Jika determinan 0, matriks tidak memiliki invers
            throw new ArithmeticException("Matriks tidak memiliki invers karena determinan adalah 0.");
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Matrix minor = new Matrix(n - 1, n - 1);
                OperasiMatrix.getMinor(matrix, minor, i, j, n);
                adjoint.contents[j][i] = Math.pow(-1, i + j) * OperasiMatrix.cofactorExpansion(minor, n - 1);
            }
        }
        return adjoint;
    }

    //Fungsi mengubah bentuk matriks ke dalam bentuk matriks eselon baris
    public static Matrix REF(Matrix m) {
        Matrix result = copyMatrix(m);
        int rows = result.rows;
        int cols = result.cols;

        // Menentukan pivot sebagai elemen selain nol dari kolom ujung kiri
        for (int i = 0; i < rows; i++) {
            int maxRow = i;
            for (int k = i + 1; k < rows; k++) {
                if (Math.abs(result.contents[k][i]) > Math.abs(result.contents[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Menukar apabila ditemukan pivot dibawah current baris
            if (maxRow != i) {
                result = swapTwoRows(result, i, maxRow);
            }

            //jika pivot tidak ada selain nol
            if (result.contents[i][i] == 0) {
                continue;
            }

            //Membagi baris dengan pivot
            double pivot = result.contents[i][i];
            for (int j = i; j < cols; j++) {
                result.contents[i][j] /= pivot;
            }

            // mengubah elemen dibawah pivot menjadi nol
            for (int j = i + 1; j < rows; j++) {
                if (result.contents[j][i] != 0) {
                    double factor = result.contents[j][i];
                    for (int k = i; k < cols; k++) {
                        result.contents[j][k] -= factor * result.contents[i][k];
                    }
                }
            }
        }

        return result;
    }

    public static Matrix ReductionREF(Matrix m){
        Matrix result = copyMatrix(m);
        Matrix ref = REF(result); //Memanggil REF untuk melanjutkan Matriks eselon baris tereduksi
        int rows = result.rows;
        int cols = result.cols;

        for (int i = 0; i < rows; i++){
            // mengubah elemen diatas pivot menjadi nol
            for (int j = 0; j < i; j++) {
                if (ref.contents[j][i] != 0) {
                    double factor = ref.contents[j][i];
                    for (int k = i; k < cols; k++) {
                        ref.contents[j][k] -= factor * ref.contents[i][k];
                    }
                }
            }}
        return ref;

    }


    // Menukar dua baris
    public static Matrix swapTwoRows(Matrix m, int row1, int row2) {
        Matrix result = copyMatrix(m);

        double[] tempRow = new double[m.cols];
        for (int j = 0; j < result.cols; j++) {
            tempRow[j] = result.contents[row1][j];
        }
        for (int j = 0; j < result.cols; j++) {
            result.contents[row1][j] = result.contents[row2][j];
        }
        for (int j = 0; j < result.cols; j++) {
            result.contents[row2][j] = tempRow[j];
        }

        return result;
    }

}