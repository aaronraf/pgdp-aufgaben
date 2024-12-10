package pgdp.FlipperFlow;

import pgdp.FlipperFlow.Components.Cable;
import pgdp.FlipperFlow.Components.Piece;
import pgdp.FlipperFlow.Components.PowerSource;
import pgdp.FlipperFlow.Helpers.Direction;
import pgdp.FlipperFlow.Helpers.Vector2;

public class Circuit {
    private Piece[][] grid;
    private Vector2 powerSourcePosition;
    private Vector2 openEnd;

    public Circuit(int width, int height, int sourceX, int sourceY) {
        this.grid = new Piece[height][width];
        this.powerSourcePosition = new Vector2(sourceX, sourceY);
        this.openEnd = powerSourcePosition;

        Direction inputDirection = Direction.LEFT;
        Direction outputDirection = Direction.RIGHT;
        if (sourceX == width - 1) {
            if (sourceY == 0) {
                outputDirection = Direction.DOWN;
            } else {
                outputDirection = Direction.UP;
            }
        }
        if (sourceX == 0) {
            if (sourceY == 0) {
                inputDirection = Direction.DOWN;
            } else {
                inputDirection = Direction.UP;
            }
        }
        grid[sourceY][sourceX] = new PowerSource(inputDirection, outputDirection);
    }

    public PowerSource getPowerSource() {
        return (PowerSource) grid[powerSourcePosition.getY()][powerSourcePosition.getX()];
    }

    public void setSourceVoltage(double voltage) {
        getPowerSource().setVoltage(voltage);
    }

    private void setSourceCurrent(double current) {
        getPowerSource().setCurrent(current);
    }

    public Piece getAt(int x, int y) {
        return (x >= grid.length || y >= grid[0].length) ? null : grid[y][x];
    }

    private boolean positionIsEmpty(Vector2 position) {
        int x = position.getX();
        int y = position.getY();
        return y < grid.length && x < grid[0].length && grid[y][x] == null;
    }

    private Vector2[] getPossibleLinkPositions() {
        return null;
    }

    public boolean insertAt(Piece piece, int x, int y) {
        if (
                piece.getClass().equals(PowerSource.class) && !positionIsEmpty(powerSourcePosition)
                || !positionIsEmpty(new Vector2(x, y))
                || piece.isInserted()
        ) {
            return false;
        }

        Piece recent = grid[openEnd.getY()][openEnd.getX()];
        Vector2 newPosition = new Vector2(x, y);
        Direction relativePosition = Direction.getDirectionFromCoordinates(openEnd, newPosition);
        switch (recent.getInputDirection()) {
            case LEFT -> {
                if (relativePosition == Direction.LEFT || !positionIsEmpty(newPosition)) {
                    return false;
                }
            }
            case RIGHT -> {
                if (relativePosition == Direction.RIGHT || !positionIsEmpty(newPosition)) {
                    return false;
                }
            }
            case UP -> {
                if (relativePosition == Direction.UP || !positionIsEmpty(newPosition)) {
                    return false;
                }
            }
            case DOWN -> {
                if (relativePosition == Direction.DOWN || !positionIsEmpty(newPosition)) {
                    return false;
                }
            }
        };

        grid[y][x] = piece;
        return true;
    }

    public LinkedCircuit<Cable, PowerSource> generateLinked() {
        return null;
    }

    public void propagate() {

    }

    public String printStatusReport() {
        return "";
    }
}
