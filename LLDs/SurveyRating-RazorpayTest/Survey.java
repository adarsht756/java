import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Option;
import Models.Question;
import Models.SurveyResponse;

/**
 * Survey
 */
public class Survey {
    private Map<String, Question> questions;
    private Map<String, SurveyResponse> responseStore; // key: Username val: surveyResponse
    private double surveyRating;

    public String surveyName;
    public String surveyId;

    public Survey(String surveyId, String surveyName, List<Question> questions) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.questions = new HashMap<String, Question>();
        this.responseStore = new HashMap<String, SurveyResponse>();
        for (Question question : questions) {
            this.questions.put(question.getName(), question);
        }
    }

    private void updateQuestionRating(String questionName, int userRating) {
        double oldQuestionRatingsSum = questions.get(questionName).getRating() * responseStore.size();
        double newQuestionRatingSum = oldQuestionRatingsSum + userRating;
        double questionOverallRating = newQuestionRatingSum / (responseStore.size() + 1);

        Question question = questions.get(questionName);
        question.updateQuestionRating(questionOverallRating);
        questions.put(questionName, question);
    }

    private void updateSurveyRating(SurveyResponse surveyResponse) {
        double oldSumOfRatings = surveyRating * responseStore.size();
        double currentSurveyResponseRatingsSum = 0;
        for (Map.Entry<String, Option> response : surveyResponse.getSurveryRespone().entrySet()) {
            Option option = response.getValue();
            String questionName = response.getKey();
            updateQuestionRating(questionName, option.getOptionRating());
            currentSurveyResponseRatingsSum += option.getOptionRating();
        }
        surveyRating = (oldSumOfRatings + ((currentSurveyResponseRatingsSum) / surveyResponse.getSurveryRespone().size())) / (responseStore.size() + 1);
    }

    public void addQuestion(Question question) {
        questions.put(question.getName(), question);
    }


    public void deleteQuestion(String quesName) {
        if (questions.containsKey(quesName)) {
            questions.remove(quesName);
        } else
            System.out.println("Question not found in survey");
    }

    public void updateOptionWeightForQuestion(String questionName, String optionName, int updatedWeight) {
        if (questions.containsKey(questionName)) {
            Question question = questions.get(questionName);
            question.updateWeightOfOption(optionName, updatedWeight);
            questions.put(optionName, question);
        } else
            System.out.println("Question not found in survey");
    }

    public void addUserResponse(String userId, SurveyResponse surveyResponse) {
        if (responseStore.containsKey(userId) == true) {
            System.out.println("User cannot answer survey again");
            return;
        }
        updateSurveyRating(surveyResponse);
        responseStore.put(userId, surveyResponse);
        System.out.println("User response saved successfully");
    }

    public double getSurveyRating() {
        return this.surveyRating;
    }

    public double getRatingOfQuestion(String questionName) {
        return this.questions.get(questionName).getRating();
    }
}
