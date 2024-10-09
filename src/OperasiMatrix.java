public class OperasiMatrix {
    public static double returnDetBySarrus(Matrix m) {
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
}