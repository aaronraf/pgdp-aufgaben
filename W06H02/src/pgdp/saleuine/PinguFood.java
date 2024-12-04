package pgdp.saleuine;

public class PinguFood {

    private int age;
    private int weight;

    public PinguFood(int age, int weight) {
        this.age = age;
        this.weight = weight;
    }

    public boolean isEdible() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("Alter: %d Jahre, Gewicht: %dg", age, weight);
    }

    //    -- Getter --

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

}
