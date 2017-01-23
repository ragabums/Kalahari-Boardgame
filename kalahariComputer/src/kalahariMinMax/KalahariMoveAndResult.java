package kalahariMinMax;

/**
 * Class to generate objects holding data for Move and resulting Field. Hereby a
 * Move is defined as all fields a player drew from until the other player's
 * turn is reached. A such defined Move may consist of multiple single moves,
 * e.g. in the case the same player has to draw again.
 * 
 * Moreover the field contains a "Score" field that can be used to evaluate the move in e.g. a min max algorithm.
 * <ul>
 * <li>previousMove: List of Integer containing the fields the player drew from until the other player's turn was reached.
 * <li>resultingFields: current status of fields (number of tokens per field), the result of the previousMove 
 * <li>playerToDraw: player to draw next after move was made (player 0 or player 1)
 * <li>score: used in min-max Algorithm to return the score
 * </ul>
 * 
 * @author Theo Wilhelm, Jan. 2017
 * @version from 06. January 2017
 *
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KalahariMoveAndResult implements Cloneable {
	final static Logger log = LogManager.getLogger(KalahariMoveAndResult.class);

	private List<Integer> previousMove = new ArrayList<>();
	private int[] resultingFields = new int[12];
	private int playerToDraw;
	private int score;
	StringBuffer strBuff = new StringBuffer();

	/**
	 * Constructor initializing data:
	 * <ul>
	 * <li>previousMove = {},
	 * <li>resultingFields = { 99, 99, 99, 99, 99, 0, 99, 99, 99, 99, 99, 0 },
	 * <li>playerToDraw = 99,
	 * <li>score = 99,
	 * </ul>
	 */
	public KalahariMoveAndResult() {
		playerToDraw = 99;
		score = 99;
		for (int i = 0; i <= 4; i++) {
			resultingFields[i] = 99;
			resultingFields[i + 6] = 99;
			resultingFields[5] = 0;
			resultingFields[11] = 0;
		}
	}

	/**
	 * Method to reset fields (same values as upon initialization)
	 * 
	 */
	public void reset() {
		this.playerToDraw = 99;
		this.previousMove.clear();
		this.score = 99;
		for (int i = 0; i <= 4; i++) {
			this.resultingFields[i] = 99;
			this.resultingFields[i + 6] = 99;
			this.resultingFields[5] = 0;
			this.resultingFields[11] = 0;
		}
	}

	/**
	 * Setter method for resultingFields
	 * 
	 * @param newFields
	 *            new number of tokens that are to be assigned to fields
	 */
	public void setResultingFields(int[] newFields) {
		for (int i = 0; i <= 11; i++)
			this.resultingFields[i] = newFields[i];
	}

	/**
	 * Getter method for resultingFields
	 * 
	 * @return fields: number of tokens contained by each field)
	 */
	public int[] getResultingFields() {
		return resultingFields;
	}

	/**
	 * Setter method for a single field of resultingFields
	 * 
	 * @param index:
	 *            index of field to change
	 * @param content:
	 *            new value of field
	 */
	public void setResultingField(int index, int content) {
		this.resultingFields[index] = content;
	}

	/**
	 * Getter method for single fields
	 * 
	 * @param index
	 *            index of the field that is requested.
	 * 
	 * @return fields: number of tokens contained by field[index]
	 */
	public int getResultingField(int index) {
		return resultingFields[index];
	}

	/**
	 * Setter method for playerToDraw
	 * 
	 * @param newPlayerToDraw:
	 *            new player to draw
	 */
	public void setPlayerToDraw(int newPlayerToDraw) {
		this.playerToDraw = newPlayerToDraw;
	}

	/**
	 * Getter method for playerToDraw
	 * 
	 * @return playerToDraw: player who is to draw next (0 ... for player 1 and 1 ... for player 2)
	 */
	public int getPlayerToDraw() {
		return playerToDraw;
	}

	/**
	 * Setter method for score
	 * 
	 * @param newScore
	 *            new score
	 */
	public void setScore(int newScore) {
		this.score = newScore;
	}

	/**
	 * Getter method for score
	 * 
	 * @return score: score of this move
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Setter method for previousMove: All elements are set at once here.
	 * 
	 * @param Move
	 *            List of integer representing the subsequent move(s) of one player until the next player is to draw.
	 */
	public void setPreviousMove(List<Integer> Move) {
		this.previousMove.clear();
		this.previousMove = Move;
	}

	/**
	 * Setter method to add a single move to the List previousMove
	 * 
	 * @param newSelectedSingleMove
	 *            new selected single move
	 */
	public void addToPreviousMove(int newSelectedSingleMove) {
		this.previousMove.add(newSelectedSingleMove);
	}

	/**
	 * Getter method specific element (int) of previousMove element with index i
	 * 
	 * @param index
	 *            what element of previousMove is to be returned
	 * 
	 * @return: returns single move out of the list previousMove
	 */
	public int getSpecifiedPreviousMove(int index) {
		return this.previousMove.get(index);
	}

	/**
	 * Getter method for previousMove
	 * 
	 * @return previousMove: List of Integer containing the index (0..11) of fields the last player drew from to reach
	 *         the resultingField
	 */
	public List<Integer> getPreviousMove() {
		return this.previousMove;
	}

	/**
	 * equals method for objects of KalahariMoveAndResult
	 * 
	 * @param other
	 *            object to be compared to
	 * 
	 * @return false or true: true in case previousMove, resultingFields[] and playerToDraw are identical.
	 */
	public boolean equals(KalahariMoveAndResult other) {
		int i = 0;

		if (this == other)
			return true;
		if (other == null)
			return false;
		if (other.getClass() != getClass())
			return false;

		// Are previousMove(s) equal?
		if (this.previousMove.size() != other.previousMove.size()) {
			log.warn("this.previousMove.size() != other.previousMove.size()");
			return false;
		}

		for (i = 0; i <= (this.previousMove.size() - 1); i++) {
			if (this.previousMove.get(i) != other.previousMove.get(i)) {
				log.warn("KalahariMoveAndResult.equals WARNING: this.previousMove.get(" + i + ") != other.previousMove.get(" + i + ")");
				return false;
			}
		}

		// Are resultingFields(s) equal?
		for (i = 0; i <= 11; i++) {
			if (this.resultingFields[i] != other.resultingFields[i]) {
				log.warn("KalahariMoveAndResult.equals WARNING: this.resultingFields[" + i + "] != other.resultingFields[" + i + "]");
				return false;
			}
		}

		// Are player(s)ToDraw equal?
		try {
			if (this.playerToDraw != other.playerToDraw) {
				log.warn("KalahariMoveAndResult.equals WARNING: this.playerToDraw != other.playerToDraw");
				return false;
			}
		} catch (Exception e) {
			log.info("Exception in KalahariMoveAndResult.equals test if playerToDraw are equal", e);
		}

		// Are scores equal?
		try {
			if (this.score != other.score) {
				log.warn("KalahariMoveAndResult.equals WARNING: this.score != other.score");
				return false;
			}
		} catch (Exception e) {
			log.info("Exception in KalahariMoveAndResult.equals test if scrores are equal", e);
		}

		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 * 
	 *      Overrides clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		KalahariMoveAndResult copyObj = new KalahariMoveAndResult();

		for (int i = 0; i <= 11; i++)
			copyObj.resultingFields[i] = this.resultingFields[i];
		copyObj.setPlayerToDraw(this.getPlayerToDraw());
		copyObj.setScore(this.getScore());
		copyObj.setPreviousMove(this.getPreviousMove());
		return copyObj;
		// return super.clone();
	}

	/**
	 * prints Data via System.out.print...
	 * 
	 */
	public void printData() {
		strBuff.delete(0, strBuff.length());
		strBuff.append("Move: " + previousMove + "; ");
		strBuff.append("playerToDraw: " + playerToDraw + "; ");
		strBuff.append("score: " + score + "; ");
		//
		strBuff.append("resultingFields: [ ( ");
		for (int i = 0; i <= 4; i++) {
			strBuff.append(resultingFields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(resultingFields[5] + " | ) ( ");
		for (int i = 6; i <= 10; i++) {
			strBuff.append(resultingFields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(resultingFields[11] + " | ) ];   ");

		System.out.println(strBuff);
		log.debug(strBuff);
	}

	/**
	 * @return: data (information contained in object) as StringBuffer.
	 * 
	 */
	public StringBuffer getDataAsStringBuffer() {
		strBuff.delete(0, strBuff.length());
		strBuff.append("Move: " + previousMove + "; ");
		strBuff.append("playerToDraw: " + playerToDraw + "; ");
		strBuff.append("score: " + score + "; ");
		//
		strBuff.append("resultingFields: [ ( ");
		for (int i = 0; i <= 4; i++) {
			strBuff.append(resultingFields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(resultingFields[5] + " | ) ( ");
		for (int i = 6; i <= 10; i++) {
			strBuff.append(resultingFields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(resultingFields[11] + " | ) ];   ");

		return strBuff;
	}
}
