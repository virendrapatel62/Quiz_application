package controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import listeners.NewScreenListener;
import models.Quiz;
import models.Student;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class QuizListController implements Initializable {
    @FXML private FlowPane quizListContainer;
    Map<Quiz , Integer> quizzes = null;
    private NewScreenListener screenListener;
    private Set<Quiz> keys ;

    private Student student;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setScreenListener(NewScreenListener screenListener) {
        this.screenListener = screenListener;


    }


    public  void setCards(){

        for(Quiz quiz : keys){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/QuizCardLayoutFXML.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuizCardLayoutFXMLController quizCardLayoutFXMLController = fxmlLoader.getController();
                quizCardLayoutFXMLController.setQuiz(quiz);
                quizCardLayoutFXMLController.setStudent(this.student);
                quizCardLayoutFXMLController.setNoq(quizzes.get(quiz) + "");
                quizCardLayoutFXMLController.setScreenListener(this.screenListener);
                quizListContainer.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        quizzes = Quiz.getAllWithQuestionCount();
        keys = quizzes.keySet();

    }
}
