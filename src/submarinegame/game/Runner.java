package submarinegame.game;


public class Runner {
	
	public static void replay()
	{
		Game game = new Game();
		if (Game.numRecordings > 0)
		{
			System.out.println("Enter number of game to replay from 1 to "+Game.numRecordings);
			int num = Game.sc.nextInt();
			if (num > Game.numRecordings || num < 1)
			{
				System.out.println("Invalid number!");
				return;
			}
			game.readGameFromFile(num);
			game.replayGame();
		}

	}
	
	public static void play()
	{
		System.out.println("Enter name");
		String name = Game.sc.next();
		System.out.println("Enter email");
		String email = Game.sc.next();
		System.out.println("Enter phone number");
		String phone = Game.sc.next();
		Player player = new Player(name, email, phone);
		Game game = new Game(player);
		game.play();
		game.writeGameToFile();

	}
	
	public static int getChoice()
	{
		System.out.println("\nTo play enter 1");
		System.out.println("To replay game enter 2");
		System.out.println("To exit enter -1");
		int choice = Game.sc.nextInt();
		return choice;
	}
	
	public static void main(String[] args) {

		int choice = 0;
		while (choice != -1)
		{
			choice = getChoice();
			switch (choice)
			{
			case 1:
				play();
				break;
			case 2:
				replay();
				break;
			default:
				break;
			}
		}
		Game.sc.close();
	}
	
	
}
