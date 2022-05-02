package submarinegame.game;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

public class Board implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1237049972419253868L;
	protected final int ROWS = 12;
	protected final int COLS = 12;
	protected final int NUM_SUBMARINES = 10;
	protected final char frameChar = '#';

	protected char[][] matrix;
	protected Submarine[] subs;

	
	public Board()
	{
		this.matrix = new char[ROWS][COLS];
		this.subs = new Submarine[NUM_SUBMARINES];
		
	}
	
	public void clearBoard()
	{
		for (int i=1; i<ROWS-1; i++)
		{
			for (int j=1; j<COLS-1; j++)
				matrix[i][j] = ' ';
		}
	}
	
	public void initializeBoard() {
		// insert frame to the board
		for (int i = 0; i < COLS; i++) {
			matrix[0][i] = frameChar;
			matrix[ROWS - 1][i] = frameChar;
		}
		
		for(int i=0; i < ROWS; i++) {
			matrix[i][0] = frameChar;
			matrix[i][COLS - 1] = frameChar;
		}
		
		for (int i = 1; i < ROWS - 1; i++) {
			for (int j = 1; j < COLS - 1; j++) {
				matrix[i][j] = ' ';
			}
		}
	}
	
	public void printBoard() {
		System.out.println("-Submarine Board Game-");
		System.out.print("   ");
		for (int i=1; i<=ROWS-3; i++)
			System.out.print(i+"  ");
		System.out.print(ROWS-2+" ");
		for (int i=ROWS-1; i<=COLS-2; i++)
			System.out.print(i+" ");
		System.out.println();
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.print(matrix[i][j]+"  ");
			}
			if (i > 0 && i < ROWS-1)
				System.out.print(i);
			System.out.println();
		}

	}

	
	private void initSubsArray()
	{
		for (int i = 0; i < subs.length; i++) {
			subs[i] = new Submarine();
		}
	}

	protected void setSubsOnBoard() {
		int i = 0;
		Point randPoint;

		initSubsArray();

		do {
			randPoint = getRandomPointOnBoard();

			if (checkValidLocation(randPoint, i)) {
				insertSubToBoard(randPoint, i);
				i++;
			}

		} while (i < subs.length);
	}
	
	private Point getRandomPointOnBoard() {
		Point p = new Point();
		Random rad = new Random();
		p.x = rad.nextInt(10) + 1;
		p.y = rad.nextInt(20) + 1;
		return p;
	}

	private boolean checkValidLocation(Point randomPoint, int subIndex) {
		int xtmp, ytmp;
		for (int i = 0; i < subs[subIndex].getSize(); i++) {
			xtmp = randomPoint.x + subs[subIndex].submarineCoords[i].x;
			ytmp = randomPoint.y + subs[subIndex].submarineCoords[i].y;
			if (xtmp >= ROWS - 1 || ytmp >= COLS - 1 || xtmp < 1 || ytmp < 1)
				return false;
			if (isSubAround(new Point(xtmp, ytmp)))
				return false;
		}

		return true;
	}

	private boolean isSubAround(Point point) {
//		this.printBoard();
		for (int i = -1; i <= 1; i++)
			if (matrix[point.x - 1][point.y + i] == 'S' || matrix[point.x + i][point.y - 1] == 'S'
					|| matrix[point.x + 1][point.y + i] == 'S' || matrix[point.x + i][point.y + 1] == 'S')
				return true;

		return false;
	}

	private void insertSubToBoard(Point p, int subIndex) {
		int xtmp, ytmp;
		for (int i = 0; i < subs[subIndex].getSize(); i++) {
			xtmp = p.x + subs[subIndex].submarineCoords[i].x;
			ytmp = p.y + subs[subIndex].submarineCoords[i].y;
			subs[subIndex].submarineCoords[i].x = xtmp;
			subs[subIndex].submarineCoords[i].y = ytmp;
			matrix[xtmp][ytmp] = 'S';
		}
	}


	protected char getCellValue(int x, int y) {
		return matrix[x][y];
	}
	
	protected boolean checkValidInput(int x, int y) {            
   		if (x > ROWS-2 || x < 1 || y > COLS-2 || y < 1) {
    			return false;                                             
    		}                                                          
                                                                 
    		return true;                                               
    	}                                                           
                                                                 
    	protected void markHit(int x, int y, Submarine[] subs) {  
    		Submarine detected=null, current;
    		for (int i=0; i<NUM_SUBMARINES && detected == null; i++)
    		{
    			current = subs[i];
    			Point[] subPoints = current.submarineCoords;
    			for (Point p: subPoints)
    			{
    				if (p.x == x && p.y == y)
    				{
    					detected = current;
    				}		
    			}
    		}
    		Point[] detectedPoints = detected.submarineCoords;
    		for (Point p: detectedPoints)
    			matrix[p.x][p.y] = 'H';                                         
    	}                                                           
    	                                                            
    	protected void markMiss(int x, int y) {                     
    		matrix[x][y] = 'm';                                         
    	} 
}