import java.util.Random;

public class Dice {
    private int minValue;
    private int maxValue;

    public Dice(int minVal, int maxVal) {
        this.minValue = minVal;
        this.maxValue = maxVal;
    }

    public int roll() {
        Random random = new Random();
        return random.nextInt(minValue, maxValue + 1);
    }
}