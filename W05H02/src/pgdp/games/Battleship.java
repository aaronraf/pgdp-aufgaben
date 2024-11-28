package pgdp.games;

public class Battleship {

	private static final String WELCOME_MESSAGE = "Welcome to Battleships";
	private static final String PLAY_MESSAGE = "Let's start the game!";
	private static final String WIN_MESSAGE = "Congrats! You won!";
	private static final String LOSE_MESSAGE = "Too bad, you lost!";

	private static final String PRINT_FIRST_LINE = "Your Board:\t\t\tAI Board:";
	private static final String PRINT_SECOND_LINE = "+ a b c d e f g h\t\t+ a b c d e f g h";
	private static final String TABS = "\t\t";

	private static final String PLAYER_BOAT = " ■";
	private static final String BOAT_MISSED = " o";
	private static final String BOAT_HIT = " x";
	private static final String WATER = "  ";

	private Player player;
	private AI ai;

	public Battleship(Player player, AI ai) {
		this.player = player;
		this.ai = ai;
	}

	public static void main(String[] args) {
		new Battleship(new Player(), new AI()).game();
	}

	/**
	 * Runs the whole game.
	 */
	public void game() {
		// TODO 7
		System.out.println(WELCOME_MESSAGE);
		printBoard(player.getBoard(), ai.getBoard());
		player.generatePlayerBoard();

		System.out.println(PLAY_MESSAGE);
		boolean playerStillInPlay;
		boolean aiStillInPlay;
		do {
			boolean playerHit;
			boolean aiHit = true;

			do {
				playerHit = player.play(ai.getBoard());
				printBoard(player.getBoard(), ai.getBoard());
				playerStillInPlay = hasShipsLeft(player.getBoard());
				aiStillInPlay = hasShipsLeft(ai.getBoard());
			} while (playerHit && playerStillInPlay && aiStillInPlay);

			while (aiHit && playerStillInPlay && aiStillInPlay) {
				aiHit = ai.play(player.getBoard());
				printBoard(player.getBoard(), ai.getBoard());
				playerStillInPlay = hasShipsLeft(player.getBoard());
				aiStillInPlay = hasShipsLeft(ai.getBoard());
			}

		} while (playerStillInPlay && aiStillInPlay);

		if (!aiStillInPlay) {
			System.out.println(WIN_MESSAGE);
		} else {
			System.out.println(LOSE_MESSAGE);
		}
	}

	/**
	 * Checks if there are any ships left.
	 * 
	 * @param board
	 * @return true if at least one ship exists (partially)
	 */
	public boolean hasShipsLeft(int[][] board) {
		for (int line = 0; line < board.length; line++) {
			for (int row = 0; row < board[line].length; row++) {
				if (board[line][row] == 1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Prints both boards to the console
	 * 
	 * @param playerBoard
	 * @param aiBoard
	 */
	public static void printBoard(int[][] playerBoard, int[][] aiBoard) {
		System.out.println(PRINT_FIRST_LINE);
		System.out.println(PRINT_SECOND_LINE);
		StringBuilder sb = new StringBuilder();
		for (int line = 0; line < playerBoard.length; line++) {
			System.out.println(linePrinter(playerBoard, line, true) + TABS + linePrinter(aiBoard, line, false));
		}
	}

	private static String linePrinter(int[][] board, int line, boolean playersBoard) {
		StringBuilder sb = new StringBuilder();
		sb.append(line + 1);

		for (int row = 0; row < board[line].length; row++) {
			if (board[line][row] == 1 && playersBoard) {
				sb.append(PLAYER_BOAT);
			} else if (board[line][row] == 2) {
				sb.append(BOAT_MISSED);
			} else if (board[line][row] == 3) {
				sb.append(BOAT_HIT);
			} else {
				sb.append(WATER);
			}
		}
		return sb.toString();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}
}
