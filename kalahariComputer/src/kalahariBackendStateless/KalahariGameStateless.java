package kalahariBackendStateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Kalahari game backend.
 * 
 * @author Theo Wilhelm
 * @version from 02.05.2016
 *
 */
public class KalahariGameStateless {
	final static Logger log = LogManager.getLogger(KalahariGameStateless.class);

	private final String RULES_TEXT = "Description:\n" + "------------\n" + "Netkala  is a  game for  two players.  To my knowledge it \n"
			+ "comes  from  Afrika,  where it was  played  in  sand  with\n" + "marbles  or Kaori shells as pawns (pieces). It is a simple\n"
			+ "and short game.  One rather needs to be clever than  lucky\n" + "to  win.  It has a similar character as 'draughts' (Ameri-\n"
			+ "can 'checker', German 'Dame') or the German kind of  'mor-\n" + "ris'  (called  'Muehle').  \n" + "\n\n" + "How to play netkala:\n" + "--------------------\n"
			+ "Each  player  sits  in front of a row of five holes (indi-\n" + "cated by the five small 'o' signs next to the line Play-\n"
			+ "er 1/2 on  the  schematic sketch  bellow), and  has a big \n" + "whole to his right (indicated by capital 'O'):\n" + "\n" + "                  Player 1\n"
			+ "               o  o  o  o  o\n" + "            O                 O\n" + "               o  o  o  o  o\n" + "                  Player 2\n" + "\n"
			+ "At the beginning of the game all the small ('o') holes are\n" + "filled  with three pieces each.  The aim of the game is to\n" + "collect as many pieces in his big hole.\n"
			+ "An arbitrary player starts (usually  the  one,  that  dies\n" + "earlier).   He can choose one of his small holes and move.\n"
			+ "He does this by picking all the  pieces  of  the  hole  he\n" + "choose,  and  distributing  them  over  all  the following\n" + "holes.\n" + "\n"
			+ "To understand what I mean by following holes you  have  to\n" + "think  of  all the holes forming an accidently flattened\n"
			+ "circle.  Moreover we define  the  direction  of  the  game\n" + "opposite  to  the  direction of conventional clocks (swiss\n"
			+ "quality clocks).  By distributing it is  meant,  that  the\n" + "player  leaves  exactly one piece in each of the following\n"
			+ "holes. No matter whether its his or his  opponents  whole.\n" + "And no matter whether its a small or a big whole.\n" + "\n"
			+ "Usually  the players play one after each other and nothing\n" + "embarrassing will happen. To avoid, that kalahari becomes\n" + "too boring, there are three extra rules:\n" + "\n"
			+ "\n" + "Rule 1: If  the  last piece in a move goes into one of the\n" + "        big  side  holes (capital  'O'),  the  same player \n" + "        plays again.\n" + "\n"
			+ "Rule 2: If  the  last  piece  in a  move goes into a small \n" + "        hole,  that is  empty,  the  player  can  take all \n"
			+ "        pieces from the small hole opposite to it, and put \n" + "        them  into  his  big  hole (no matter,  whether the \n"
			+ "        pieces  come  from  one of  his, or his  opponents \n" + "        small holes).\n" + "\n" + "Rule 3: At the  time  one  of  the players can not move any \n"
			+ "        more because he hasn't a single piece in one of his \n" + "        holes  left, the  other player can collect all the \n"
			+ "        pieces  that are left  in his  small holes and put \n" + "        them into  his  big  hole.\n" + "        The number of pieces in the big holes are compared.\n"
			+ "        The one who was able to collect more wins.  \n" + "        The game is finished.\n";

	/**
	 * Constructor does basic object construction only.
	 */
	public KalahariGameStateless() {
	}

	/**
	 * Returns a String explaining the game and its rules.
	 * 
	 * @return String explaining the game and its rules.
	 */
	public String getRules() {
		return RULES_TEXT;
	}

