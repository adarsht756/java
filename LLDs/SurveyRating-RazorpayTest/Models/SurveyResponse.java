package Models;
import java.util.Map;

/**
 * SurveyResponse
 */
public class SurveyResponse {
    private Map<String, Option> response; // Key: QuestionName : Option

    public SurveyResponse(Map<String, Option> response) {
        this.response = response;
    }

    public Map<String, Option> getSurveryRespone() {
        return this.response;
    }
}