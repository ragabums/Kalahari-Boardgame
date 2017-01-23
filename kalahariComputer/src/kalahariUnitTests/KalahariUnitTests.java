package kalahariUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import kalahariBackendStateless.KalahariFieldsMove;
import kalahariBackendStateless.KalahariGameStateless;
import kalahariMinMax.KalahariMinMax;
import kalahariMinMax.KalahariMoveAndResult;

/**
 * Unit Tests for backend parts of the Kalahari game program.
 *  
 * @author Theo Wilhelm, Jan. 2017
 * @version from 06. January 2017
 * 
 */
public class KalahariUnitTests {

	@Test
	public final void testGetAvailableMovesONE() {
		int[] startFields = { 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 8, 0 };
		int playerToDrawStart = 0;
		int playerToDrawExpected = 1;
		List<KalahariMoveAndResult> availableMovesComputed = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesExpected = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesTemp = new ArrayList<>();
		KalahariMoveAndResult startMoveAndResult = new KalahariMoveAndResult();
		KalahariMinMax minMax = new KalahariMinMax();
		int i = 0;

		System.out.println("\n\n*************** testGetAvailableMovesONE ************************");
		System.out.print("playerToDrawStart: " + playerToDrawStart + ";     startField: ");
		System.out.println(java.util.Arrays.toString(startFields));

		// setup startMove
		startMoveAndResult.setPlayerToDraw(playerToDrawStart);
		startMoveAndResult.setResultingFields(startFields);
		// get List of availableMoves
		availableMovesComputed = minMax.getAvailableMoves(startMoveAndResult, availableMovesTemp);

		// print out availableMovesComputed
		System.out.println("availableMovesComputed:");
		for (KalahariMoveAndResult Move : availableMovesComputed) {
			Move.printData();
		}

		int[][] expectedFields = { { 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 8, 0 }, { 1, 2, 1, 1, 0, 1, 1, 1, 0, 0, 0, 2 } };
		int[][] tempPreviousMoves = { { 7 }, { 10 } };

		for (i = 0; i <= (expectedFields.length - 1); i++) {
			KalahariMoveAndResult MoveAndResult = new KalahariMoveAndResult();
			MoveAndResult.setResultingFields(expectedFields[i]);
			MoveAndResult.setPlayerToDraw(playerToDrawExpected);
			// List of previousMoves is a bit more complicated
			for (int j = 0; j <= (tempPreviousMoves[i].length - 1); j++) {
				MoveAndResult.addToPreviousMove(tempPreviousMoves[i][j]);
			}
			availableMovesExpected.add(MoveAndResult);
		}
		// print out availableMovesExpected
		System.out.println();
		System.out.println("availableMovesExpected:");
		for (KalahariMoveAndResult Move : availableMovesExpected) {
			Move.printData();
		}

		// assertions
		// check if availableMovesComputed and availableMovesExpected are
		// identical
		System.out.println();
		System.out.println("check if availableMovesComputed and availableMovesExpected are identical");
		for (i = 0; i <= (availableMovesComputed.size() - 1); i++) {
			assertTrue("availableMovesComputed(" + i + ") !=  availableMovesExpected(" + i + ")", availableMovesComputed.get(i).equals(availableMovesExpected.get(i)));
			System.out.println("Move(" + availableMovesComputed.get(i).getPreviousMove() + ") is ok!");
		}
	}

