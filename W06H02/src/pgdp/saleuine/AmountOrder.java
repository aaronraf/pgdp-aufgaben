package pgdp.saleuine;

import java.math.BigDecimal;

public class AmountOrder extends TradeOrder {

    private final int targetAmountAnchovies;
    private final int targetAmountCrustaceans;
    private final int targetAmountSardines;
    private int currentAmountAnchovies;
    private int currentAmountCrustaceans;
    private int currentAmountSardines;

    public AmountOrder(int targetAmountAnchovies, int targetAmountCrustaceans, int targetAmountSardines) {
        this.targetAmountAnchovies = targetAmountAnchovies;
        this.targetAmountCrustaceans = targetAmountCrustaceans;
        this.targetAmountSardines = targetAmountSardines;

        this.currentAmountAnchovies = 0;
        this.currentAmountCrustaceans = 0;
        this.currentAmountSardines = 0;
    }

    @Override
    public String orderType() {
        return String.format("Anzahl: [%d,%d,%d]", targetAmountAnchovies, targetAmountCrustaceans, targetAmountSardines);
    }

    @Override
    public String toString() {
        return String.format("Die Bestellung(%s) hat ein Gesamtgewicht von %dg und kostet %sPD.", orderType(), getCurrentWeight(), getTotalCost().toString());
    }

    @Override
    public boolean isOrderFulfilled() {
        return currentAmountAnchovies >= targetAmountAnchovies && currentAmountSardines >= targetAmountSardines && currentAmountCrustaceans >= targetAmountCrustaceans;
    }

    @Override
    public boolean supplyOrder(PinguFood supply, BigDecimal cost) {
        if (!supply.isEdible()) {
            return false;
        }

        if (supply.getClass().equals(Anchovie.class)) {
            if (currentAmountAnchovies >= targetAmountAnchovies) {
                return false;
            }

            currentAmountAnchovies += supply.getWeight();
        } else if (supply.getClass().equals(Crustacean.class)) {
            if (currentAmountCrustaceans >= targetAmountCrustaceans) {
                return false;
            }

            currentAmountCrustaceans += supply.getWeight();
        } else if (supply.getClass().equals(Sardine.class)) {
            if (currentAmountSardines >= targetAmountSardines) {
                return false;
            }

            currentAmountSardines += supply.getWeight();
        }

        setCurrentWeight(getCurrentWeight() + supply.getWeight());
        setTotalCost(getTotalCost().add(cost));

        return true;
    }

}
