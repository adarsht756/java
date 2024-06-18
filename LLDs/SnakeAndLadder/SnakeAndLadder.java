import java.util.Scanner;

public class SnakeAndLadder {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        try {
            int boardSize, numberOfSnakes, numberOfLadders, numberOfPlayers;
            System.out.println("Enter board size");
            boardSize = sc.nextInt();
            System.out.println("Enter number of Snakes");
            numberOfSnakes = sc.nextInt();
            System.out.println("Enter number of Ladders");
            numberOfLadders = sc.nextInt();
            System.out.println("Enter number of Players");
            numberOfPlayers = sc.nextInt();

            Game game = new Game(boardSize, numberOfSnakes, numberOfLadders);
            for (int i = 0; i < numberOfPlayers; ++i) {
                System.out.println("Enter player name");
                String pName = sc.next();
                Player player = new Player(pName);
                game.addPlayer(player);
            }
            game.playGame();

        } finally {
            sc.close();
        }
    }
}