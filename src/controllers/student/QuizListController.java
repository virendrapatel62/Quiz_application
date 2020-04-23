package controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import models.Quiz;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class QuizListController implements Initializable {
    @FXML private FlowPane quizListContainer;
    Map<Quiz , Integer> quizzes = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        quizzes = Quiz.getAllWithQuestionCount();
        Set<Quiz> keys = quizzes.keySet();
        for(Quiz quiz : keys){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/QuizCardLayoutFXML.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuizCardLayoutFXMLController quizCardLayoutFXMLController = fxmlLoader.getController();
                quizCardLayoutFXMLController.setTitle(quiz.getTitle());
                quizCardLayoutFXMLController.setNoq(quizzes.get(quiz) + "");
                quizListContainer.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
