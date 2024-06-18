#include <bits/stdc++.h>
#include <iostream>
#include <stdio.h>
#include <deque>
#include <unordered_set>

using namespace std;

class Utils {
    public: int getRandomNumInRange(int low, int high) {
        float random = ((float) rand()) / (float) RAND_MAX;
        float diff = high - low;
        float r = random * diff;
        return low + r;
    }
};

class Player {
public:
    string name;
    int position;
    bool won;

    Player(string playerName) {
        this->name = playerName;
        this->position = 0;
        this->won = false;
    }
};

class Ladder {
private:
    int start, end;

public:
    Ladder(int start, int end) {
        this->start = start;
        this->end = end;
    }

    int getStart() {
        return this->start;
    }

    int getEnd() {
        return this->end;
    }

};

class Snake {
private:
    int head, tail;

public:
    Snake(int head, int tail) {
        this->head = head;
        this->tail = tail;
    }

    int getHead() {
        return this->head;
    }

    int getTail() {
        return this->tail;
    }
};

class Dice {
private:
    int maximumValue, minimumValue;
    Utils *utils;

public:
    Dice(int maximumVal, int minimumVal) {
        this->utils = new Utils();
        this->maximumValue = maximumVal;
        this->minimumValue = minimumVal;
    }

    int roll() {
        return utils->getRandomNumInRange(minimumValue, maximumValue);
    }
};

class Board {
private:
    int size, start, end;

public:
    Board(int size) {
        this->size = size;
        this->start = 1;
        this->end = start + size - 1;
    }

    int getStart() {
        return start;
    }

    int getEnd() {
        return end;
    }

};

class Game {
private:
    int numberOfSnakes;
    int numberOfLadders;

    deque<Player*> players;
    vector<Snake*> snakes;
    vector<Ladder*> ladders;

    Board *board;
    Dice *dice;
    Utils *utils;

    void initBoard() {
        unordered_set<string> store;
        for (int i = 0; i < numberOfSnakes; ++i) {
            while (true) {
                int snakeTail = utils->getRandomNumInRange(board->getStart(), board->getEnd());
                int snakeHead = utils->getRandomNumInRange(board->getStart(), board->getEnd());
                if (snakeTail >= snakeHead)
                    continue;
                string startEndPair = to_string(snakeTail) + "-" + to_string(snakeHead);
                if (store.find(startEndPair) == store.end()) {
                    Snake *snake = new Snake(snakeHead, snakeTail);
                    this->snakes.push_back(snake);
                    break;
                }
            }
        }

        for (int i = 0; i < numberOfLadders; ++i) {
            while (true) {
                int ladderStart = utils->getRandomNumInRange(board->getStart(), board->getEnd());
                int ladderEnd = utils->getRandomNumInRange(board->getStart(), board->getEnd());
                if (ladderStart >= ladderEnd)
                    continue;
                string startEndPair = to_string(ladderStart) + "-" + to_string(ladderEnd);
                if (store.find(startEndPair) == store.end()) {
                    Ladder *ladder = new Ladder(ladderStart, ladderEnd);
                    this->ladders.push_back(ladder);
                    break;
                }
            }
        }
    }

    int getNewPosition(int currentPosition) {
        for (Snake *(&snake): snakes) {
            if (currentPosition == snake->getHead()) {
                cout << "Sanke bit\n";
                return snake->getTail();
            }
        }

        for (Ladder *(&ladder) : ladders) {
            if (currentPosition == ladder->getEnd()) {
                cout << "Climbed Ladder\n";
                return ladder->getStart();
            }
        }
        return currentPosition;
    }

public:
    Game(int boardSize, int numOfSnakes, int numOfLadders) {
        board = new Board(boardSize);
        this->numberOfSnakes = numOfSnakes;
        this->numberOfLadders = numOfLadders;

        dice = new Dice(1, 7);
        utils = new Utils();

        initBoard();
    }

    void addPlayer(Player *player) {
        this->players.push_back(player);
    }

    void startGame() {
        while (true) {
            Player *player = players.front();
            players.pop_front();

            int val = dice->roll();
            int newPosition = player->position + val;

            if (newPosition > board->getEnd()) {
                players.push_back(player);
            } else {
                player->position = getNewPosition(newPosition);
                if (player->position == board->getEnd()) {
                    player->won = true;
                    cout << "Player " << player->name << " won.\n";
                } else {
                    cout << "Setting " << player->name << "'s new position to " << player->position << endl;
                    players.push_back(player);
                }
            }
            if (players.size() < 2) break;
        }
    }
};

int main () {
    int size, numOfSnakes, numOfLadders, numOfPlayers;

    cout << "Enter board size:\n";
    cin >> size;
    cout << "Enter number of snakes:\n";
    cin >> numOfSnakes;
    cout << "Enter number of ladders:\n";
    cin >> numOfLadders;
    cout << "Enter number of players:\n";
    cin >> numOfPlayers;

    Game *game = new Game(size, numOfSnakes, numOfLadders);

    for (int i = 0; i  < numOfPlayers; ++i) {
        string playerName;
        cout << "Enter player name:\n";
        cin >> playerName;
        game->addPlayer(new Player(playerName));
    }
    game->startGame();
}