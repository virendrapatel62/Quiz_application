package controllers.student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
public class QuestionsScreenController implements Initializable {

    public Label title;
    public Label time;
    public Label question;
    public JFXRadioButton option1;
    public JFXRadioButton option2;
    public JFXRadioButton option3;
    public JFXRadioButton option4;
    public ToggleGroup options;
    public JFXButton next;
    public JFXButton submit;

    private Quiz quiz;
    private List<Question> questionList;
    private  Question currentQuestion;
    int currentIndex = 0 ;

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
        this.getData();
    }

    private void getData(){
        if(quiz != null){
            this.questionList = quiz.getQuestions();
            Collections.shuffle(this.questionList);
            setNextQuestion();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.showNextQuestionButton();
        this.hideSubmitQuizButton();
    }

    public void nextQuestions(ActionEvent actionEvent) {
        this.setNextQuestion();
    }
    private void setNextQuestion(){
        if(!(currentIndex >= questionList.size())) {
            this.currentQuestion = this.questionList.get(currentIndex);
            List<String> options = new ArrayList<>();
            options.add(this.currentQuestion.getOption1());
            options.add(this.currentQuestion.getOption2());
            options.add(this.currentQuestion.getOption3());
            options.add(this.currentQuestion.getOption4());
            Collections.shuffle(options);

            this.question.setText(this.currentQuestion.getQuestion());
            this.option1.setText(options.get(0));
            this.option2.setText(options.get(1));
            this.option3.setText(options.get(2));
            this.option4.setText(options.get(3));
            currentIndex++;
        }else{
            hideNextQuestionButton();
            showSubmitQuizButton();
        }
    }

    private void hideNextQuestionButton(){
        this.next.setVisible(false);
    }
    private void showNextQuestionButton(){
        this.next.setVisible(true);
    }

    private void hideSubmitQuizButton(){
        this.submit.setVisible(false);
    }

    private void showSubmitQuizButton(){
        this.submit.setVisible(true);
    }
    public void submit(ActionEvent actionEvent) {
    }
}
