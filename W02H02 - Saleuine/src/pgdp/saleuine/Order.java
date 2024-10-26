package pgdp.saleuine;

public class Order {
    private int amountCrustaceans;
    private int amountAnchovies;
    private int amountSardines;

    public Order(int amountCrustaceans, int amountAnchovies, int amountSardines) {
        this.amountCrustaceans = amountCrustaceans;
        this.amountAnchovies = amountAnchovies;
        this.amountSardines = amountSardines;
    }

    @Override
    public String toString() {
        return amountCrustaceans + " Krustentiere, " + amountAnchovies + " Sardellen und " + amountSardines + " Sardinen";
    }

    public void addOrder(Order order) {
        this.amountAnchovies += order.amountAnchovies;
        this.amountCrustaceans += order.amountCrustaceans;
        this.amountSardines += order.amountSardines;
    }

    public int getAmountCrustaceans() {
        return amountCrustaceans;
    }

    public int getAmountAnchovies() {
        return amountAnchovies;
    }

    public int getAmountSardines() {
        return amountSardines;
    }
}
