package pgdp.FlipperFlow.Components;

import pgdp.FlipperFlow.Helpers.Direction;

public abstract class Piece {
    private Direction inputDirection;
    private Direction outputDirection;
    private boolean inserted;
    private double resistance;

    public Direction getInputDirection() {
        return inputDirection;
    }

    public void setInputDirection(Direction inputDirection) {
        if (this.inputDirection == null) {
            this.inputDirection = inputDirection;
            this.inserted = true;
        }
    }

    public Direction getOutputDirection() {
        return outputDirection;
    }

    public void setOutputDirection(Direction outputDirection) {
        if (this.outputDirection == null) {
            this.outputDirection = outputDirection;
        }
    }

    public boolean isInserted() {
        return inserted;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }
}
