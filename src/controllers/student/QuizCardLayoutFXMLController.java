package controllers.student;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import listeners.NewScreenListener;
import models.Quiz;
import models.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizCardLayoutFXMLController implements Initializable {
    public Label title;
    public Label noq;
    public JFXButton startButton;
    private Quiz quiz;



    private NewScreenListener screenListener;
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
        this.title.setText(this.quiz.getTitle());
    }

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setNoq(String value) {
        this.noq.setText(value);
    }


    public void startQuiz(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("/fxml/student/QuestionsScreenFXML.fxml"));

        try {
            Node node = fxmlLoader.load();
            QuestionsScreenController questionsScreenController= fxmlLoader.getController();
              questionsScreenController.setStudent(this.student);
                questionsScreenController.setQuiz(this.quiz);
                questionsScreenController.setScreenListener(this.screenListener);
            this.screenListener.ChangeScreen(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
