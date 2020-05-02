package controllers.student.smallLayout;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listeners.AttempedQuizItemClickListener;
import models.Quiz;
import models.QuizResult;

import java.net.URL;
import java.util.ResourceBundle;

public class AttempedQuizListItemFXML implements Initializable {
    public Label title;
    public VBox item;
    private Quiz quiz;
    private QuizResult quizResult;
    private AttempedQuizItemClickListener itemClickListener;

    public void setData(Quiz quiz , QuizResult quizResult) {
        this.quiz = quiz;
        this.quizResult = quizResult;
        this.title.setText(this.quiz.getTitle());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setItemClickListener(AttempedQuizItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void loadData(MouseEvent mouseEvent) {
        System.out.println("Item Clicked...");
        Integer numberOfAttempedQuestions = quizResult.getNumberOfAttempedQuestions();
        Integer numberOfQuestions = quiz.getNumberOfQuestions();
        System.out.println(numberOfAttempedQuestions);
        System.out.println(numberOfQuestions);
        itemClickListener.itemClicked(numberOfQuestions , numberOfAttempedQuestions);
    }
}
