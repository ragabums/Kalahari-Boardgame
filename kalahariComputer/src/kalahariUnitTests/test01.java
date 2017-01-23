package kalahariUnitTests;

import kalahariBackendStateless.KalahariFieldsMove;
import kalahariBackendStateless.KalahariGameStateless;

public class test01 {

	public static void main(String[] args) {
		int i = 0;
		KalahariGameStateless testGame = new KalahariGameStateless();
		KalahariFieldsMove newMove = new KalahariFieldsMove();

		// Test move
		newMove.setSelectedMove(10);
		newMove.setPlayerToDraw(0);
		int[] fieldsBefore = { 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 0 };
		newMove.setFields(fieldsBefore);

		KalahariFieldsMove moveResult = new KalahariFieldsMove();

		moveResult = testGame.performMove(newMove);
		System.out.println("movereturncode, message: " + moveResult.getMoveReturnCode() + ", '"
				+ testGame.getMoveReturnMessage(moveResult.getMoveReturnCode()) + "'");
		System.out.println("playertodraw: " + moveResult.getPlayerToDraw());
		for (i = 0; i <= 11; i++)
			System.out.println("fields[" + i + "]: " + moveResult.getFields()[i]);

		// Test KalahariFieldsMove.printData()
		System.out.println("***************************************\n\n\n");

		System.out.println("\n --> newMove (after move): ");
		newMove.printData();

		System.out.println("\n --------> moveResult: ");
		moveResult.printData();

		// Test KalahariFieldsMove.clone() and KalahariFieldsMove.equals()
		try {
			KalahariFieldsMove clonedMove = (KalahariFieldsMove) moveResult.clone();
			System.out.println("\n --> cloned Move (from moveResult): ");
			clonedMove.printData();

			// check if KalahariFieldsMove.equals() works
			System.out
					.println("\n\n\n Are clonedMove and moveResult equal? " + moveResult.equals(clonedMove) + "\n\n\n");

			// alter data of cloned Move and check if data of moveResult are
			// altered too
			clonedMove.setPlayerToDraw(99);
			clonedMove.setSelectedMove(99);
			int[] newClonedFields = { 99, 99, 99, 99, 3, 0, 3, 3, 3, 3, 3, 0 };
			clonedMove.setFields(newClonedFields);
			System.out.println("\n --> cloned Move (after altering it): ");
			clonedMove.printData();
			System.out.println("\n --------> moveResult (after altering cloned Move): ");
			moveResult.printData();

			// check if KalahariFieldsMove.equals() works
			System.out
					.println("\n\n\n Are clonedMove and moveResult equal? " + moveResult.equals(clonedMove) + "\n\n\n");
		} catch (CloneNotSupportedException cnse) {
			System.out.println("Cloneable should be implemented. " + cnse);
		}

	}
}
