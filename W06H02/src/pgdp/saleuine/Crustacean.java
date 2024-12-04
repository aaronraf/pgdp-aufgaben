package pgdp.saleuine;

public class Crustacean extends PinguFood {

    public Crustacean(int weight) {
        super(0, weight);
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("Krill(%dg)", getWeight());
    }

}
