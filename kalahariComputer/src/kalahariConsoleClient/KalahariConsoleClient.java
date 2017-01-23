package kalahariConsoleClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kalahariBackendStateless.KalahariFieldsMove;
import kalahariBackendStateless.KalahariGameStateless;
import kalahariMinMax.KalahariMinMax;
import kalahariMinMax.KalahariMoveAndResult;

/**
 * Kalahari Computer game client
 * 
 * @author Theo Wilhelm, Jan. 2017
 * @version from 06. January 2017
 *
 */
public class KalahariConsoleClient {
	final static Logger log = LogManager.getLogger(KalahariConsoleClient.class);

	KalahariGameStateless newGame = new KalahariGameStateless();
	String humanStartsGame;
	String[] playerName = new String[2];
	Scanner in;
	int selectedField;
	KalahariFieldsMove newMove = new KalahariFieldsMove();
	KalahariFieldsMove initialMove = new KalahariFieldsMove();
	KalahariFieldsMove moveResult = new KalahariFieldsMove();
	int result;
	// TODO check if object declarations should really be done here!?
	KalahariMoveAndResult recommendedMoves = new KalahariMoveAndResult();
	KalahariMinMax minMax = new KalahariMinMax();

	/**
	 * Constructor: Does nothing.
	 */
	public KalahariConsoleClient() {
	}

	/**
	 * Starts and controls game; Offers menu to play game(s), read the manual and exit the game.
	 */
	public void start() {
		Scanner in = new Scanner(System.in);
		int option = 0;
		String inString = "";
		boolean readingFailed = true;

		while (option != 4) {
			do {
				System.out.println();
				System.out.println();
				System.out.println("1 - Start a new one player game (human vs. computer)?");
				System.out.println("2 - Start a new two player game (human vs. humain)?");
				System.out.println("3 - Read the manual?");
				System.out.println("4 - Exit the game?");
				inString = in.nextLine();
				try {
					// the String to int conversion happens here
					option = Integer.parseInt(inString.trim());
					readingFailed = false;
				} catch (NumberFormatException nfe) {
					System.out.println("Please select option by entering 1, 2, 3 or 4!");
					readingFailed = true;
				}
			} while (readingFailed);
			switch (option) {
			case 1:
				log.info("-----------------------------------------------------------");
				log.info("Choice 1 selected: new one player game (human vs. computer)");
				playGame(1);
				break;
			case 2:
				log.info("--------------------------------------------------------");
				log.info("Choice 2 selected: new two player game (human vs. human)");
				playGame(2);
				break;
			case 3:
				log.info("----------------------------------");
				log.info("Choice 3 selected: Read the manual");
				DisplayRules();
				break;
			case 4:
				option = 4;
				log.info("----------------------------");
				log.info("Choice 4 selected: Exit game");
				System.out.println("You left the game!");
				break;
			}
		}
		in.close();
	}

	/**
	 * Draws complete field on the screen: players names, fields and their content, schematic outline of Kalahari board.
	 * 
	 * @param fields
	 */
	private void drawFields(int[] fields) {
		for (int clear = 0; clear < 1000; clear++) {
			System.out.println("\b");
		}
		System.out.println("");
		System.out.println("\t  --------------------------------------------------------");
		System.out.println("\t  *               KALAHARI Computer by Wilhelm                    *");
		System.out.println("\t  --------------------------------------------------------");
		System.out.println("\n\n\n");
		System.out.println("\t" + playerName[0] + "'s Fields\n");
		System.out.println("\t\t\t    10  9  8  7  6    \n");
		System.out.println("\t\t\t /-------------------\\");

		System.out.println("\t\t\t/    " + fields[10] + "  " + fields[9] + "  " + fields[8] + "  " + fields[7] + "  " + fields[6] + "    \\");

		System.out.println("\t\t\t| " + fields[11] + "  ------------- " + fields[5] + "  |");
		System.out.println("\t\t\t\\    " + fields[0] + "  " + fields[1] + "  " + fields[2] + "  " + fields[3] + "  " + fields[4] + "    /");
		System.out.println("\t\t\t \\-------------------/ \n");
		System.out.println("\t\t\t     0  1  2  3  4    \n");
		System.out.println("\t" + playerName[1] + "'s Fields");
	}

