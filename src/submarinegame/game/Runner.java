package submarinegame.game;

public class Runner {
	
	public static void main(String[] args) {

		Player player = new Player("Rotem","missroteml@gmail.com","0525360337");
		Game game = new Game(player);
		game.play();
//		game.writeGameToFile();
//		game = new Game(player);
//		game.readGameFromFile();
//		game.replayGame();
		
	}
}
