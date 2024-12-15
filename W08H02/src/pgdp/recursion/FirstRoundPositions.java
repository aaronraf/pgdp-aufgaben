package pgdp.recursion;

public class FirstRoundPositions {
    protected BigGameField bigGameField;
    protected boolean solutionFound;

    public FirstRoundPositions(int[][] initialGrid) {
        this.bigGameField = new BigGameField(initialGrid);
        this.solutionFound = false;
    }

    protected boolean placeAllPenguins(int row, int col) {
        if (row == 9) {
            return true;
        }

        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col == 8) ? 0 : col + 1;

        if (bigGameField.isFixed(row, col)) {
            return placeAllPenguins(nextRow, nextCol);
        }

        for (int num = 1; num <= 9; num++) {
            if (bigGameField.isSafe(row, col, num)) {
                bigGameField.placePenguin(row, col, num);

                if (placeAllPenguins(nextRow, nextCol)) {
                    return true;
                }

                bigGameField.removePenguin(row, col);
            }
        }

        return false;
    }

    public void solvePuzzle() {
        if (placeAllPenguins(0, 0)) {
            System.out.println("Solution found:");
            bigGameField.printGrid();
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void main(String[] args) {
        //Here are some given examples below.
        // You can extend those to test your implementation.

        // Example Nr. 1 easy
        int[][] easyGrid = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        System.out.println("Solution found - easy grid:");
        FirstRoundPositions solver = new FirstRoundPositions(easyGrid);
        solver.solvePuzzle();

        // Example Nr. 2 medium
        int[][] mediumGrid = {
                {0, 0, 0, 6, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        System.out.println("Solution found - medium grid:");
        FirstRoundPositions solver2 = new FirstRoundPositions(mediumGrid);
        solver2.solvePuzzle();

        // Example Nr. 2 medium
        int[][] hardGrid = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 3, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 6, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        System.out.println("Solution found - hard grid:");
        FirstRoundPositions solver3 = new FirstRoundPositions(hardGrid);
        solver3.solvePuzzle();
    }
}