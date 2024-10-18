import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SolusiBanyak {
    public double[] list_koef;

    public SolusiBanyak() {
        list_koef = new double[53];
        int i;
        for (i = 0; i < 53; i++) {
            list_koef[i] = 0;
        }
        //[0,0,0,0,0,0,0,0...,0,0]
        //[angka yg berdiri sendiri,A,B,C,D,E,F,...,a,b,c,d,e,f...]
    }

    public static String MergeSolusiBanyak(SolusiBanyak sb) {
        StringBuilder result = new StringBuilder();
        int i;

        // Handle the independent term (sb.list_koef[0])
        if (sb.list_koef[0] != 0) {
            result.append(String.valueOf(Math.round(sb.list_koef[0] * 100) / 100.0));
        }

        // Loop through the rest of the coefficients (sb.list_koef[1] to sb.list_koef[58])
        for (i = 1; i < 53; i++) {
            if (sb.list_koef[i] == 0) {
                continue; // Skip if the coefficient is zero
            }

            // Append a '+' if the result is not empty and the coefficient is positive
            if (!result.isEmpty() && sb.list_koef[i] > 0) {
                result.append("+");
            }

            char variable;
            if (i <= 26) {
                variable = (char) (i + 64); //A-Z
            } else {
                variable = (char) (i + 70); //a-z
            }
            if (sb.list_koef[i] == 1) {
                result.append(variable);
            } else if (sb.list_koef[i] == -1) {
                result.append("-").append(variable);
            } else {
                result.append(String.valueOf(Math.round(sb.list_koef[i] * 100) / 100.0)).append(variable);
            }
        }
        if (result.isEmpty()) {
            result.append("0");
        }
        return result.toString();
    }
    public static SolusiBanyak sbKaliKonstanta(SolusiBanyak sb, double c) {
        SolusiBanyak result = new SolusiBanyak();
        int i;
        for (i = 0; i < 53; i++) {
            result.list_koef[i] = sb.list_koef[i]*c;
        }
        return result;
    }
    public static SolusiBanyak subtractSolusiBanyak(SolusiBanyak sb1, SolusiBanyak sb2) {
        SolusiBanyak result = new SolusiBanyak();
        int i;
        for (i = 0; i < 53; i++) {
            result.list_koef[i] = sb1.list_koef[i] - sb2.list_koef[i];
        }
        return result;
    }
    public static int nextIndexSB(int idx) {
        int result;
        //var pertama adalah r (114), var terakhir adalah Q ()
        //idx = var - 64
        if (idx == 52) { //z lompat ke a
            result = 27;
        }
        else if (idx == 43) { //q lompat ke R
            result = 18;
        }
        else if (idx == 26) { //Z lompat ke A
            result = 1;
        }
        else {
            result = idx + 1;
        }
        return result;
    }
}
