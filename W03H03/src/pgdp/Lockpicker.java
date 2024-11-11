package pgdp;

import java.util.concurrent.locks.Lock;

public class Lockpicker {

    private Mysterybox mbox;

    public Lockpicker(Mysterybox box) {
        this.mbox = box;
    }

    // ---------------------- Teil 1 Methoden ----------------------

    public String decryptInteger(int ix) {
        int baseValue = ix * ix * mbox.getIntHint();
        return HelperBook.intToAlphabetCharacter(Math.abs(baseValue % 26));
    }

    public String decryptFloat(float fx) {
        // TODO: Implementiere diese Methode
        double baseValue = (mbox.getIntHint() + fx) * mbox.getFloatHint();
        double cosineValue = Math.abs(Math.cos(baseValue));
        long roundedValue = Math.round(cosineValue * 1000);
        return String.valueOf(roundedValue);
    }

    public String decryptNumericalString(String sx) {
        StringBuilder finalValue = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int numStr = Integer.parseInt(sx.substring(i * 2, (i + 1) * 2));
            finalValue.append(HelperBook.intToAlphabetCharacter(numStr % 26));
        }
        return finalValue.toString();

    }

    // ---------------------- Teil 2 Methoden ----------------------

    public Float decryptModular(int a, int b) {
        // TODO: Implementiere diese Methode
        float firstBase = a * mbox.getFloatHint();
        float secondBase = b / mbox.getFloatHint();
        return firstBase % secondBase;
    }

    public String decryptMinimal(float x, float y) {
        // TODO: Implementiere diese Methode
        double biggerBase = Math.sqrt(Math.max(x, y));
        double lesserBase = Math.min(x, y) * mbox.getFloatHint();
        String biggerValue = String.format("%.4f", biggerBase);
        String smallerValue = String.format("%.4f", lesserBase);
        return biggerValue.substring(biggerValue.length() - 4) + smallerValue.substring(smallerValue.length() - 4);
    }

    public int decryptBytepolar(int i, byte b) {
        // TODO: Implementiere diese Methode
        int dividend = i * b;
        int divisor = ((i % b) << 1) + 1;
        return dividend / divisor;
    }

    // ---------------------- Teil 3 Methoden ----------------------

    public boolean solve() {
        // TODO: Implementiere diese Methode
        int bytepolarBase = decryptBytepolar(mbox.getPolarVal1(), mbox.getPolarVal2());
        float modularBase = decryptModular(mbox.getModularVal1(), mbox.getModularVal2());
        String minimalBase = decryptMinimal(mbox.getMinimalVal1(), mbox.getMinimalVal2());
        String finalValue = String.format("%s_%s_%s", decryptInteger(bytepolarBase), decryptFloat(modularBase), decryptNumericalString(minimalBase));
        System.out.println(finalValue);
        return mbox.checkSolution(finalValue);
    }

    // ====================== Helper/Getter/Setter ======================


    public Mysterybox getMbox() {
        return mbox;
    }

    public void setMbox(Mysterybox mbox) {
        this.mbox = mbox;
    }

    public static void main(String[] args) {
        // Hier kannst du deine Implementierungen testen
        Mysterybox mbox = new Mysterybox(0, "");
        Lockpicker lp = new Lockpicker(mbox);
//        System.out.println("12345678".substring(0,2));
//        System.out.println("12345678".substring(2,4));
//        System.out.println("12345678".substring(4,6));
//        System.out.println("12345678".substring(6,8));

//        System.out.println(lp.decryptInteger(69));
//        System.out.println(lp.decryptNumericalString("27410889"));
//        String a = "12345678";
//        System.out.println(a.substring(a.length()-4));
//        System.out.println(lp.decryptMinimal(0.123f, 4.567f));
        System.out.println(lp.solve());
    }
}
