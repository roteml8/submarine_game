package submarinegame.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import submarinegame.exceptions.OutOfBoardException;
import submarinegame.exceptions.OutOfTargetsException;

public class Game  {
	
	protected final int NUM_GUESSES = 5;
	protected static String filePath = "files/record.dat";

	protected Board userBoard;
	protected Board logicBoard;
	protected Player player;
	protected int points;
	protected int guesses;
	protected int hits;
	protected int misses;
	protected Guess[] playerGuesses;
	
	
	public Game(Player player)
	{
		this.points = 1000;
		this.guesses = NUM_GUESSES;
		this.hits = 0;
		this.misses = 0;
		this.userBoard = new Board();
		this.logicBoard = new Board();
		this.userBoard.initializeBoard();
		this.logicBoard.initializeBoard();
		this.logicBoard.setSubsOnBoard();
		this.player = player;
		this.playerGuesses = new Guess[NUM_GUESSES];
	}
	
	public void play() {
		Scanner sc = new Scanner(System.in);
		int currentPoints, x=0, y=0;
		String input = "";
		boolean lastGuess = false,  win=false;
		printBoardAndScore();

		while (guesses > 0 && points > 0 ) {
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
					int guessIndex = NUM_GUESSES-guesses;
					playerGuesses[guessIndex] = new Guess(x,y,guessIndex);
				}
				catch (OutOfTargetsException e)
				{
					System.out.println(e.getMessage());
					System.out.println("You win!");
					win=true;
				}
				catch (NumberFormatException e)
				{
					System.out.println("You must enter integers only");;
				}
				catch (OutOfBoardException e)
				{
					System.out.println(e.getMessage());
				}
			}
			if (!isMarked(x,y))
			{
				if (checkGuess(x, y)) {
					userBoard.markHit(x, y, logicBoard.subs);
					//logicBoard.markHit(x, y, logicBoard.subs);
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
					misses++;
				}
			}
			else
				misses++;
			guesses--;
			printBoardAndScore();
			System.out.printf("You have %d guesses left!\n", guesses);
		}
		if (!win)
			System.out.println("you lost!");
		sc.close();
	}

	private void printBoardAndScore() {
		userBoard.printBoard();
		System.out.println(
				"Your score is: " + points + ", number of hits is: " + hits + ", number of misses: " + misses);
		//System.out.println("Enter X and Y coordinate, Or press q to quit.");
		System.out.printf("X coordinates 1-%d, Y coordinates 1-%d\n", logicBoard.ROWS-2, logicBoard.COLS-2);
	}

	private void printLogicBoard()
	{
		logicBoard.printBoard();
	}

	protected boolean checkGuess(int x, int y) {
		if (logicBoard.getCellValue(x,y) == 'S' && userBoard.getCellValue(x, y) != 'H') // new ship is detected 
			return true;

		return false;
	}
	
	protected boolean isMarked(int x, int y)
	{
		if (userBoard.getCellValue(x, y) != ' ')
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
	
	public void writeGameToFile()
	{
		try (FileOutputStream file = new FileOutputStream(filePath);
				ObjectOutputStream output = new ObjectOutputStream(file))
		{
			output.writeObject(this.logicBoard);
			userBoard.clearBoard();
			output.writeObject(this.userBoard);
			for (int i=0; i<NUM_GUESSES; i++)
			{
				output.writeObject(playerGuesses[i]);
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);	
		}
	}
	
	public void readGameFromFile()
	{
		try (FileInputStream file = new FileInputStream(filePath);
				ObjectInputStream input = new ObjectInputStream(file))
		{
				this.logicBoard = (Board) input.readObject();
				this.userBoard = (Board) input.readObject();
				for (int i=0; i<NUM_GUESSES; i++)
				{
					Object obj = input.readObject();
					if (obj == null)
						break;
					Guess guess = (Guess)obj;
					this.playerGuesses[i] = guess;
				}
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
	}
	
	public void replayGame()
	{
		int currentPoints;
		boolean lastGuess=false;
		printBoardAndScore();
		for (int i=0; i<NUM_GUESSES; i++)
		{
	
			Guess current = playerGuesses[i];
			if (current == null)
				break;
			if (checkGuess(current.xGuess, current.yGuess)) {
				userBoard.markHit(current.xGuess, current.yGuess, logicBoard.subs);
				logicBoard.markHit(current.xGuess, current.yGuess, logicBoard.subs);
				currentPoints = 200;
				if (lastGuess)
					currentPoints = 1000;
				points += currentPoints;
				lastGuess = true;
				hits++;
			} else {
				userBoard.markMiss(current.xGuess, current.yGuess);
				lastGuess = false;
				points -= 10;
				misses++;
			}
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			guesses--;
			printBoardAndScore();
		}
		if (hits == logicBoard.NUM_SUBMARINES)
			System.out.println("You won!");
		else
			System.out.println("You lost!");
	}
}
