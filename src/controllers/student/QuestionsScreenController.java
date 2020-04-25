package controllers.student;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void nextQuestions(ActionEvent actionEvent) {
    }

    public void submit(ActionEvent actionEvent) {
    }
}
