package pgdp.FlipperFlow.Components;

public class Cable extends Piece implements Conductive {
    private double currentInput;
    private double voltageInput;

    public void setCurrentInput(double currentInput) {
        this.currentInput = currentInput;
    }

    public void setVoltageInput(double voltageInput) {
        this.voltageInput = voltageInput;
    }

    @Override
    public double getCurrentInput() {
        return currentInput;
    }

    @Override
    public double getCurrentOutput() {
        return currentInput;
    }

    @Override
    public double getVoltageInput() {
        return voltageInput;
    }

    @Override
    public double getVoltageOutput() {
        return voltageInput;
    }
}
