package Model;

public class Pair implements Comparable<Pair> {
    public int first;
    public String second;

    public Pair(int first, String second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return ("(" + first + ", " + second + ")");
    }

    @Override
    public int compareTo(Pair a) {
        if (a.getFirst() > this.first)
            return 1;
        else if (a.getFirst() < this.first)
            return -1;
        return 0;
    }
}