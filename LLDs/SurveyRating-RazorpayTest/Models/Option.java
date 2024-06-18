package Models;
/**
 * Option
 */
public class Option {

    private String optionName;
    private int rating;

    public Option(String name, int rating) {
        this.optionName = name;
        this.rating = rating;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public int getOptionRating() {
        return this.rating;
    }

    public void updateRating(int rating) {
        this.rating = rating;
    }
}