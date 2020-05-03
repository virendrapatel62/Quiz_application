package controllers;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Quiz;
import models.QuizResult;
import models.Student;

import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AdminDashboardFXMLController implements Initializable {


    public BarChart barchart;
    public TableColumn<Student , Integer> idcol;
    public TableColumn <Student , String>namecol;
    public TableColumn<Student , String> emailcol;
    public TableColumn<Student , String> phonecol;
    public TableColumn<Student , Character> gendercol;
    public TableView<Student> table;
    private Quiz selectedQuiz;


    private  void setupTeble(){
        this.idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.namecol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.emailcol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.phonecol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        this.gendercol.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }

    private void setupBarChart(){
        Map<Quiz , Integer> data = Quiz.getStudentCount();
        Set<Quiz> quizzes = data.keySet();
        ObservableList<XYChart.Series> series = this.barchart.getData();
        XYChart.Series series1 = new XYChart.Series();



        for(Quiz  quiz : quizzes){
            XYChart.Data data1 = new XYChart.Data(
                    quiz.getTitle() + "("+data.get(quiz)+")" , data.get(quiz));
            data1.setExtraValue(quiz);
            series1.getData().add(data1);
        }

        series.add(series1);

        ObservableList<XYChart.Data> barData = series1.getData();
        for(XYChart.Data dt : barData){
            Node node = dt.getNode();
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(dt);
                    selectedQuiz = (Quiz) dt.getExtraValue();
                    collectStudents();
                }
            });
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTeble();
        setupBarChart();
    }

    private void collectStudents(){
        List<Student> students = QuizResult.getStudents(this.selectedQuiz);
        this.table.getItems().clear();
        this.table.getItems().addAll(students);
    }
}
