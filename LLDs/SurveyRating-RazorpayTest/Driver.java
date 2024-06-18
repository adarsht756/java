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

        surveyManager.createUser("adarsht756", "adarsh");
        surveyManager.createUser("rishabh123", "rishabh");

        surveyManager.createAdmin("prashantAdmin1", "prashant");

        List<Question> questions = new ArrayList<Question>();
        List<Option> options = new ArrayList<Option>();
        options.add(new Option("Good", 4));
        options.add(new Option("Best", 5));
        options.add(new Option("Okay", 3));
        options.add(new Option("Bad", 2));

        questions.add(new Question("How was Interview", options));
        questions.add(new Question("How was question clarity", options));

        surveyManager.createSurvey("survey1", "Interview" , questions, "prashantAdmin1");

        Map<String, Option> responses = new HashMap<String, Option>();
        responses.put("How was Interview", options.get(0));
        responses.put("How was question clarity", options.get(2));
        SurveyResponse surveyResponse = new SurveyResponse(responses);
        surveyManager.answerSurvey("adarsht756", "survey1", surveyResponse);


        responses = new HashMap<String, Option>();
        responses.put("How was Interview", options.get(2));
        responses.put("How was question clarity", options.get(1));
        surveyResponse = new SurveyResponse(responses);
        surveyManager.answerSurvey("rishabh123", "survey1", surveyResponse);


        surveyManager.addQuestionToSurvey("survey1", new Question("How was IDE", options), "prashantAdmin1");

        surveyManager.createUser("mohit123", "mohit");
        responses = new HashMap<String, Option>();
        responses.put("How was Interview", options.get(2));
        responses.put("How was question clarity", options.get(1));
        responses.put("How was IDE", options.get(0));
        surveyResponse = new SurveyResponse(responses);
        surveyManager.answerSurvey("mohit123", "survey1", surveyResponse);

        surveyManager.deleteQuestionFromSurvey("survey1", "How was IDE", "prashantAdmin1");
        surveyManager.deleteQuestionFromSurvey("survey1", "How was Interview", "adarsh");

        surveyManager.showQuestionRating("survey1", "How was Interview", "prashantAdmin1");
        surveyManager.showSurveyRating("prashantAdmin1", "survey1");

        surveyManager.updateOptionWeightOfQuestion("survey1", "How was Interview", "Best", 6, "prashantAdmin1");
        surveyManager.deleteSurvey("survey1", "prashantAdmin1");
    }
}