	@Test
	public final void testGetAvailableMovesTWO() {
		int[] startFields = { 0, 0, 3, 0, 1, 0, 0, 0, 0, 2, 1, 0 };
		int playerToDrawStart = 1;
		int playerToDrawExpected = playerToDrawStart * (-1) + 1;
		List<KalahariMoveAndResult> availableMovesComputed = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesExpected = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesTemp = new ArrayList<>();
		KalahariMoveAndResult startMoveAndResult = new KalahariMoveAndResult();
		KalahariMinMax minMax = new KalahariMinMax();
		int i = 0;

		System.out.println("\n\n*************** testGetAvailableMovesTWO ************************");
		System.out.print("playerToDrawStart: " + playerToDrawStart + ";     startField: ");
		System.out.println(java.util.Arrays.toString(startFields));

		// setup startMove
		startMoveAndResult.setPlayerToDraw(playerToDrawStart);
		startMoveAndResult.setResultingFields(startFields);
		// get List of availableMoves
		availableMovesComputed = minMax.getAvailableMoves(startMoveAndResult, availableMovesTemp);

		// print out availableMovesComputed
		System.out.println("availableMovesComputed:");
		for (KalahariMoveAndResult Move : availableMovesComputed) {
			Move.printData();
		}

		int[][] expectedFields = { { 0, 0, 0, 0, 3, 1, 0, 0, 0, 2, 1, 0 }, { 0, 0, 0, 1, 0, 2, 1, 0, 0, 2, 1, 0 }, { 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 1, 0 }, { 0, 0, 0, 0, 1, 3, 0, 0, 0, 2, 1, 0 } };
		int[][] tempPreviousMoves = { { 2, 3 }, { 2, 4 }, { 4, 2, 3 }, { 4, 2, 4, 3 } };

		for (i = 0; i <= (expectedFields.length - 1); i++) {
			KalahariMoveAndResult MoveAndResult = new KalahariMoveAndResult();
			MoveAndResult.setResultingFields(expectedFields[i]);
			MoveAndResult.setPlayerToDraw(playerToDrawExpected);
			// List of previousMoves is a bit more complicated
			for (int j = 0; j <= (tempPreviousMoves[i].length - 1); j++) {
				MoveAndResult.addToPreviousMove(tempPreviousMoves[i][j]);
			}
			availableMovesExpected.add(MoveAndResult);
		}
		// print out availableMovesExpected
		System.out.println();
		System.out.println("availableMovesExpected:");
		for (KalahariMoveAndResult Move : availableMovesExpected) {
			Move.printData();
		}

		// assertions
		// check if availableMovesComputed and availableMovesExpected are
		// identical
		System.out.println();
		System.out.println("check if availableMovesComputed and availableMovesExpected are identical");
		for (i = 0; i <= (availableMovesComputed.size() - 1); i++) {
			assertTrue("availableMovesComputed(" + i + ") !=  availableMovesExpected(" + i + ")", availableMovesComputed.get(i).equals(availableMovesExpected.get(i)));
			System.out.println("Move(" + availableMovesComputed.get(i).getPreviousMove() + ") is ok!");
		}
	}

