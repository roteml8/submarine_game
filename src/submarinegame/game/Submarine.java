package submarinegame.game;

import java.awt.Point;
import java.util.Random;

public class Submarine {
	
	protected int size;
	protected int xLength;
	protected int yLength;
	protected Point[] submarineCoords;
	
	public Submarine() {
		size = getRandomSize();
		makeSub();
	}

	private Point[] makeSub() {
		int i = 1, step, yPreviusStep=0, xPreviusStep=0;

		initializeSub();

		while (i != size) {
			step = getRandomDirection();
			
			if(step != 0 && step != yPreviusStep) {
					yStepDirection(step, i);
					i++;
					yPreviusStep = step*-1;
					xPreviusStep=0;
				} else {
					
				step = getRandomDirection();
				if (step != 0 && step != xPreviusStep) {
					xStepDirection(step, i);
					i++;
					xPreviusStep = step*-1;
					yPreviusStep=0;
				}
			}
		}

		return submarineCoords;
	}
	
	/**
	 * this method steps at direction according step value
	 * @param step value is 1 or -1 
	 * @param i the index of the point in the points array
	 */
	private void yStepDirection(int step, int i) {
		submarineCoords[i].x += submarineCoords[i-1].x;
		submarineCoords[i].y += step + submarineCoords[i-1].y;
		yLength++;
	}
	
	/**
	 * this method steps at direction according step value
	 * @param step value is 1 or -1 
	 * @param i the index of the point in the points array
	 */
	private void xStepDirection(int step, int i) {
		submarineCoords[i].x += step + submarineCoords[i-1].x;
		submarineCoords[i].y += submarineCoords[i-1].y;
		xLength++;
	}
	
	/**
	 * initialize the submarine points array to 0,0
	 */
	private void initializeSub() {
		submarineCoords = new Point[size];
		for (int i = 0; i < submarineCoords.length; i++) {
			submarineCoords[i] = new Point();
		}
	}

	/**
	 * creates the length of sub
	 * 
	 * @return the size between 1 - 4
	 */
	public int getRandomSize() {
		Random rand = new Random();
		int subSize = rand.nextInt(4) + 1;
		return subSize;
	}

	/**
	 * 
	 * @return -1 / 0 / 1 randomly
	 */
	public int getRandomDirection() {
		Random rand = new Random();
		int subSize = rand.nextInt(3) - 1;
		return subSize;
	}

	public int subMaxLength() {
		if (xLength > yLength)
			return xLength;
		else
			return yLength;
	}

	public int getxLength() {
		return xLength;
	}

	public int getyLength() {
		return yLength;
	}

	public int getSize() {
		return size;
	}

}
