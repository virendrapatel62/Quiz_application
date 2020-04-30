package controllers.student;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizResultFXMLController implements Initializable {
    public PieChart attempedChart;
    public PieChart scoreChart;
    public VBox questionsContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i = 0 ; i < 6 ; i ++){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/QuizResultSingleQuestionFXML.fxml"));

            try {
                Node node = fxmlLoader.load();
                QuizResultSingleQuestionFXMLController controller= fxmlLoader.getController();
                questionsContainer.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
