package controllers;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import models.Quiz;

import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AdminDashboardFXMLController implements Initializable {


    public BarChart barchart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Map<Quiz , Integer> data = Quiz.getStudentCount();
        Set<Quiz> quizzes = data.keySet();
        ObservableList<XYChart.Series> series = this.barchart.getData();
        XYChart.Series series1 = new XYChart.Series();


        for(Quiz  quiz : quizzes){
            XYChart.Data data1 = new XYChart.Data(
                    quiz.getTitle() + "("+data.get(quiz)+")" , data.get(quiz));
            series1.getData().add(data1);
        }

        series.add(series1);
    }
}