	@Test
	public final void testGetAvailableMovesTHREE() {
		int[] startFields = { 0, 1, 0, 0, 1, 0, 0, 0, 0, 2, 1, 0 };
		int playerToDrawStart = 0;
		int playerToDrawExpected = playerToDrawStart * (-1) + 1;
		List<KalahariMoveAndResult> availableMovesComputed = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesExpected = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesTemp = new ArrayList<>();
		KalahariMoveAndResult startMoveAndResult = new KalahariMoveAndResult();
		KalahariMinMax minMax = new KalahariMinMax();
		int i = 0;

		System.out.println("\n\n*************** testGetAvailableMovesTHREE ************************");
		System.out.print("playerToDrawStart: " + playerToDrawStart + ";     startField: ");
		System.out.println(java.util.Arrays.toString(startFields));

		// setup startMove
		startMoveAndResult.setPlayerToDraw(playerToDrawStart);
		startMoveAndResult.setResultingFields(startFields);
		// get List of availableMoves
		availableMovesComputed = minMax.getAvailableMoves(startMoveAndResult, availableMovesTemp);

		// print out availableMovesComputed
		System.out.println("availableMovesComputed:");
		for (KalahariMoveAndResult Move : availableMovesComputed) {
			Move.printData();
		}

		int[][] expectedFields = { { 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2 }, { 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 3 } };
		int[][] tempPreviousMoves = { { 9, 10 }, { 10, 9, 10 } };

		for (i = 0; i <= (expectedFields.length - 1); i++) {
			KalahariMoveAndResult MoveAndResult = new KalahariMoveAndResult();
			MoveAndResult.setResultingFields(expectedFields[i]);
			if (i == 0) {
				MoveAndResult.setPlayerToDraw(playerToDrawExpected);
			} else {
				MoveAndResult.setPlayerToDraw(-99);
			}
			// List of previousMoves is a bit more complicated
			for (int j = 0; j <= (tempPreviousMoves[i].length - 1); j++) {
				MoveAndResult.addToPreviousMove(tempPreviousMoves[i][j]);
			}
			availableMovesExpected.add(MoveAndResult);
		}
		// print out availableMovesExpected
		System.out.println();
		System.out.println("availableMovesExpected:");
		for (KalahariMoveAndResult Move : availableMovesExpected) {
			Move.printData();
		}

		// assertions
		// check if availableMovesComputed and availableMovesExpected are
		// identical
		System.out.println();
		System.out.println("check if availableMovesComputed and availableMovesExpected are identical");
		for (i = 0; i <= (availableMovesComputed.size() - 1); i++) {
			assertTrue("availableMovesComputed(" + i + ") !=  availableMovesExpected(" + i + ")", availableMovesComputed.get(i).equals(availableMovesExpected.get(i)));
			System.out.println("Move(" + availableMovesComputed.get(i).getPreviousMove() + ") is ok!");
		}
	}

	@Test
	public final void testGetAvailableMovesFOUR() {
		int[] startFields = { 0, 1, 0, 0, 1, 0, 0, 1, 0, 2, 1, 0 };
		int playerToDrawStart = 0;
		int playerToDrawExpected = playerToDrawStart * (-1) + 1;
		List<KalahariMoveAndResult> availableMovesComputed = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesExpected = new ArrayList<>();
		List<KalahariMoveAndResult> availableMovesTemp = new ArrayList<>();
		KalahariMoveAndResult startMoveAndResult = new KalahariMoveAndResult();
		KalahariMinMax minMax = new KalahariMinMax();
		int i = 0;

		System.out.println("\n\n*************** testGetAvailableMovesFOUR ************************");
		System.out.print("playerToDrawStart: " + playerToDrawStart + ";     startField: ");
		System.out.println(java.util.Arrays.toString(startFields));

		// setup startMove
		startMoveAndResult.setPlayerToDraw(playerToDrawStart);
		startMoveAndResult.setResultingFields(startFields);
		// get List of availableMoves
		availableMovesComputed = minMax.getAvailableMoves(startMoveAndResult, availableMovesTemp);

		// print out availableMovesComputed
		System.out.println("availableMovesComputed:");
		for (KalahariMoveAndResult Move : availableMovesComputed) {
			Move.printData();
		}

		int[][] tempPreviousMoves = { { 7 }, { 9, 7 }, { 9, 10 }, { 10, 7 }, { 10, 9, 7 }, { 10, 9, 10, 7 } };
		int[][] expectedFields = { { 0, 1, 0, 0, 1, 0, 0, 0, 1, 2, 1, 0 }, { 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 2, 1 }, { 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 2 }, { 0, 1, 0, 0, 1, 0, 0, 0, 1, 2, 0, 1 },
				{ 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2 }, { 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 3 } };

		for (i = 0; i <= (expectedFields.length - 1); i++) {
			KalahariMoveAndResult MoveAndResult = new KalahariMoveAndResult();
			MoveAndResult.setResultingFields(expectedFields[i]);
			MoveAndResult.setPlayerToDraw(playerToDrawExpected);
			// List of previousMoves is a bit more complicated
			for (int j = 0; j <= (tempPreviousMoves[i].length - 1); j++) {
				MoveAndResult.addToPreviousMove(tempPreviousMoves[i][j]);
			}
			availableMovesExpected.add(MoveAndResult);
		}
		// print out availableMovesExpected
		System.out.println();
		System.out.println("availableMovesExpected:");
		for (KalahariMoveAndResult Move : availableMovesExpected) {
			Move.printData();
		}

		// assertions
		// check if availableMovesComputed and availableMovesExpected are identical
		System.out.println();
		System.out.println("check if availableMovesComputed and availableMovesExpected are identical");
		for (i = 0; i <= (availableMovesComputed.size() - 1); i++) {
			assertTrue("availableMovesComputed(" + i + ") !=  availableMovesExpected(" + i + ")", availableMovesComputed.get(i).equals(availableMovesExpected.get(i)));
			System.out.println("Move(" + availableMovesComputed.get(i).getPreviousMove() + ") is ok!");
		}
	}

