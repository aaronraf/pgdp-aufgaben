package pgdp.FlipperFlow.Components;

import pgdp.FlipperFlow.Helpers.Vector2;

public class Resistor extends Cable {
    private double minCurrent;
    private double maxCurrent;
    private double minVoltage;
    private double maxVoltage;
    private Vector2 positionInGrid;

    public Resistor(double resistance, double minCurrent, double minVoltage, double maxCurrent, double maxVoltage) {
        super.setResistance(resistance);
        this.minCurrent = minCurrent;
        this.maxCurrent = maxCurrent;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
    }

    public void setPositionInGrid(Vector2 positionInGrid) {
        this.positionInGrid = positionInGrid;
    }

    public String getStatus() {
        return getCurrentInput() < minCurrent || getCurrentInput() > maxCurrent || getVoltageInput() < minVoltage || getVoltageInput() > maxVoltage ? "NOT WORKING" : "WORKING";
    }

    @Override
    public double getVoltageOutput() {
        return getVoltageInput() - getResistance() * getCurrentInput();
    }

    @Override
    public String toString() {
        return String.format(
                "HI I AM RESISTOR AT: %s, CURRENTLY %s\nMY STATS ARE: RESISTANCE %.2f, CURRENT %.2f, VOLTAGE_INPUT %.2f, VOLTAGE_OUTPUT %.2f\nMY SPECIFICATIONS ARE: MIN_CURRENT %.2f, MAX_CURRENT %.2f, MIN_VOLTAGE %.2f, MAX_VOLTAGE %.2f\n",
                positionInGrid, getStatus(), getResistance(), getCurrentInput(), getVoltageInput(), getVoltageOutput(), minCurrent, maxCurrent, minVoltage, maxVoltage
        );
    }
}
