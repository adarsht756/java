import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Question;
import Models.SurveyResponse;
import Models.User;

public class SurveyManager {
    Map<String, Survey> surveys; // keeping string for tracking survey
    Map<String, User> adminStore;
    Map<String, User> users;
    int adminCount = 0, surveyCount = 0, userCount = 0;

    public SurveyManager() {
        this.surveys = new HashMap<String, Survey>();
        this.adminStore = new HashMap<String, User>();
        this.users = new HashMap<String, User>();
    }

    public void createAdmin(String username) {
        User user = new User(adminCount++, username, "ADMIN");
        adminStore.put(username, user);
        System.out.println("Admin " + username + " created sucessfully");
    }

    public boolean isUserRegistered(String username) {
        return this.users.containsKey(username);
    }

    public void createSurvey(String surveyName, List<Question> questions, String adminName) {
        if (adminStore.containsKey(adminName)) {
            Survey survey = new Survey(surveyName, surveyCount, questions);
            surveys.put(surveyName, survey);
            surveyCount++;
            System.out.println("Survey Created successfully");
        } else
            System.out.println("Requested user is not admin");
    }

    public void addQuestionToSurvey(String surveyName, Question question, String adminName) {
        if (adminStore.containsKey(adminName)) {
            if (surveys.containsKey(surveyName)) {
                Survey survey = surveys.get(surveyName);
                survey.addQuestion(question);
                System.out.println("Question added successfully");
            } else
                System.out.println("Survey not found");
        } else
            System.out.println("Requested user is not admin");
    }

    public void deleteQuestionFromSurvey(String surveyName, String questionName, String requestedUser) {
        if (adminStore.containsKey(requestedUser)) {
            if (surveys.containsKey(surveyName)) {
                Survey survey = surveys.get(surveyName);
                survey.deleteQuestion(questionName);
                surveys.put(surveyName, survey);
            }
            System.out.println("Survey not found");
        }
        else System.out.println("Requested user is not admin");
    }

    public void updateOptionWeightOfQuestion(String surveyName, String questionName, String optionName,
            Integer updatedWeight, String requestedUser) {
        if (adminStore.containsKey(requestedUser)) {
            if (surveys.containsKey(surveyName)) {
                Survey survey = surveys.get(surveyName);
                survey.updateOptionWeightForQuestion(questionName, optionName, updatedWeight);
            } else
                System.out.println("Survey not found");
        } else
            System.out.println("Requested user is not admin");
    }

    public void createUser(String username) {
        User user = new User(userCount++, username, "USER");
        this.users.put(user.getUsername(), user);
        System.out.println("User added successfully");
    }

    public void answerSurvey(String username, String surveyName, SurveyResponse surveyResponse) {
        if (surveys.containsKey(surveyName)) {
            Survey survey = surveys.get(surveyName);
            if (isUserRegistered(username)) {
                survey.addUserResponse(username, surveyResponse);
            } else
                System.out.println("User is registered for survey " + surveyName);
        } else
            System.out.println("Survey not found");
    }

    public void showSurveyRating(String requestedUser, String surveyName) {
        if (adminStore.containsKey(requestedUser)) {
            if (surveys.containsKey(surveyName)) {
                System.out.println(surveys.get(surveyName).getSurveyRating());
            }
        } else
            System.out.println("Requested user is not admin");
    }

    public void showQuestionRating(String surveyName, String questionName, String requestedUser) {
        if (adminStore.containsKey(requestedUser)) {
            if (surveys.containsKey(surveyName)) {
                Survey survey = surveys.get(surveyName);
                System.out.println(survey.getRatingOfQuestion(questionName));
            } else
                System.out.println("Survey not found");
        } else
            System.out.println("Requested user is not admin");
    }
}