public class Board {
    int size;
    int start, end;

    public Board(int size) {
        this.start = 1;
        this.size = size;
        this.end = start + size - 1;
    }
}