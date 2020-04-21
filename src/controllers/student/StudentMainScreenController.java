package controllers.student;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentMainScreenController implements Initializable {


    @FXML  private JFXButton backButton;
    @FXML  private StackPane stackPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addQuizListScreen();
    }


    private void addQuizListScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("/fxml/student/QuizListFXML.fxml"));

        try {
            Node node = fxmlLoader.load();
            stackPanel.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
