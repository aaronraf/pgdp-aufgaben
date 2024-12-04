package pgdp.saleuine;

import java.math.BigDecimal;

public class TradeOrder {

    private BigDecimal totalCost;
    private int currentWeight;
    private boolean orderFulfilled;

    public TradeOrder() {
        totalCost = BigDecimal.ZERO;
        currentWeight = 0;
        orderFulfilled = false;
    }

    public boolean supplyOrder(PinguFood supply, BigDecimal cost) {
        if (!supply.isEdible()) {
            return false;
        }

        setTotalCost(getTotalCost().add(cost));
        orderFulfilled = true;

        return false;
    }

    public boolean isOrderFulfilled() {
        return orderFulfilled;
    }

    public String orderType() {
        return "Einzeln";
    }

    @Override
    public String toString() {
        return String.format("Die Bestellung(%s) hat ein Gesamtgewicht von %dg und kostet %sPD.", orderType(), currentWeight, totalCost.toString());
    }

//    -- Getter --

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

//    -- Setter --

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

}
