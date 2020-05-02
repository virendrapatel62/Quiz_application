package controllers.student;

import controllers.student.smallLayout.AttempedQuizListItemFXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import models.Quiz;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AttempedQuizListScreenFXMLController implements Initializable {
    public VBox list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Map<Quiz, Integer> map = Quiz.getAllWithQuestionCount();
        Set<Quiz> quizzes = map.keySet();

        for(Quiz quiz : quizzes){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/smallLayout/AttempedQuizListItemFXML.fxml"));
            try {
                Parent root = fxmlLoader.load();
                AttempedQuizListItemFXML attempedQuizListItemFXML = fxmlLoader.getController();
                attempedQuizListItemFXML.setQuiz(quiz);
                this.list.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
