package kalahariBackendStateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to generate objects holding data for
 * <ul>
 * <li>current status of fields (number of tokens per field),
 * <li>player to draw (player 0 or player 1; -99 after game has ended),
 * <li>selected move (index of field to draw from),
 * <li>status of performed move.
 * </ul>
 * 
 * @author Theo Wilhelm, Jan. 2017
 * @version from 06. January 2017
 *
 */
public class KalahariFieldsMove implements Cloneable {
	final static Logger log = LogManager.getLogger(KalahariFieldsMove.class);

	private int[] fields = new int[12];
	private int playerToDraw;
	private int selectedMove;
	private int moveReturnCode;
	StringBuffer strBuff = new StringBuffer();

	/**
	 * Constructor initializing data:
	 * <ul>
	 * <li>playerToDraw = 0,
	 * <li>selectedMove = 99,
	 * <li>moveReturCode = 99,
	 * <li>fields = { 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 0 }
	 * </ul>
	 */
	public KalahariFieldsMove() {
		// By definition the first player to draw is PlayerOne
		playerToDraw = 0;
		selectedMove = 99; // 99 stands for "no field selected so far, no move
							// happened yet"
		moveReturnCode = 99; // 99 stands for "no field selected so far, no move
								// happened yet"

		// initialize fields
		for (int i = 0; i <= 4; i++) {
			fields[i] = 3;
			fields[i + 6] = 3;
			fields[5] = 0;
			fields[11] = 0;
		}
	}

	/**
	 * Method to reset fields (same values as upon initialization)
	 * 
	 */
	public void reset() {
		this.playerToDraw = 0;
		this.selectedMove = 99; // 99 stands for "no field selected so far, no
								// move
		// happened yet"
		this.moveReturnCode = 99; // 99 stands for "no field selected so far, no
									// move
									// happened yet"

		// initialize fields
		for (int i = 0; i <= 4; i++) {
			this.fields[i] = 3;
			this.fields[i + 6] = 3;
			this.fields[5] = 0;
			this.fields[11] = 0;
		}
	}

	/**
	 * Setter method for fields
	 * 
	 * @param newFields
	 *            new number of tokens that are to be assigned to fields
	 */
	public void setFields(int[] newFields) {
		for (int i = 0; i <= 11; i++)
			this.fields[i] = newFields[i];
	}

	/**
	 * Getter method for fields
	 * 
	 * @return fields: number of tokens contained by each field)
	 */
	public int[] getFields() {
		return fields;
	}

	/**
	 * Setter method for single field this methods sets fields[index] = content
	 * 
	 * @param index
	 *            index of field to change
	 * @param content
	 *            new value of field
	 */
	public void setField(int index, int content) {
		this.fields[index] = content;
	}

	/**
	 * Getter method for single fields
	 * 
	 * @param index
	 *            index of the field that is requested.
	 * 
	 * @return fields number of tokens contained by field[index]
	 */
	public int getField(int index) {
		return fields[index];
	}

	/**
	 * Setter method for playerToDraw
	 * 
	 * @param newPlayerToDraw
	 *            new player to draw
	 */
	public void setPlayerToDraw(int newPlayerToDraw) {
		this.playerToDraw = newPlayerToDraw;
	}

	/**
	 * Getter method for playerToDraw
	 * 
	 * @return playerToDraw player who is to draw next (0 ... for player 1 and 1 ... for player 2; -99 after game has
	 *         ended)
	 */
	public int getPlayerToDraw() {
		return playerToDraw;
	}

	/**
	 * Setter method for selectedMove
	 * 
	 * @param newSelectedMove
	 *            new selected move
	 */
	public void setSelectedMove(int newSelectedMove) {
		this.selectedMove = newSelectedMove;
	}

	/**
	 * Getter method for selectedMove
	 * 
	 * @return selectedMove index (0..11) of field to draw from
	 */
	public int getSelectedMove() {
		return selectedMove;
	}

