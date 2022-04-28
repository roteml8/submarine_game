package submarinegame.game;

import java.util.Scanner;

import submarinegame.exceptions.OutOfBoardException;
import submarinegame.exceptions.OutOfTargetsException;

public class Game {
	
	protected final int NUM_GUESSES = 100;

	protected Board userBoard;
	protected Board logicBoard;
	
	protected int points;
	protected int guesses;
	protected int hits;
	
	public Game()
	{
		this.points = 1000;
		this.guesses = NUM_GUESSES;
		this.hits = 0;
		this.userBoard = new Board();
		this.logicBoard = new Board();
		this.userBoard.initializeBoard();
		this.logicBoard.initializeBoard();
		this.logicBoard.setSubsOnBoard();
	}

	public void play() {
		Scanner sc = new Scanner(System.in);
		int currentPoints, x=0, y=0;
		String input = "";
		boolean lastGuess = false;

		while (guesses > 0 && points > 0 ) {
			printBoardAndScore();
			//printLogicBoard();
			boolean validInput = false;
			while (!validInput)
			{
				try {
					if (hits == logicBoard.NUM_SUBMARINES)
						throw new OutOfTargetsException();
					x = getCoordinate('X', sc);
					y = getCoordinate('Y', sc);
					if (!userBoard.checkValidInput(x, y))
						throw new OutOfBoardException();
					validInput = true;
				}
				catch (OutOfTargetsException e)
				{
					System.out.println("You win!");
					return;
				}
				catch (NumberFormatException e)
				{
					continue;
				}
				catch (OutOfBoardException e)
				{
					continue;
				}
			}
			
			if (checkGuess(x, y)) {
				userBoard.markHit(x, y, logicBoard.subs);
				logicBoard.markHit(x, y, logicBoard.subs);
				currentPoints = 200;
				if (lastGuess)
					currentPoints = 1000;
				points += currentPoints;
				lastGuess = true;
				hits++;
			} else {
				userBoard.markMiss(x, y);
				lastGuess = false;
				points -= 10;
			}
			guesses--;
			System.out.printf("You have %d guesses left!\n", guesses);
		}
		System.out.println("you lost!");
		sc.close();
	}

	private void printBoardAndScore() {
		userBoard.printBoard();
		System.out.println(
				"Your score is: " + points + ", number of hits is: " + hits + ", number of misses: " + getMisses());
		//System.out.println("Enter X and Y coordinate, Or press q to quit.");
		System.out.println("X coordinates 1-10, Y coordinates 1-20");
	}

	private void printLogicBoard()
	{
		logicBoard.printBoard();
	}
	public int getMisses() {
		return NUM_GUESSES - guesses - hits;
	}

	protected boolean checkGuess(int x, int y) {
		if (logicBoard.getCellValue(x,y) == 'S' && userBoard.getCellValue(x, y) != 'H')
			return true;

		return false;
	}

	protected int getCoordinate(char coordinate, Scanner sc) {
		String input;
		System.out.println("Enter " + coordinate + " coordinate");
		input = sc.next();
//		if (input.equals("q"))
//			return 0;

		return Integer.parseInt(input);
	}
}
