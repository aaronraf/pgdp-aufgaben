package pgdp.expedition;

public class Fish {

    private String type;
    private String preparation;
    private int amount;

    public Fish(String type, String preparation) {
        this.type = type;
        this.preparation = preparation;
        amount = 1;
    }

    public Fish(String type) {
        this(type, "fresh");
    }

    public void prepare(String preparation) {
        this.preparation = preparation;
    }

    public int add(Fish fish) {
        amount += fish.getAmount();
        return amount;
    }

    public int eat() {
        return --amount;
    }

    @Override
    public String toString() {
        return preparation + " " + type;
    }

    public String getType() {
        return type;
    }

    public String getPreparation() {
        return preparation;
    }

    public int getAmount() {
        return amount;
    }
}
