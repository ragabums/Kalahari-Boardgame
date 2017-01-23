package kalahariMinMax;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kalahariBackendStateless.KalahariFieldsMove;
import kalahariBackendStateless.KalahariGameStateless;

/**
 * MinMax Algorithm for Kalahari Computer Game
 * 
 * @author Theo Wilhelm, Jan. 2017
 * @version from 06. January 2017
 * 
 */
public class KalahariMinMax {
	final static Logger log = LogManager.getLogger(KalahariMinMax.class);

	KalahariGameStateless newGame = new KalahariGameStateless();
	KalahariFieldsMove moveResult = new KalahariFieldsMove();
	KalahariMoveAndResult recommendedMove = new KalahariMoveAndResult();
	int movesEvaluated = 0;
	// maxDepth will contain the depth defined upon initial call (= maximum depth)
	int maxDepth = 0;

	/**
	 * returns the number of evaluated possible moves (game states).
	 * 
	 * @return Number of evaluated moves (game states).
	 */
	public int getNumberOfEvaluatedMoves() {
		return this.movesEvaluated;
	}

	/**
	 * clears the number of evaluated moves (sets it to zero)
	 */
	public void clearNumberOfEvaluatedMoves() {
		this.movesEvaluated = 0;
	}

	/**
	 * Given a dedicated situation this method returns a recommended move that was computed with a MinMax Algorithm
	 * developed for zero-sum games (see e.g. https://en.wikipedia.org/wiki/Minimax).
	 * 
	 * @param initialSituation Initial situation to calculate optimized computer move for (type KalahariFieldsMove)
	 * 
	 * @param computerPlayer index of computerPlayer (0 or 1)
	 * 
	 * @param playerToDraw index of payer that is to draw (0 or 1)
	 * 
	 * @param depth number of moves taken into account in the evaluation of the best computer move (type int). Example:
	 * if depth = 3 human's draws, subsequent computer's draws and again subsequent human's draws are taken into account
	 * in the MinMax Algorithm.
	 * 
	 * @param alpha alpha value of alpha-beta pruning for min max algorithm.
	 * @param beta beta value of alpha-beta pruning for min max algorithm.
	 * 
	 * @return recommendedMove: Recommended Move(s) (all until other is to draw) (type KalahariMoveAndResult)
	 * 
	 * See also <a
	 * href="https://de.wikipedia.org/wiki/Alpha-Beta-Suche"target="_top">https://de.wikipedia.org/wiki/Alpha-Beta-Suche
	 * </a> or <a href="https://en.wikipedia.org/wiki/Alpha-beta_pruning"
	 * target="_top">https://en.wikipedia.org/wiki/Alpha-beta_pruning</a>
	 */
	public KalahariMoveAndResult getRecommendedMove(KalahariFieldsMove initialSituation, int computerPlayer, int playerToDraw, int depth, int alpha, int beta) {
		KalahariMoveAndResult startMoveAndResult = new KalahariMoveAndResult();
		KalahariMoveAndResult currentMoveAndResult = new KalahariMoveAndResult();
		KalahariFieldsMove currentFieldsMove = new KalahariFieldsMove();

		List<KalahariMoveAndResult> availableMovesTemp = new ArrayList<>();
		List<KalahariMoveAndResult> availableMoves = new ArrayList<>();

		// sBuffer below is used to format debug output nicely
		StringBuffer sBuffer = new StringBuffer("");
		maxDepth = Math.max(depth, maxDepth);
		for (int i = 1; i <= (maxDepth - depth) + 1; i++) {
			sBuffer.append("\t");
		}
		sBuffer.append("|");
		log.debug(sBuffer + "-----------------------------------------------------------------------------------------------------------------");
		log.debug(sBuffer + "depth: " + depth + ";     computerPlayer: " + computerPlayer + ";\t playerToDraw: " + playerToDraw + ";\t alpha=" + alpha + ";\t beta=" + beta);
		log.debug(sBuffer + "" + initialSituation.getFieldsAsStringBuffer() + ";\t playerToDraw: " + initialSituation.getPlayerToDraw());
		log.debug(sBuffer + "-----------------------------------------------------------------------------------------------------------------");

		int maxValue = alpha;
		int minValue = beta;
		int currentScore = Integer.MIN_VALUE;

		// setup startMove
		startMoveAndResult.setPlayerToDraw(initialSituation.getPlayerToDraw());
		startMoveAndResult.setResultingFields(initialSituation.getFields());

		// get possible moves
		availableMoves = getAvailableMoves(startMoveAndResult, availableMovesTemp);
		log.debug(sBuffer + "Available (possible) moves:");
		logAvailableMoves(availableMoves, sBuffer);

		// Leaf node reached?
		if (depth == 0 || availableMoves.isEmpty()) {
			log.debug(sBuffer + "!!!! Leaf node reached:    depth=" + depth + "; availableMoves.isEmpty()=" + availableMoves.isEmpty());
			currentScore = getComputerScore(startMoveAndResult, computerPlayer);
			startMoveAndResult.setScore(currentScore);
			return startMoveAndResult;
		}

		for (KalahariMoveAndResult Move : availableMoves) {
			log.debug("");
			log.debug(sBuffer + "Testing Move" + Move.getPreviousMove() + "(previous potential game state (PGS))");
			currentFieldsMove.setFields(Move.getResultingFields());
			currentFieldsMove.setPlayerToDraw(1 - playerToDraw);

			// maximizing draw (computer's draw)!
			if (playerToDraw == computerPlayer) {
				currentMoveAndResult = getRecommendedMove(currentFieldsMove, computerPlayer, currentFieldsMove.getPlayerToDraw(), depth - 1, maxValue, beta);
				currentScore = currentMoveAndResult.getScore();
				log.debug(sBuffer + "visited potential game state (PGS): " + currentMoveAndResult.getDataAsStringBuffer());
				log.debug(sBuffer + "---> currentScore=" + currentScore + ";  alpha=" + maxValue + ";  beta=" + beta);
				if (currentScore > maxValue) {
					log.debug(sBuffer + "---> Maximizing move (cumputer's turn AND currentScore > alpha)");
					maxValue = currentScore;
					log.debug(sBuffer + "---> alpha from maximizing move: " + maxValue + " (= currentScore)");
					if (maxValue >= beta) {
						log.debug(sBuffer + "!! Break because alpha > beta;\t alpha=" + maxValue + ";\t beta=" + beta);
						break;
					}
					if (depth == maxDepth) {
						recommendedMove = Move;
						log.debug(sBuffer + "---> recommendedMove: " + recommendedMove.getPreviousMove());
					}
				}
			}
			// minimizing draw (human's draw)!
			if (playerToDraw != computerPlayer) {
				currentMoveAndResult = getRecommendedMove(currentFieldsMove, computerPlayer, currentFieldsMove.getPlayerToDraw(), depth - 1, alpha, minValue);
				currentScore = currentMoveAndResult.getScore();
				/// log.debug(sBuffer + "visited potential game state (PGS): " +
				/// currentMoveAndResult.getDataAsStringBuffer());
				log.debug(sBuffer + "---> currentScore=" + currentScore + ";  alpha=" + alpha + ";  beta=" + minValue);
				if (currentScore < minValue) {
					log.debug(sBuffer + "---> Minimizing move (human's turn AND currentScore < beta)");
					minValue = currentScore;
					log.debug(sBuffer + "---> beta from minimizing move: " + minValue + " (= currentScore)");
					if (minValue <= alpha) {
						log.debug(sBuffer + "!! Break because alpha > beta;\t alpha=" + alpha + ";\t beta=" + minValue);
						break;
					}
				}
			}
			log.debug(sBuffer + "Move " + Move.getPreviousMove() + ": currentScore=" + currentScore + ";  alpha=" + maxValue + ";  beta=" + minValue);

		}
		movesEvaluated += availableMoves.size();
		log.debug("");
		log.debug(sBuffer + "-----------------------------");
		log.debug(sBuffer + "RecommendedMove for depth=" + depth + ": " + recommendedMove.getPreviousMove());
		log.debug(sBuffer + "currentScore=" + currentScore + ";\t alpha=" + minValue + ";\t beta=" + maxValue);
		log.debug(sBuffer + "Moves evailuated: " + movesEvaluated);
		log.debug(sBuffer + "-----------------------------");
		log.debug("");
		log.debug("");
		if (playerToDraw == computerPlayer) {
			recommendedMove.setScore(maxValue);
		} else {
			recommendedMove.setScore(minValue);
		}
		return recommendedMove;
	}

