package submarinegame.game;

import java.io.Serializable;

public class Guess implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -788495876393392711L;
	protected int xGuess;
	protected int yGuess;
	protected int numOfGuess;
	
	public Guess(int xGuess, int yGuess, int numOfGuess) {
		super();
		this.xGuess = xGuess;
		this.yGuess = yGuess;
		this.numOfGuess = numOfGuess;
	}
	
	

}
