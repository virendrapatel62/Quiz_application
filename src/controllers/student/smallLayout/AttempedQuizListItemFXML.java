package controllers.student.smallLayout;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.Quiz;

import java.net.URL;
import java.util.ResourceBundle;

public class AttempedQuizListItemFXML implements Initializable {
    public Label title;
    private Quiz quiz;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
