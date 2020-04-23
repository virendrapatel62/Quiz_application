package controllers.student;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class QuizCardLayoutFXMLController implements Initializable {
    public Label title;
    public Label noq;
    public JFXButton startButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setNoq(String value) {
        this.noq.setText(value);
    }

    public void setTitle(String value) {
        this.title.setText(value);
    }

    public void startQuiz(ActionEvent actionEvent) {
    }
}
