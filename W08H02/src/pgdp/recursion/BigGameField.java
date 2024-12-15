package pgdp.recursion;

public class BigGameField {
    protected int[][] board; // 9x9 game field (0 = empty)
    protected boolean[][] fixed; // Indicates whether a cell is fixed

    public BigGameField(int[][] initialGrid) {
        board = initialGrid;
        fixed = new boolean[9][9];

        for (int i = 0; i < fixed.length; i++) {
            for (int j = 0; j < fixed[0].length; j++) {
                if (board[i][j] != 0) {
                    fixed[i][j] = true;
                }
            }
        }
    }

    protected boolean isSafe(int row, int col, int num) {
        return isRowSafe(row, num) && isColSafe(col, num) && isBlockSafe(row, col, num);
    }

    protected boolean isRowSafe(int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == num) {
                return false;
            }
        }

        return true;
    }

    protected boolean isColSafe(int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (board[row][col] == num) {
                return false;
            }
        }

        return true;
    }

    protected boolean isBlockSafe(int startRow, int startCol, int num) {
        startRow = (startRow / 3) * 3;
        startCol = (startCol / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    protected void placePenguin(int row, int col, int num) {
        if (isSafe(row, col, num) && !isFixed(row, col)) {
            board[row][col] = num;
        }
    }


    protected void removePenguin(int row, int col) {
        if (!isFixed(row, col)) {
            board[row][col] = 0;
        }
    }


    //This method print the grid with the numbers of placed penguins on it.
    public void printGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        BigGameField bigGameField = new BigGameField(new int[9][9]);
        System.out.println(bigGameField.isColSafe(0, 0));

    }
}
