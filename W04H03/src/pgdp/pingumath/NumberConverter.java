package pgdp.pingumath;

import java.util.Locale;

public class NumberConverter {

    public static String intToPinguNum(int n) {
//        edge case negative int
        if (n < 0) {
            return "Not Defined";
        }

//        if n = 0 just return "In"
        if (n == 0) {
            return "In";
        }

//        convert decimal to ternary
        StringBuilder ternary = new StringBuilder();
        while (n > 0) {
            int rest = n % 3;
            ternary.insert(0, rest);
            n /= 3;
        }

//        convert ternary to pingu num
        StringBuilder pinguNum = new StringBuilder();
        while (!ternary.isEmpty()) {
            switch (ternary.charAt(0)) {
                case '0' -> pinguNum.append("in");
                case '1' -> pinguNum.append("gu");
                case '2' -> pinguNum.append("pin");
            }
            ternary.deleteCharAt(0);
        }

//        set first char to upper case
        pinguNum.setCharAt(0, Character.toUpperCase(pinguNum.charAt(0)));
        return pinguNum.toString();
    }

    public static int pinguNumToInt(String pinguNum) {
//        edge case empty string
        if (pinguNum == null || pinguNum.isEmpty()) {
            return -1;
        }

//        edge case first char not upper case or the rest not lower case
        if (!Character.isUpperCase(pinguNum.charAt(0)) || !pinguNum.substring(1).equals(pinguNum.substring(1).toLowerCase(Locale.ROOT))) {
            return -1;
        }

//        change first char to lower case
        pinguNum = pinguNum.substring(0, 1).toLowerCase(Locale.ROOT) + pinguNum.substring(1);
        int index = 0;
        int pointer = pinguNum.length() - 1;
        int sumInDecimal = 0;

        while (pointer >= 0) {
            if (pointer >= 2 && pinguNum.substring(pointer - 2, pointer + 1).equals("pin")) {
                sumInDecimal += (int) (2 * (Math.pow(3, index++)));
                pointer -= 3;
            } else if (pointer >= 1 && pinguNum.substring(pointer - 1, pointer + 1).equals("gu")) {
                sumInDecimal += (int) Math.pow(3, index++);
                pointer -= 2;
            } else if (pointer >= 1 && pinguNum.substring(pointer - 1, pointer + 1).equals("in")) {
                pointer -= 2;
                index++;
            } else {
                return -1;
            }
        }

        return sumInDecimal;
    }

    public static void main(String[] args) {
        System.out.println(pinguNumToInt("Pingu"));
    }
}
