package pgdp.games;

import static pgdp.PinguLib.*;

public class Player {

	private static final String PLACE_LENGTH_4 = "Place a ship of length 4. Enter the coordinates of both ends.";
	private static final String PLACE_LENGTH_3 = "Place a ship of length 3.";
	private static final String PLACE_LENGTH_2 = "Place a ship of length 2.";
	private static final String PLACE_ERROR = "There is a problem with your coordinates. Try again!";

	private static final String ALREADY_HIT = "Field is already hit!";
	private static final String MISSED = "You missed. Better luck next time!";
	private static final String HIT = "You hit it!";

	private static final String ENTER_NUMBER = "Enter number coordinate:";
	private static final String ENTER_LETTER = "Enter letter coordinate:";

	private int[][] board;

	public Player(int[][] board) {
		this.board = board;
	}

	public Player() {
		this.board = new int[8][8];
	}

	/**
	 * Generates the player board with 1x ships of length 4, 2x ships of length 3
	 * and 3x ships of length 2.
	 */
	public void generatePlayerBoard() {
		board = new int[8][8];
//		1x 4
		placeShip(4, PLACE_LENGTH_4);
//		2x 3
		placeShip(3, PLACE_LENGTH_3);
		placeShip(3, PLACE_LENGTH_3);
//		3x 2
		placeShip(2, PLACE_LENGTH_2);
		placeShip(2, PLACE_LENGTH_2);
		placeShip(2, PLACE_LENGTH_2);
	}

	/**
	 * Places a ship of given length on the player board.
	 * 
	 * @param length
	 * @param message
	 */
	public void placeShip(int length, String message) {
		boolean invalidCoordinates = false;
		int row1;
		int row2;
		int col1;
		int col2;

		do {
			System.out.println(message);
			row1 = readNumber();
			col1 = readCharacter();
			row2 = readNumber();
			col2 = readCharacter();

			if (!((row1 == row2 && col2 - col1 == length - 1) || (col1 == col2 && row2 - row1 == length - 1))) {
				System.out.println(PLACE_ERROR);
				invalidCoordinates = true;
				continue;
			}

			for (int row = row1; row <= row2; row++) {
				for (int col = col1; col <= col2; col++) {
					if (!checkCoordinate(row, col)) {
						System.out.println(PLACE_ERROR);
						invalidCoordinates = true;
						break;
					}
				}

				if (invalidCoordinates) {
					break;
				}
			}
		} while (invalidCoordinates);

		setShip(row1, col1, row2, col2);
		Battleship.printBoard(board, new int[8][8]);
	}

	/**
	 * Sets the ship between the two given coordinates.
	 * 
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 */
	public void setShip(int row1, int col1, int row2, int col2) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row >= row1 && row <= row2 && col >= col1 && col <= col2) {
					board[row][col] = 1;
				}
			}
		}
	}

	/**
	 * Checks the given coordinate if there are no adjacent ships around.
	 * 
	 * @param x
	 * @param y
	 * @return true if it is not adjacent to any existing ship
	 */
	public boolean checkCoordinate(int x, int y) {
		if (board[x][y] != 0 //
				|| x + 1 < 8 && board[x + 1][y] != 0 //
				|| y + 1 < 8 && board[x][y + 1] != 0 //
				|| x - 1 >= 0 && board[x - 1][y] != 0 //
				|| y - 1 >= 0 && board[x][y - 1] != 0) {
			return false;
		}
		return true;
	}

	/**
	 * Plays one move of the player. Repeatedly asks the player to input coordinates
	 * until they are valid
	 * 
	 * @param aiBoard
	 * @return true if the player hit a ship
	 */
	public boolean play(int[][] aiBoard) {
		int row;
		int col;

		do {
			row = readNumber();
			col = readCharacter();

			if (aiBoard[row][col] == 1) {
				System.out.println(HIT);
				aiBoard[row][col] = 3;
				return true;
			} else if (aiBoard[row][col] == 0) {
				System.out.println(MISSED);
				aiBoard[row][col] = 2;
				return false;
			}
			System.out.println(ALREADY_HIT);
		} while (true);
	}

	/**
	 * Reads and returns a number between 1 and 8 (inclusive) from std-in.
	 * 
	 * @return the number - 1
	 */
	public static int readNumber() {
		int number = readInt(ENTER_NUMBER);
		while (number < 1 || number > 8) {
			number = readInt(ENTER_NUMBER);
		}
		return number - 1;
	}

	/**
	 * Reads and returns a char between a and h (inclusive) from std-in.
	 * 
	 * @return the char - 'a'
	 */
	public static int readCharacter() {
		char character = readChar(ENTER_LETTER);
		while (character < 'a' || character > 'h') {
			character = readChar(ENTER_LETTER);
		}
		return character - 'a';
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public static void main(String[] args) {
		Player p = new Player(new int[8][8]);
		p.placeShip(2, "tes length 2");
	}

}
