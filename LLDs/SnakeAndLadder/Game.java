import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Game {
    int numberOfSnakes;
    int numberOfLadders;

    private Queue<Player> players;
    private List<Snake> snakes;
    private List<Ladder> ladders;

    private Board board;
    private Dice dice;

    public Game(int boardSize, int numOfSnakes, int numOfLadders) {
        this.numberOfSnakes = numOfSnakes;
        this.numberOfLadders = numOfLadders;
        this.players = new ArrayDeque<>();
        this.snakes = new ArrayList<>(numOfSnakes);
        this.ladders = new ArrayList<>(numOfLadders);
        board = new Board(boardSize);
        dice = new Dice(1, 6);
        initBoard();
    }

    public void initBoard() {
        Random random = new Random();
        Set<String> slSet = new HashSet<>();
        for (int i = 0; i < numberOfSnakes; ++i) {
            while (true) {
                int snakeStart = random.nextInt(board.start, board.size);
                int snakeEnd = random.nextInt(board.start, board.size);
                if (snakeStart > snakeEnd)
                    continue;
                String startEndPair = String.valueOf(snakeStart) + snakeEnd;
                if (!slSet.contains(startEndPair)) {
                    Snake snake = new Snake(snakeStart, snakeEnd);
                    snakes.add(snake);
                    slSet.add(startEndPair);
                    break;
                }
            }
        }

        for (int i = 0; i < numberOfLadders; ++i) {
            while (true) {
                int ladderStart = random.nextInt(board.start, board.size);
                int ladderEnd = random.nextInt(board.start, board.size);
                if (ladderStart > ladderEnd)
                    break;
                String startEndPair = String.valueOf(ladderStart) + ladderEnd;
                if (!slSet.contains(startEndPair)) {
                    Ladder ladder = new Ladder(ladderStart, ladderEnd);
                    ladders.add(ladder);
                }
            }
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void playGame() {
        while (true) {
            Player player = players.poll();
            int val = dice.roll();
            int newPosition = player.position + val;
            if (newPosition > board.end) {
                players.offer(player);
            } else {
                player.position = getNewPosition(newPosition);
                if (player.position == board.end) {
                    player.won = true;
                    System.out.println("Player " + player.name + " won game.");
                } else {
                    System.out.println("Setting " + player.name + "'s new position to " + player.position);
                    players.offer(player);
                }
            }
            if (players.size() < 2)
                break;
        }
    }

    public int getNewPosition(int position) {
        for (Snake snake : snakes) {
            if (position == snake.head) {
                System.out.println("Snake bit");
                return snake.tail;
            }
        }

        for (Ladder ladder : ladders) {
            if (position == ladder.end) {
                System.out.println("Climbed ladder");
                return ladder.start;
            }
        }
        return position;
    }
}