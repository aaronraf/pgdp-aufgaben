package pgdp.saleuine;

public class Sardine extends PinguFood {

    private static int MIN_AGE = 1;
    private static int MIN_WEIGHT = 100;
    private static int MIN_LENGTH = 14;
    private int length;

    public Sardine(int age, int weight, int length) {
        super(age, weight);
        this.length = length;
    }

    @Override
    public boolean isEdible() {
        return this.getAge() >= MIN_AGE && this.getWeight() >= MIN_WEIGHT && length >= MIN_LENGTH;
    }

    @Override
    public String toString() {
        return String.format("Sardine(Alter: %d Jahre, Gewicht: %dg, Länge: %d)", getAge(), getWeight(), length);
    }

    //    -- Getter --

    public int getLength() {
        return length;
    }

}
