package controllers.student;

import controllers.student.smallLayout.AttempedQuizListItemFXML;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import listeners.AttempedQuizItemClickListener;
import models.Quiz;
import models.QuizResult;
import models.Student;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AttempedQuizListScreenFXMLController implements Initializable {
    public VBox list;
    public Label total;
    public Label Aq;
    public Label ra;
    public Label wa;
    public PieChart attempedChart;
    public PieChart rightWrongChart;
    private Student student;

    public void setStudent(Student student) {
        this.student = student;

        setList();
    }


    private void setList(){
        Map<QuizResult, Quiz> map = QuizResult.getQuizzes(student);
        Set<QuizResult> quizzesData = map.keySet();

        for(QuizResult quizResult : quizzesData){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/fxml/student/smallLayout/AttempedQuizListItemFXML.fxml"));
            try {
                Parent root = fxmlLoader.load();
                AttempedQuizListItemFXML attempedQuizListItemFXML = fxmlLoader.getController();
            attempedQuizListItemFXML.setItemClickListener(new AttempedQuizItemClickListener() {
                @Override
                public void itemClicked(Integer nof, Integer noa) {

                    int attemped = noa;
                    int nonAtemped = nof - attemped ;
                    int right = quizResult.getRightAnswers() ;
                    int wrong = attemped - right ;

                    total.setText(nof.toString());
                    Aq.setText(attemped + "");
                    ra.setText(right+"");
                    wa.setText((noa - quizResult.getRightAnswers()) + "");


                    ObservableList<PieChart.Data> attempedData  = attempedChart.getData();
                    attempedData.clear();
                    attempedData.add(new PieChart.Data("Attemped Questions ("+attemped+")" , attemped));
                        attempedData.add(new PieChart.Data("Not Attemped Questions ("+nonAtemped+")" , nonAtemped));


                    ObservableList<PieChart.Data> answerData  = rightWrongChart.getData();
                    answerData.clear();
                    answerData.add(new PieChart.Data("Right Answers ("+right+")" , right));
                    answerData.add(new PieChart.Data("Wrong Answers ("+wrong+")" , wrong));


                }
            });
                attempedQuizListItemFXML.setData(map.get(quizResult) , quizResult);
                this.list.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
