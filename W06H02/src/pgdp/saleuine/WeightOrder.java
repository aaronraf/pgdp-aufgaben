package pgdp.saleuine;

import java.math.BigDecimal;

public class WeightOrder extends TradeOrder {

    public final int targetWeight;

    public WeightOrder(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    @Override
    public String orderType() {
        return String.format("Zielgewicht: %dg", targetWeight);
    }

    @Override
    public String toString() {
        return String.format("Die Bestellung(%s) hat ein Gesamtgewicht von %dg und kostet %sPD.", orderType(), getCurrentWeight(), getTotalCost().toString());
    }

    @Override
    public boolean isOrderFulfilled() {
        return getCurrentWeight() >= targetWeight;
    }

    @Override
    public boolean supplyOrder(PinguFood supply, BigDecimal cost) {
        if (!supply.isEdible() || getCurrentWeight() >= targetWeight) {
            return false;
        }

        setCurrentWeight(getCurrentWeight() + supply.getWeight());
        setTotalCost(getTotalCost().add(cost));

        return true;
    }
}
