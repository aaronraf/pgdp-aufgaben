package pgdp.FlipperFlow.Components;

import pgdp.FlipperFlow.Helpers.Direction;

public class PowerSource extends Piece {
    private double voltage;
    private double current;

    public PowerSource(Direction inputDirection, Direction outputDirection) {
        super.setInputDirection(inputDirection);
        super.setOutputDirection(outputDirection);
        this.voltage = 10.0;
        this.current = 0.0;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        if (this.voltage != 10.) {
            this.voltage = voltage;
        }
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        if (this.current != 0.) {
            this.current = current;
        }
    }
}
