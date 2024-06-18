package Models;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question
 */
public class Question {

    private String questionName;
    private Map<String, Option> options; // key: optionName
    private double rating;

    public Question(String questionName, List<Option> options) {
        this.questionName = questionName;
        this.rating = 0;
        this.options = new HashMap<String, Option>();
        for (Option option: options)
            this.options.put(option.getOptionName(), option);
    }

    public void updateWeightOfOption(String optionName, int updatedWeight) {
        if (options.containsKey(optionName)) {
            this.options.put(optionName, new Option(optionName, updatedWeight));
        } else System.out.println("Option not found");
    }

    public String getName() {
        return this.questionName;
    }

    public double getRating() {
        return this.rating;
    }

    public void updateQuestionRating(double newRating) {
        this.rating = newRating;
    }
}