	/**
	 * Performs a players move: takes all tokens/mussels from the field the player chose to draw from
	 * (KalahariFieldsMove private variable fieldToMove) and spreads content to subsequent fields by adding one
	 * token/mussel to each.
	 * 
	 * @param selectedMove
	 *            object of class KalahariFieldsMove) containing <code>fields</code>, <code>fieldToMove</code>,
	 *            <code>playerToDraw</code> and <code>moveReturnCode</code> prior to move.
	 * 
	 *            <ul>
	 *            <li>0 ... error: selected Field outside allowed range (0..4 and 6..10)
	 *            <li>1 ... success: Successful move; other player's turn now.
	 *            <li>2 ... success: Successful move; same player moves again.
	 *            <li>3 ... success: Successful move; game over.
	 *            </ul>
	 * 
	 * @return KalahariFieldsMove containing data (see above) after move
	 */
	public KalahariFieldsMove performMove(KalahariFieldsMove selectedMove) {
		KalahariFieldsMove newFieldsMove = new KalahariFieldsMove();
		int[] fields = new int[11];
		fields = selectedMove.getFields();
		int selectedField = selectedMove.getSelectedMove();
		int playerToDraw = selectedMove.getPlayerToDraw();
		int fieldToReach = 0;
		int i = 0;

		newFieldsMove.setFields(fields);
		newFieldsMove.setPlayerToDraw(playerToDraw);
		newFieldsMove.setSelectedMove(selectedField);

		// Check if selectedField is allowed according to playerToDraw and board
		// layout; check if selectedField is not empty
		if (playerToDraw == 0) {
			if (selectedField < 6 || selectedField > 10 || fields[selectedField] == 0) {
				log.warn("WARNING: selectedField outside allowed range or empty. playerToDraw=" + playerToDraw + ";\t selectedField=" + selectedField);
				newFieldsMove.setPlayerToDraw(0);
				newFieldsMove.setMoveReturnCode(0); // selected field outside allowed range or empty
				return newFieldsMove;
			}
		} else {
			if (selectedField < 0 || selectedField > 4 || fields[selectedField] == 0) {
				log.warn("WARNING: selectedField outside allowed range or empty. playerToDraw=" + playerToDraw + ";\t selectedField=" + selectedField);
				newFieldsMove.setPlayerToDraw(1);
				newFieldsMove.setMoveReturnCode(0); // selected field outside allowed range or empty
				return newFieldsMove;
			}
		}

		// perform move by spreading mussels to subsequent fields
		fieldToReach = selectedField + fields[selectedField];
		fields[selectedField] = 0; // empty selectedField
		if (fieldToReach <= 11) {
			// spread tokens/mussels from selectedField to subsequent fields
			for (i = selectedField + 1; i <= fieldToReach; i++)
				fields[i]++;
			// eat tokens from field opposite to the field the last token ended
			// up
			if (fields[fieldToReach] == 1 && fieldToReach != 5 && fieldToReach != 11) {
				if (selectedField > 5) {
					fields[11] += fields[10 - fieldToReach];
				} else {
					fields[5] += fields[10 - fieldToReach];
				}
				fields[10 - fieldToReach] = 0;
			}
		} else {
			// spread tokens/mussels from selectedField to subsequent fields
			for (i = selectedField + 1; i <= 11; i++)
				fields[i]++;
			for (i = fieldToReach - 12; i >= 0; i--)
				fields[i]++;
			// eat tokens from field opposite to the field the last token ended
			// up
			if (fields[fieldToReach - 12] == 1 && (fieldToReach - 12) != 5 && (fieldToReach - 12) != 11) {
				if (selectedField > 5) {
					fields[11] += fields[10 - (fieldToReach - 12)];
				} else {
					fields[5] += fields[10 - (fieldToReach - 12)];
				}
				fields[10 - (fieldToReach - 12)] = 0;
			}
		}

		/*
		 * Check if move ended in a bate|side field
		 * 
		 * If so: --> Check if current players fields are empty --> If so: Game over (current player has no tokens to
		 * move anymore)
		 * 
		 * If not: --> the same player can/must move again
		 */
		// Check if move ended in a bate|side field
		if (fieldToReach == 11 || fieldToReach == 5 || fieldToReach == 17 || fieldToReach == 23) {
			if ((playerToDraw == 0) && ((fields[6] + fields[7] + fields[8] + fields[9] + fields[10]) == 0)
					|| (playerToDraw == 1) && ((fields[0] + fields[1] + fields[2] + fields[3] + fields[4]) == 0)) {
				moveTokensToSideFields(fields);

				newFieldsMove.setMoveReturnCode(3); // Game over
				newFieldsMove.setFields(fields);
				return newFieldsMove;
			}
			newFieldsMove.setMoveReturnCode(2); // same players move again
			newFieldsMove.setFields(fields);
			return newFieldsMove;
		}

		/*
		 * Check if other players fields are empty - --> if so, add remaining tokens to bate|side field; game over
		 */
		if ((playerToDraw == 0) && ((fields[0] + fields[1] + fields[2] + fields[3] + fields[4]) == 0) || (playerToDraw == 1) && ((fields[6] + fields[7] + fields[8] + fields[9] + fields[10]) == 0)) {
			moveTokensToSideFields(fields);
			newFieldsMove.setMoveReturnCode(3); // Game over
			newFieldsMove.setFields(fields);
			return newFieldsMove;
		}

		// toggle player that is to draw next
		if (playerToDraw == 0) {
			playerToDraw = 1;
		} else {
			playerToDraw = 0;
		}
		newFieldsMove.setMoveReturnCode(1);
		newFieldsMove.setPlayerToDraw(playerToDraw);
		newFieldsMove.setFields(fields);
		return newFieldsMove;
	}

	/**
	 * Moves remaining tokens to bate/side fields
	 * 
	 * @param fields
	 */
	private void moveTokensToSideFields(int[] fields) {
		int i = 0;

		// move remaining tokens to bate/side fields
		for (i = 0; i <= 4; i++) {
			fields[5] += fields[i];
			fields[i] = 0;
		}

		for (i = 6; i <= 10; i++) {
			fields[11] += fields[i];
			fields[i] = 0;
		}
	}

	/**
	 * Returns human readable error or success messages for the requested error/success code.
	 * 
	 * @param performMoveReturnCode
	 *            Code for that a human readable message is requested
	 * @return human readable error or success message.
	 */
	public String getMoveReturnMessage(int performMoveReturnCode) {
		switch (performMoveReturnCode) {
		case 0:
			return "Error: selected Field outside allowed range (0..4 and 6..10)";
		case 1:
			return "Success: successful move; other player's turn now.";
		case 2:
			return "Success: Successful move; same player moves again.";
		case 3:
			return "Success: successful move; game over.";
		default:
			return "Error: Unknown returnCode";
		}
	}

}