	@Test
	public final void testPerformSingleMove() {
		KalahariGameStateless testGame = new KalahariGameStateless();

		// Initialize test move
		KalahariFieldsMove newMove = new KalahariFieldsMove();
		newMove.setSelectedMove(10);
		newMove.setPlayerToDraw(0);
		int[] fieldsBefore = { 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 0 };
		newMove.setFields(fieldsBefore);

		// printout data
		System.out.println("\n\n*************** testPerformSingleMove ************************");
		System.out.println("newMove (before move is performed): ");
		newMove.printData();

		// Define expected result
		KalahariFieldsMove expectedResult = new KalahariFieldsMove();
		expectedResult.setSelectedMove(10);
		expectedResult.setPlayerToDraw(1);
		expectedResult.setMoveReturnCode(1);
		int[] fieldsExpected = { 4, 4, 3, 3, 3, 0, 3, 3, 3, 3, 0, 1 };
		expectedResult.setFields(fieldsExpected);

		// Get move Result
		KalahariFieldsMove moveResult = new KalahariFieldsMove();
		moveResult = testGame.performMove(newMove);

		// printout data
		System.out.println("\nexpectedResult: ");
		expectedResult.printData();
		System.out.println("\nmoveResult: ");
		moveResult.printData();

		// assertions
		assertEquals("Player to draw should be 1: ", moveResult.getPlayerToDraw(), 1);
		assertEquals("Move return code should be 1: ", moveResult.getMoveReturnCode(), 1);
		assertArrayEquals(moveResult.getFields(), expectedResult.getFields());
		assertTrue(moveResult.equals(expectedResult));
	}

	@Test
	public final void testGetRules() {
		KalahariGameStateless testGame = new KalahariGameStateless();

		String rules = testGame.getRules();
		// printout data
		System.out.println("\n\n*************** testGetRules ************************");
		System.out.println(rules);
		assertNotNull(rules);
	}

	@Test
	public final void testGetMoveReturnMessages() {
		KalahariGameStateless testGame = new KalahariGameStateless();
		int i = 0;
		String[] returnmassages = new String[4];

		// printout data
		System.out.println("\n\n*************** testGetMoveReturnMessages ************************");
		for (i = 0; i <= 3; i++) {
			returnmassages[i] = testGame.getMoveReturnMessage(i);
			System.out.println("Return message " + i + ": '" + returnmassages[i] + "'");
			assertNotNull(returnmassages[i]);
		}

	}

