package masarik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Simple game to be played in console.
 * 
 * @author michal masarik
 *
 */
public class TicTacToeGame {

	private static char[][] gameboard = { 
			{ ' ', '|', ' ', '|', ' ' }, 
			{ '-', '+', '-', '+', '-' },
			{ ' ', '|', ' ', '|', ' ' }, 
			{ '-', '+', '-', '+', '-' }, 
			{ ' ', '|', ' ', '|', ' ' } };

	public enum Party {
		PLAYER, AI
	}

	private static List<Integer> playerPositions = new ArrayList<Integer>();
	private static List<Integer> aiPositions = new ArrayList<Integer>();;

	public static void main(String[] args) {

		String messagePrefix = "";
		while (true) {
			printGameboard();
			System.out.println();
			System.out.print(messagePrefix + "Choose tile to place X (1-9): ");
			Scanner scan = new Scanner(System.in);
			int pos = 0;
			try {
				pos = scan.nextInt();
			} catch (InputMismatchException e) {
			}
			if (pos >= 1 && pos <= 9) {
				if (!playerPositions.contains(pos) && !aiPositions.contains(pos)) {
					placePiece(pos, Party.PLAYER);

					if (isWinConditionSatisfied(Party.PLAYER)) {
						printGameboard();
						System.out.println("\nCongratulation! You won.");
						break;
					}

					int aiPos = computeBestAiMove();
					if (aiPos == 0) {
						printGameboard();
						System.out.println("\nEnd of game. It's a draw.");
						break;

					} else {
						placePiece(aiPos, Party.AI);

						if (isWinConditionSatisfied(Party.AI)) {
							printGameboard();
							System.out.println("\nGame Over! You lost.");
							break;
						}
						messagePrefix = "";
					}

				} else {
					messagePrefix = "This tile is already taken. ";
				}
			} else {
				messagePrefix = "Incorrect input. ";
			}
		}

	}

	private static boolean isWinConditionSatisfied(Party party) {

		List<Integer> upRow = new ArrayList<>(Arrays.asList(1, 2, 3));
		List<Integer> midRow = new ArrayList<>(Arrays.asList(4, 5, 6));
		List<Integer> bottomRow = new ArrayList<>(Arrays.asList(7, 8, 9));
		List<Integer> leftColumn = new ArrayList<>(Arrays.asList(1, 4, 7));
		List<Integer> midColumn = new ArrayList<>(Arrays.asList(2, 5, 8));
		List<Integer> rightColumn = new ArrayList<>(Arrays.asList(3, 6, 9));
		List<Integer> LeftUpRightBotDiagonal = new ArrayList<>(Arrays.asList(1, 5, 9));
		List<Integer> LeftBotRightUpDiagonal = new ArrayList<>(Arrays.asList(3, 5, 7));
		List<List<Integer>> validWinConditions = new ArrayList<List<Integer>>();

		validWinConditions.add(upRow);
		validWinConditions.add(midRow);
		validWinConditions.add(bottomRow);
		validWinConditions.add(leftColumn);
		validWinConditions.add(midColumn);
		validWinConditions.add(rightColumn);
		validWinConditions.add(LeftUpRightBotDiagonal);
		validWinConditions.add(LeftBotRightUpDiagonal);

		switch (party) {
		case PLAYER:
			for (List<Integer> winCondition : validWinConditions) {
				if (playerPositions.containsAll(winCondition)) {
					return true;
				}
			}

		case AI:
			for (List<Integer> winCondition : validWinConditions) {
				if (aiPositions.containsAll(winCondition)) {
					return true;
				}
			}
		}
		return false;
	}

	private static int computeBestAiMove() {
		while (true) {
			int pos = new Random().nextInt(1, 9 + 1);
			if (!playerPositions.contains(pos) && !aiPositions.contains(pos)) {
				return pos;
			} else if (playerPositions.size() + aiPositions.size() == 9) {
				return 0;
			}
		}
	}

	private static void placePiece(int pos, Party party) {
		char token = ' ';
		if (party == Party.PLAYER) {
			playerPositions.add(pos);
			token = 'X';
		} else if (party == Party.AI) {
			token = 'O';
			aiPositions.add(pos);
		}

		switch (pos) {
		case 1: {
			gameboard[0][0] = token;
			break;
		}
		case 2: {
			gameboard[0][2] = token;
			break;
		}
		case 3: {
			gameboard[0][4] = token;
			break;
		}
		case 4: {
			gameboard[2][0] = token;
			break;
		}
		case 5: {
			gameboard[2][2] = token;
			break;
		}
		case 6: {
			gameboard[2][4] = token;
			break;
		}
		case 7: {
			gameboard[4][0] = token;
			break;
		}
		case 8: {
			gameboard[4][2] = token;
			break;
		}
		case 9: {
			gameboard[4][4] = token;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + pos);
		}
	}

	private static void printGameboard() {
		System.out.println();
		for (char[] row : gameboard) {
			for (char element : row) {
				System.out.print(element);
			}
			System.out.println();
		}
	}
}
