package controllers.student;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import models.Student;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentHomeFXMLController implements Initializable {
    public Tab dashboard;
    public Tab allQuizzes;
    public Tab attempedQuizzes;
    private Student student ;


    public void setStudent(Student student) {
        this.student = student;

        setAllQuizzesScreen();
        setAttempedQuizzesScreen();

    }

    private void setAllQuizzesScreen(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/StudentAllQuizzes.fxml"));
            Parent root = fxmlLoader.load();
            StudentAllQuizzesController controller = fxmlLoader.getController();
            controller.setStudent(student);

            this.allQuizzes.setContent(root);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setAttempedQuizzesScreen(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/AttempedQuizListScreenFXML.fxml"));
            Parent root = fxmlLoader.load();
            AttempedQuizListScreenFXMLController controller = fxmlLoader.getController();
            controller.setStudent(student);
            this.attempedQuizzes.setContent(root);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}


