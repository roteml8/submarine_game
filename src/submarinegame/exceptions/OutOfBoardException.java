package submarinegame.exceptions;

public class OutOfBoardException extends Exception{
		
	public OutOfBoardException()
	{
		super("you entered coordinates out of the board's size");
	}

}
