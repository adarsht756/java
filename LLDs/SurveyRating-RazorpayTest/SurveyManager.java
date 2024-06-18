import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Question;
import Models.SurveyResponse;
import Models.User;

public class SurveyManager {
    Map<String, Survey> surveys;
    Map<String, User> users;

    public SurveyManager() {
        this.surveys = new HashMap<String, Survey>();
        this.users = new HashMap<String, User>();
    }

    public void createAdmin(String userId, String userName) {
        User user = new User.UserBuilder().setUserId(userId).setUserName(userName).setUserRole("ADMIN").build();
        users.put(userId, user);
        System.out.println("Admin " + userName + " created successfully");
    }

    public boolean isUserRegistered(String userName) {
        return this.users.containsKey(userName);
    }

    public void createSurvey(String surveyId, String surveyName, List<Question> questions, String userId) {
        if (surveys.containsKey(surveyId)) {
            System.out.println("SurveyId already exists, choose another surveyId");
            return;
        }
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            Survey survey = new Survey(surveyId, surveyName, questions);
            surveys.put(surveyId, survey);
            System.out.println("Survey Created successfully");
        } else
            System.out.println("Requested user is not admin");
    }

    public void addQuestionToSurvey(String surveyId, Question question, String userId) {
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            if (surveys.containsKey(surveyId)) {
                Survey survey = surveys.get(surveyId);
                survey.addQuestion(question);
                System.out.println("Question added successfully");
            } else
                System.out.println("Survey not found");
        } else
            System.out.println("Requested user is not admin");
    }

    public void deleteQuestionFromSurvey(String surveyId, String questionName, String userId) {
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            if (surveys.containsKey(surveyId)) {
                Survey survey = surveys.get(surveyId);
                survey.deleteQuestion(questionName);
                surveys.put(surveyId, survey);
            }
            else System.out.println("Survey not found");
        }
        else System.out.println("Requested user is not admin");
    }

    public void updateOptionWeightOfQuestion(String surveyId, String questionName, String optionName,
            Integer updatedWeight, String userId) {
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            if (surveys.containsKey(surveyId)) {
                Survey survey = surveys.get(surveyId);
                survey.updateOptionWeightForQuestion(questionName, optionName, updatedWeight);
            } else
                System.out.println("Survey not found");
        } else
            System.out.println("Requested user is not admin");
    }

    public void createUser(String userId, String userName) {
        if (users.containsKey(userId)) {
            System.out.println("UserId already exists, choose another one");
            return;
        }
        User user = new User.UserBuilder().setUserId(userId).setUserName(userName).setUserRole("USER").build();
        this.users.put(userId, user);
        System.out.println("User added successfully");
    }

    public void answerSurvey(String userId, String surveyId, SurveyResponse surveyResponse) {
        if (surveys.containsKey(surveyId)) {
            Survey survey = surveys.get(surveyId);
            if (isUserRegistered(userId)) {
                survey.addUserResponse(userId, surveyResponse);
            } else
                System.out.println("User is not registered");
        } else
            System.out.println("Survey not found");
    }

    public void showSurveyRating(String userId, String surveyId) {
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            if (surveys.containsKey(surveyId)) {
                System.out.println(surveys.get(surveyId).getSurveyRating());
            }
        } else
            System.out.println("Requested user is not admin");
    }

    public void showQuestionRating(String surveyId, String questionName, String userId) {
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            if (surveys.containsKey(surveyId)) {
                Survey survey = surveys.get(surveyId);
                System.out.println(survey.getRatingOfQuestion(questionName));
            } else
                System.out.println("Survey not found");
        } else
            System.out.println("Requested user is not admin");
    }

    public void deleteSurvey(String surveyId, String userId) {
        if (users.containsKey(userId) && users.get(userId).getRole().equals("ADMIN")) {
            if (surveys.containsKey(surveyId)) {
                System.out.println("Survey \"" + surveys.get(surveyId).surveyName + "\" deleted successfully");
                surveys.remove(surveyId);
            } else System.out.println("Survey not foudn");
        } else System.out.println("Requested user is not admin");
    }
}