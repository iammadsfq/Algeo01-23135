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

    //Fungsi mencari determinan dengan reduksi baris
    public static double returnDetByRowReduction(Matrix m, int rows){
        Matrix result = copyMatrix(m);
        int cols = result.cols;
        double det = 1;

        for (int i = 0; i < rows; i++) {

            // Jika salah satu elemen diagonal bernilai 0
            if (result.contents[i][i] == 0) {
                return 0;
            }

            // Eliminasi nilai dibawah elemen diagonal
            for (int j = i + 1; j < rows; j++) {
                double factor = result.contents[j][i] / result.contents[i][i];  // Normalize by the pivot
                for (int k = i; k < cols; k++) {
                    result.contents[j][k] -= factor * result.contents[i][k];
                }
            }
        }

        //Perkalian elemen diagonal
        for (int i = 0; i < rows; i++) {
            det *= result.contents[i][i];
        }

        return det;

    }

    public static Matrix REF(Matrix m) {
        Matrix result = copyMatrix(m);
        int rows = result.rows;
        int cols = result.cols;
        double eps = 1e-6;

        for (int i = 0; i < Math.min(rows, cols); i++) {
            if (Math.abs(result.contents[i][i]) < eps) {
                // Mencari baris di bawah yang memiliki nilai non-nol di kolom yang sama
                for (int k = i + 1; k < rows; k++) {
                    if (Math.abs(result.contents[k][i]) > eps) {
                        result = swapTwoRows(result, i, k);
                        break; // Keluar dari loop setelah pertukaran
                    }
                }
            }

            // Jika setelah pencarian, elemen pivot masih nol, lanjutkan ke iterasi berikutnya
            if (Math.abs(result.contents[i][i]) < eps) {
                continue;
            }

            double pivot = result.contents[i][i];
            if (Math.abs(pivot) > eps) {
                for (int j = i; j < cols; j++) {
                    result.contents[i][j] /= pivot;
                }
            }

            for (int j = i + 1; j < rows; j++) {
                if (Math.abs(result.contents[j][i]) > eps) {
                    double factor = result.contents[j][i];
                    for (int k = i; k < cols; k++) {
                        result.contents[j][k] -= factor * result.contents[i][k];
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++){
            for (int j =0; j < cols; j++){
                if (Math.abs(result.contents[i][j]) < eps){
                    result.contents[i][j] = 0;
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

        double[] tempRow = result.contents[row1];
        result.contents[row1] = result.contents[row2];
        result.contents[row2] = tempRow;

        return result;
    }

    public static Matrix returnInversByGaussJordan(Matrix m, int N) {
        Matrix augmented = new Matrix(N, 2 * N);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                augmented.contents[i][j] = m.contents[i][j];
            }
        }

        for (int i = 0; i < N; i++) {
            augmented.contents[i][i + N] = 1;
        }
        augmented = ReductionREF(augmented);

        Matrix inverse = new Matrix(N, N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                inverse.contents[i][j] = augmented.contents[i][j + N];
            }
        }

        return inverse;
    }

    //fungsi menentukan nilai variable SPL dengan metode Gauss atau GaussJordan
    public static String[] SolveSPLGaussJordan(Matrix m) {
        m.TulisMatrix();
        int i, j;
        for (i = 0; i < m.rows; i++) {
            boolean allZero = true;
            for (j = 0; j < m.cols - 1; j++) {
                if (m.contents[i][j] != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero && m.contents[i][m.cols - 1] != 0) {
                return new String[]{"Tidak ada solusi"};
            }
        }

        SolusiBanyak[] solution = new SolusiBanyak[m.cols - 1];
        int k = 44;

        //assign parameters to free variables
        for (j = m.cols - 2; j >= 0; j--) {
            boolean hasLead = false;
            for (i = 0; i < m.rows; i++) {
                if (m.contents[i][j] != 0) {
                    hasLead = true;
                    for (int p = j-1; p >= 0; p--) {
                        if (m.contents[i][p] != 0) {
                            hasLead = false;
                            break;
                        }
                    }
                    if (hasLead) {
                        break;
                    }
                }
            }
            if (!hasLead) {
                solution[j] = new SolusiBanyak();
                solution[j].list_koef[k] = 1;
                k = SolusiBanyak.nextIndexSB(k);
            }
        }
        for (i = m.rows - 1; i >= 0; i--) {
            int leadIndex = -1;
            for (j = 0; j < m.cols - 1; j++) {
                if (m.contents[i][j] != 0) {
                    leadIndex = j;
                    break;
                }
            }
            if (leadIndex != -1) {
                if (solution[leadIndex] == null) {
                    solution[leadIndex] = new SolusiBanyak();
                    solution[leadIndex].list_koef[0] = m.contents[i][m.cols - 1];
                }
                for (j = leadIndex + 1; j < m.cols - 1; j++) {
                    if (solution[j] != null) {
                        solution[leadIndex] = SolusiBanyak.subtractSolusiBanyak(solution[leadIndex],
                                SolusiBanyak.sbKaliKonstanta(solution[j], m.contents[i][j]));
                    }
                }
                solution[leadIndex] = SolusiBanyak.sbKaliKonstanta(solution[leadIndex], 1 / m.contents[i][leadIndex]);
            }
        }

        String[] solutions = new String[m.cols - 1];
        for (i = 0; i < m.cols - 1; i++) {
            solutions[i] = SolusiBanyak.MergeSolusiBanyak(solution[i]);
        }

        return solutions;
    }

    public static Matrix transpose(Matrix matrix) {
        int rows = matrix.rows;
        int cols = matrix.cols;
        Matrix transposed = new Matrix(matrix.cols,matrix.rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed.contents[j][i] = matrix.contents[i][j];
            }
        }
        return transposed;
    }

    public static Matrix toAugmented(Matrix XTX, Matrix vektor) {
        Matrix augmented = new Matrix(XTX.rows, XTX.cols + 1);
        int i, j;
        for (i = 0; i < XTX.rows; i++) {
            for (j = 0; j < XTX.cols; j++) {
                augmented.contents[i][j] = XTX.contents[i][j];
            }
            augmented.contents[i][XTX.cols] = vektor.contents[i][0];
        }
        return augmented;
    }



}