	@Test
	public final void testPlayExampleGameONE() {
		// fail("Not yet implemented"); // TODO
		int[] testSequenceOne = new int[] { 8, 10, 1, 4, 6, 3, 6, 4 };
		KalahariGameStateless testGame = new KalahariGameStateless();

		// Initialize test move
		KalahariFieldsMove newMove = new KalahariFieldsMove();
		KalahariFieldsMove moveResult = new KalahariFieldsMove();

		// run game with testSequenceOne and check if results are correct
		System.out.println("\n\n*************** testPlayExampleGameONE ************************");
		for (int i = 0; i < testSequenceOne.length; i++) {
			newMove.setSelectedMove(testSequenceOne[i]);
			newMove.setPlayerToDraw(moveResult.getPlayerToDraw());
			newMove.setMoveReturnCode(moveResult.getMoveReturnCode());
			newMove.printData();
			moveResult = testGame.performMove(newMove);
		}
		moveResult.printData();
		System.out.println("Score TestPlayer1 must be 21. Here the result: " + moveResult.getField(11));
		System.out.println("Score TestPlayer2 must be 9. Here the result: " + moveResult.getField(5));

		assertEquals("result must be 3", 3, moveResult.getMoveReturnCode());
		assertEquals("Score TestPlayer1 must be 21", 21, moveResult.getField(11));
		assertEquals("Score TestPlayer2 must be 9", 9, moveResult.getField(5));
	}

	@Test
	public final void testPlayExampleGameTWO() {
		int[] testSequenceTwo = new int[] { 8, 10, 1, 0, 6, 4, 8, 3, 10, 8, 4, 1, 6 };
		KalahariGameStateless testGame = new KalahariGameStateless();

		// Initialize test move
		KalahariFieldsMove newMove = new KalahariFieldsMove();
		KalahariFieldsMove moveResult = new KalahariFieldsMove();

		// run game with testSequenceOne and check if results are correct
		System.out.println("\n\n*************** testPlayExampleGameTWO ************************");
		for (int i = 0; i < testSequenceTwo.length; i++) {
			newMove.setSelectedMove(testSequenceTwo[i]);
			newMove.setPlayerToDraw(moveResult.getPlayerToDraw());
			newMove.setMoveReturnCode(moveResult.getMoveReturnCode());
			newMove.printData();
			moveResult = testGame.performMove(newMove);
		}
		moveResult.printData();
		System.out.println("Score TestPlayer1 must be 20. Here the result: " + moveResult.getField(11));
		System.out.println("Score TestPlayer2 must be 10. Here the result: " + moveResult.getField(5));

		assertEquals("result must be 3", 3, moveResult.getMoveReturnCode());
		assertEquals("Score TestPlayer1 must be 21", 20, moveResult.getField(11));
		assertEquals("Score TestPlayer2 must be 9", 10, moveResult.getField(5));
	}

	@Test
	public final void testPlayExampleGameTHREE() {
		int[] testSequenceThree = new int[] { 8, 8, 33, 9, 2, 2, 44, 3, 10, 4, 9, 3, 10, 7, 4, 2, 8, 3, 10, 9, 4 };
		KalahariGameStateless testGame = new KalahariGameStateless();

		// Initialize test move
		KalahariFieldsMove newMove = new KalahariFieldsMove();
		KalahariFieldsMove moveResult = new KalahariFieldsMove();

		// run game with testSequenceOne and check if results are correct
		System.out.println("\n\n*************** testPlayExampleGameTHREE ************************");
		for (int i = 0; i < testSequenceThree.length; i++) {
			newMove.setSelectedMove(testSequenceThree[i]);
			newMove.setPlayerToDraw(moveResult.getPlayerToDraw());
			newMove.setMoveReturnCode(moveResult.getMoveReturnCode());
			newMove.printData();
			moveResult = testGame.performMove(newMove);
		}
		moveResult.printData();
		System.out.println("Score TestPlayer1 must be 15. Here the result: " + moveResult.getField(11));
		System.out.println("Score TestPlayer2 must be 15. Here the result: " + moveResult.getField(5));

		assertEquals("result must be 3", 3, moveResult.getMoveReturnCode());
		assertEquals("Score TestPlayer1 must be 15", 15, moveResult.getField(11));
		assertEquals("Score TestPlayer2 must be 15", 15, moveResult.getField(5));
	}

}
