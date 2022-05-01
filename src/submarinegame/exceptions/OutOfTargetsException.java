package submarinegame.exceptions;

public class OutOfTargetsException extends Exception{

	public OutOfTargetsException()
	{
		super("You are out of targest - all submarines were detected");
	}
}