	/**
	 * This is the evaluation function used by the MinMax Algorithm!
	 * Returns the score of the computer player.
	 * 
	 * @param situation Situation (=Game State) to calculate the score for (type KalahariMoveAndResult).
	 * 
	 * @param computerPlayer index of the computer player (0 if computer is player 1 and 1 if computer is player 2)
	 * 
	 * @return score: Difference between computer's score and human player's score.
	 */
	public int getComputerScore(KalahariMoveAndResult situation, int computerPlayer) {
		int computersScore = -99;

		if (computerPlayer == 0) {
			computersScore = situation.getResultingField(11) - situation.getResultingField(5);
		} else {
			computersScore = situation.getResultingField(5) - situation.getResultingField(11);
		}

		/// Here a very simple heuristic is used. This is the place where improvements of the computer player should be done first!
		/// Possible improvement ideas:
		/// * Adding more heuristics and weighting them according to importance
		///   * Heuristic taking into account how close computer player is to winning (score >=15)
		///   * Heuristic taking into account how many fields are left for computer player
		///   * ....
	
		return computersScore;
	}

	/**
	 * Given a dedicated situation (field, player to draw) returns a List possible moves. The List contains the moves as
	 * well as the resulting fields.
	 * 
	 * @param startMove Move Object to calculate further moves of same player from (Type KalahariMoveAndResult)
	 * 
	 * @param availableMoves List of available Moves (Type List of KalahariMoveAndResult objects)
	 * 
	 * @return List of available Moves including Resulting fields (type KalahariFieldsMove)
	 * 
	 */
	public List<KalahariMoveAndResult> getAvailableMoves(KalahariMoveAndResult startMove, List<KalahariMoveAndResult> availableMoves) {
		KalahariFieldsMove testMove = new KalahariFieldsMove();
		int startField, endField;

		// Find fields of playerToDraw that are not empty
		if (startMove.getPlayerToDraw() == 0) {
			startField = 6;
			endField = 10;
		} else {
			startField = 0;
			endField = 4;
		}

		for (int i = startField; i <= endField; i++) {
			if (startMove.getResultingField(i) != 0) {
				testMove.setPlayerToDraw(startMove.getPlayerToDraw());
				testMove.setFields(startMove.getResultingFields());
				testMove.setSelectedMove(i);
				moveResult = newGame.performMove(testMove);
				//
				// Create new MoveAndResult object
				KalahariMoveAndResult MoveAndResult = new KalahariMoveAndResult();
				MoveAndResult.setResultingFields(moveResult.getFields());
				//
				List<Integer> moveList = new ArrayList<>();
				moveList.addAll(0, startMove.getPreviousMove());
				MoveAndResult.setPreviousMove(moveList);
				MoveAndResult.addToPreviousMove(i);
				MoveAndResult.setPlayerToDraw(moveResult.getPlayerToDraw());

				//
				// If same player has to draw again:
				if (moveResult.getMoveReturnCode() == 2) {
					// get List of availableMoves
					availableMoves = getAvailableMoves(MoveAndResult, availableMoves);
				} else if (moveResult.getMoveReturnCode() == 0) { // Error
					log.error("!! moveResult.getMoveReturnCode() == 0 !!");
					break;
				} else if (moveResult.getMoveReturnCode() == 3) { // Game ended
					//
					// Add MoveAndResult object to list of availableMoves
					MoveAndResult.setPlayerToDraw(-99);
					availableMoves.add(MoveAndResult);
				} else {
					// Add MoveAndResult object to list of availableMoves
					availableMoves.add(MoveAndResult);
				}
			}
		}
		return availableMoves;
	}

	/**
	 * prints available moves to logger
	 * 
	 * @param List<KalahariMoveAndResult> that should be logged
	 * 
	 */
	private void logAvailableMoves(List<KalahariMoveAndResult> movesToPrint, StringBuffer indent) {

		// print available Moves
		for (KalahariMoveAndResult Move : movesToPrint) {
			log.debug(indent + "" + Move.getDataAsStringBuffer());
		}
	}

}