	/**
	 * Setter method for moveReturnCode
	 * 
	 * @param newMoveReturnCode
	 *            new status of move
	 */
	public void setMoveReturnCode(int newMoveReturnCode) {
		this.moveReturnCode = newMoveReturnCode;
	}

	/**
	 * Getter method for moveReturnCode
	 * 
	 * @return moveReturnCode:
	 *         <ul>
	 *         <li>0 ... error: selected field outside allowed range or empty (0..4 and 6..10)</li>
	 *         <li>1 ... success: Successful move; other player's turn now.</li>
	 *         <li>2 ... success: Successful move; same player moves again.</li>
	 *         <li>3 ... success: Successful move; game over.</li>
	 *         </ul>
	 */
	public int getMoveReturnCode() {
		return moveReturnCode;
	}

	/**
	 * equals method for objects of KalahariFieldsMove
	 * 
	 * @param other
	 *            object to be compared to
	 * 
	 * @return false or true: true in case fields[], playerToDraw, selectedMove and moveReturnCode are identical.
	 */
	public boolean equals(KalahariFieldsMove other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (other.getClass() != getClass())
			return false;

		for (int i = 0; i <= 11; i++) {
			if (this.fields[i] != other.fields[i])
				return false;
		}
		if (this.playerToDraw != other.playerToDraw)
			return false;
		if (this.selectedMove != other.selectedMove)
			return false;
		if (this.moveReturnCode != other.moveReturnCode)
			return false;
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
		KalahariFieldsMove copyObj = new KalahariFieldsMove();

		for (int i = 0; i <= 11; i++)
			copyObj.fields[i] = this.fields[i];
		copyObj.setPlayerToDraw(this.getPlayerToDraw());
		copyObj.setSelectedMove(this.getSelectedMove());
		copyObj.setMoveReturnCode(this.getMoveReturnCode());
		return copyObj;
		// return super.clone();
	}

	/**
	 * prints D data via System.out.print...
	 * 
	 */
	public void printData() {
		strBuff.delete(0, strBuff.length());
		strBuff.append("fields: [ ( ");
		for (int i = 0; i <= 4; i++) {
			strBuff.append(fields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(fields[5] + " |) ( ");
		for (int i = 6; i <= 10; i++) {
			strBuff.append(fields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(fields[11] + " | ) ];   ");

		// print out playerToDraw, selectedMove, moveReturnCode
		strBuff.append("playerToDraw: " + playerToDraw + "; ");
		strBuff.append("selectedMove: " + selectedMove + "; ");
		strBuff.append("moveReturnCode: " + moveReturnCode);

		System.out.println(strBuff);
		log.debug(strBuff);
	}

	/**
	 * returns data (information contained in object) as StringBuffer.
	 * 
	 * @return: data (information contained in object) as StringBuffer.
	 * 
	 */
	public StringBuffer getDataAsStringBuffer() {
		strBuff.delete(0, strBuff.length());
		strBuff.append("fields: [ ( ");
		for (int i = 0; i <= 4; i++) {
			strBuff.append(fields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(fields[5] + " |) ( ");
		for (int i = 6; i <= 10; i++) {
			strBuff.append(fields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(fields[11] + " | ) ];   ");

		// print out playerToDraw, selectedMove, moveReturnCode
		strBuff.append("playerToDraw: " + playerToDraw + "; ");
		strBuff.append("selectedMove: " + selectedMove + "; ");
		strBuff.append("moveReturnCode: " + moveReturnCode);

		return strBuff;
	}

	/**
	 * returns fields as StringBuffer.
	 * 
	 * @return: fields as StringBuffer.
	 * 
	 */
	public StringBuffer getFieldsAsStringBuffer() {
		strBuff.delete(0, strBuff.length());
		strBuff.append("fields: [ ( ");
		for (int i = 0; i <= 4; i++) {
			strBuff.append(fields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(fields[5] + " |) ( ");
		for (int i = 6; i <= 10; i++) {
			strBuff.append(fields[i] + "; ");
		}
		strBuff.append("| ");
		strBuff.append(fields[11] + " | ) ];   ");

		return strBuff;
	}
}
