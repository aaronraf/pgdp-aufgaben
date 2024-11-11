package pgdp;

import java.util.Random;

public class Mysterybox {
    // Input-Parameter tragen den Namen der Methode für die sie bestimmt sind:
    // Beispiel: decryptMinimal(float f1, float f2) --> decryptModular(minimalVal1, minimalVal2)

    private final float floatHint;
    private final float minimalVal1;
    private final float minimalVal2;
    private final int modularVal1;
    private final int modularVal2;
    private final int intHint;
    private final int polarVal1;
    private final byte polarVal2;

    private final Random random;
    private final String pwd;

    public Mysterybox(long seed, String pwd) {
        this.random = new Random(seed);
        // set parameter
        this.floatHint = random.nextFloat(1, 10_000);
        this.minimalVal1 = random.nextFloat(1, 10_000);
        this.minimalVal2 = random.nextFloat(1, 10_000);
        this.modularVal1 = random.nextInt();
        this.modularVal2 = random.nextInt();
        this.intHint = random.nextInt();
        this.polarVal1 = random.nextInt();
        this.polarVal2 = (byte) random.nextInt(1, 256);
        this.pwd = pwd;
    }

    public boolean checkSolution(String passwordAttempt) {
        return pwd.equals(passwordAttempt);
    }

    public float getFloatHint() {
        return floatHint;
    }
    public float getMinimalVal1() {
        return minimalVal1;
    }
    public float getMinimalVal2() {
        return minimalVal2;
    }
    public int getIntHint() {
        return intHint;
    }
    public int getModularVal1() {
        return modularVal1;
    }
    public int getModularVal2() {
        return modularVal2;
    }
    public int getPolarVal1() {
        return polarVal1;
    }
    public byte getPolarVal2() {
        return polarVal2;
    }

    @Override
    public String toString() {
        return "Mysterybox {" +
            "\n  intHint=" + intHint +
            ",\n  floatHint=" + floatHint +
            ",\n  minimalVal1=" + minimalVal1 +
            ",\n  minimalVal2=" + minimalVal2 +
            ",\n  modularVal1=" + modularVal1 +
            ",\n  modularVal2=" + modularVal2 +
            ",\n  polarVal1=" + polarVal1 +
            ",\n  polarVal2=" + polarVal2 +
            ",\n  pwd='" + pwd + '\'' +
            "\n}";
    }
}