	/**
	 * start, control and finish the KalahariConsole Game
	 * 
	 * @param gameType
	 *            "1" for single player game and "2" for two player game.
	 * 
	 */
	public void playGame(int gameType) {
		Scanner in = new Scanner(System.in);
		String inString = "";
		Boolean readingFailed = false;
		int computerPlayer = 99;
		List<Integer> compMovesToDraw = new ArrayList<>();
		// praefixPlayer is used to format log output nicely
		String[] praefixPlayer = { "", "\t" };

		/*
		 * Reset fields. That way they start with a newly, correctly arranged board...
		 */
		moveResult.reset();
		newMove.reset();

		/*
		 * The two lines of code below initialize the one player game in a simplified manner. Was/might be useful for
		 * test and debugging reasons.
		 */
		///int[] testStartField = { 0, 0, 0, 2, 1, 13, 0, 0, 0, 1, 0, 13 };
		///moveResult.setFields(testStartField);

		// Read players' names and initialize game
		// Case human versus computer (one player game)
		if (gameType == 1) {
			System.out.print("Enter your name: ");
			playerName[0] = in.nextLine();
			// We assume first human starts game
			humanStartsGame = "yes";
			playerName[1] = "Computer";
			computerPlayer = 1;
			System.out.print("Do you want to start (yes/no)? ");
			humanStartsGame = in.nextLine();
			if (humanStartsGame.equalsIgnoreCase("no")) {
				playerName[1] = playerName[0];
				playerName[0] = "Computer";
				computerPlayer = 0;
			}
		} else {
			// Case human versus human (two player game)
			System.out.print("Enter name of player 1:\t");
			playerName[0] = in.nextLine();
			System.out.print("Enter name of player 2:\t");
			playerName[1] = in.nextLine();
		}
		log.info("Player 1: " + playerName[0] + "            Player 2: " + playerName[1]);

		// Outer loop: Do until end of game reached; MoveReturnCode != 3
		while (moveResult.getMoveReturnCode() != 3) {
			do {
				// Inner loop: Do until other player is to draw (MoveReturnCode 1) or
				// end of game reached (MoveReturnCode 3)
				do {
					// Draw game board/fields and ask player to select field for her move
					drawFields(moveResult.getFields());
					System.out.print("\n\n\n\tChoose the field for your move, ");
					if (moveResult.getPlayerToDraw() == 0) {
						System.out.print(playerName[0]);
						System.out.print(" (6, 7, 8, 9, 10): ");
					} else {
						System.out.print(playerName[1]);
						System.out.print(" (0, 1, 2, 3, 4): ");
					}

					// if playerToDraw is human (not computer) - read player's choice via console
					if (moveResult.getPlayerToDraw() != computerPlayer) {
						inString = in.nextLine();
						try {
							// the String to int conversion happens here
							selectedField = Integer.parseInt(inString.trim());
							readingFailed = false;
						} catch (NumberFormatException nfe) {
							readingFailed = true;
						}
					}
				} while (readingFailed);
				newMove.setPlayerToDraw(moveResult.getPlayerToDraw());
				newMove.setFields(moveResult.getFields());

				// if playerToDraw human (not computer) - define field to draw according to human player's choice
				if (moveResult.getPlayerToDraw() != computerPlayer) {
					newMove.setSelectedMove(selectedField);
					log.info(playerName[newMove.getPlayerToDraw()] + "'s move: " + praefixPlayer[newMove.getPlayerToDraw()] + "[" + newMove.getSelectedMove() + "]");
					moveResult = newGame.performMove(newMove);
					// if playerToDraw is computer
				} else {
					// If there are no moves left to be drawn from previous request -> get moves from MinMax algorithm
					if (compMovesToDraw.isEmpty()) {
						minMax.clearNumberOfEvaluatedMoves();
						// computer has one or more options -> we get a good one from the minMax algorithm
						recommendedMoves = minMax.getRecommendedMove(newMove, computerPlayer, computerPlayer, 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
						log.info("Number of moves evaluated for computer's next move(s): " + minMax.getNumberOfEvaluatedMoves());
						compMovesToDraw = recommendedMoves.getPreviousMove();
					}
					newMove.setSelectedMove(compMovesToDraw.get(0));
					newMove.setPlayerToDraw(computerPlayer);
					System.out.print(compMovesToDraw.get(0) + "\t(out of " + recommendedMoves.getPreviousMove() + ")");
					log.info(playerName[newMove.getPlayerToDraw()] + "'s move: " + praefixPlayer[newMove.getPlayerToDraw()] + "[" + newMove.getSelectedMove() + "] \tout of "
							+ recommendedMoves.getPreviousMove());
					compMovesToDraw.remove(0);
					moveResult = newGame.performMove(newMove);
					if (moveResult.getMoveReturnCode() == 0) {
						log.error("ERROR: moveResult.getMoveReturnCode() == 0; playerToDraw and Move(s) missmathch. Aborting game!");
						System.exit(0);
					}
					// Pause for 4 seconds
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} while (moveResult.getMoveReturnCode() != 1 && moveResult.getMoveReturnCode() != 3);
		}

		drawFields(moveResult.getFields());
		System.out.println("\n\t*********************************************");
		System.out.println("\n\t**             GAME OVER                   **");
		System.out.println("\n\t*********************************************");
		if (moveResult.getField(11) > moveResult.getField(5)) {
			System.out.println("\n\t" + playerName[0] + " won " + moveResult.getField(11) + " over " + moveResult.getField(5));
			log.info(playerName[0] + " won " + moveResult.getField(11) + " over " + moveResult.getField(5));
		} else if (moveResult.getField(11) < moveResult.getField(5)) {
			System.out.println("\n\t" + playerName[1] + " won " + moveResult.getField(5) + " over " + moveResult.getField(11));
			log.info(playerName[1] + " won " + moveResult.getField(5) + " over " + moveResult.getField(11));
		} else {
			System.out.println("\n\t" + "The game ended in a draw!");
			log.info("The game ended in a draw!");
		}

	}

	/**
	 * Display the rules of the Kalahari game
	 */
	private void DisplayRules() {
		// Display the Kalahari games rules
		System.out.println(newGame.getRules());
	}

}