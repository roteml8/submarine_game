package submarinegame.game;

import java.util.Scanner;

public class Runner {
	
	public static void main(String[] args) {

		Player player = new Player("Rotem","missroteml@gmail.com","0525360337");
		Game game = new Game(player);
		game.play();
		game.writeGameToFile();
		game = new Game();
		System.out.println("Enter number of game to replay from 1 to "+Game.numRecordings);
		//Scanner sc = new Scanner(System.in);
		int num = Game.sc.nextInt();
		game.readGameFromFile(num);
		game.replayGame();
		Game.sc.close();
		
	}
}
