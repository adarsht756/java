import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Option;
import Models.Question;
import Models.SurveyResponse;

/**
 * Driver
 */
public class Driver {

    public static void main(String[] args) {
        SurveyManager surveyManager = new SurveyManager();

        surveyManager.createUser("adarsh");
        surveyManager.createUser("rishabh");

        surveyManager.createAdmin("prashant");

        List<Question> questions = new ArrayList<Question>();
        List<Option> options = new ArrayList<Option>();
        options.add(new Option("Good", 4));
        options.add(new Option("Best", 5));
        options.add(new Option("Okay", 3));
        options.add(new Option("Bad", 2));

        questions.add(new Question("How was Interview", options));
        questions.add(new Question("How was question clarity", options));

        surveyManager.createSurvey("Interview", questions, "prashant");

        Map<String, Option> responses = new HashMap<String, Option>();
        responses.put("How was Interview", options.get(0));
        responses.put("How was question clarity", options.get(2));
        SurveyResponse surveyResponse = new SurveyResponse(responses);
        surveyManager.answerSurvey("adarsh", "Interview", surveyResponse);


        responses = new HashMap<String, Option>();
        responses.put("How was Interview", options.get(2));
        responses.put("How was question clarity", options.get(1));
        surveyResponse = new SurveyResponse(responses);
        surveyManager.answerSurvey("rishabh", "Interview", surveyResponse);


        surveyManager.addQuestionToSurvey("Interview", new Question("How was IDE", options), "prashant");

        surveyManager.createUser("mohit");
        responses = new HashMap<String, Option>();
        responses.put("How was Interview", options.get(2));
        responses.put("How was question clarity", options.get(1));
        responses.put("How was IDE", options.get(0));
        surveyResponse = new SurveyResponse(responses);
        surveyManager.answerSurvey("mohit", "Interview", surveyResponse);

        surveyManager.deleteQuestionFromSurvey("Interview", "How was IDE", "prashant");
        surveyManager.deleteQuestionFromSurvey("Interview", "How was Interview", "adarsh");

        surveyManager.showQuestionRating("Interview", "How was Interview", "prashant");
        surveyManager.showSurveyRating("prashant", "Interview");

        surveyManager.updateOptionWeightOfQuestion("Interview", "How was Interview", "Best", 6, "prashant");
    }
}