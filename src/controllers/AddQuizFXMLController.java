
package controllers;

import com.jfoenix.controls.*;

import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.util.Duration;
import models.Question;
import models.Quiz;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AddQuizFXMLController implements Initializable {
    @FXML
    private JFXTreeView treeView;
    @FXML
    private JFXTextField quizTitle;
    @FXML
    private JFXTextArea question;
    @FXML
    private JFXTextField option1;
    @FXML
    private JFXTextField option2;
    @FXML
    private JFXTextField option3;
    @FXML
    private JFXTextField option4;
    @FXML
    private JFXRadioButton option1radio;
    @FXML
    private JFXRadioButton option2radio;
    @FXML
    private JFXRadioButton option3radio;
    @FXML
    private JFXRadioButton option4radio;
    @FXML
    private JFXButton addNextQuestion;
    @FXML
    private JFXButton submitQuiz;
    
    private ToggleGroup radioGroup;
    @FXML
    private JFXButton setQuizTitleButton;
    
    // my Variable
    private Quiz quiz = null;
    private ArrayList<Question> questions = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        radioButtonSetup();
        renderTreeView();
        
    }

    private void renderTreeView(){
        Map<Quiz , List<Question>> data = Quiz.getAll();
        Set<Quiz> quizzes = data.keySet();

        TreeItem root = new TreeItem("Quizzes");
        for(Quiz q : quizzes){
            TreeItem quizTreeItem = new TreeItem(q);

            List<Question> questions = data.get(q);
            for(Question question : questions){
                TreeItem questionTreeItem = new TreeItem(question);
                questionTreeItem.getChildren().add(new TreeItem("A : " + question.getOption1()));
                questionTreeItem.getChildren().add(new TreeItem("B : " +question.getOption2()));
                questionTreeItem.getChildren().add(new TreeItem("C : " +question.getOption3()));
                questionTreeItem.getChildren().add(new TreeItem("D : " +question.getOption4()));
                questionTreeItem.getChildren().add(new TreeItem("Ans : " +question.getAnswer()));
                quizTreeItem.getChildren().add(questionTreeItem);
            }

            quizTreeItem.setExpanded(true);
            root.getChildren().add(quizTreeItem);
        }

        root.setExpanded(true);
        this.treeView.setRoot(root);
    }
    
    private void radioButtonSetup(){
        radioGroup = new ToggleGroup();
        option1radio.setToggleGroup(radioGroup);
        option2radio.setToggleGroup(radioGroup);
        option3radio.setToggleGroup(radioGroup);
        option4radio.setToggleGroup(radioGroup);
    }

    @FXML
    private void setQuizTitle(ActionEvent event) {
        System.out.println("controllers.AddQuizFXMLController.setQuizTitle()");
        String title = quizTitle.getText();
        if(title.trim().isEmpty()){
            Notifications.create()
                    .darkStyle()
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.millis(2000))
                    .text("Enter valid Quiz Title")
                    .title("Quiz Title").showError();
            
        }else{
            quizTitle.setEditable(false);
            System.err.println("Save Title.....");
            this.quiz = new Quiz(title);
        }
    }
    
    private boolean validateFields(){
        
        
        if(quiz ==null){
            Notifications.create()
                    .title("Quiz").position(Pos.CENTER)
                    .darkStyle().text("Please Enter Quiz Title")
                    .showError();
           return false;
        }
        
        String qu = this.question.getText();
        String op1 = this.option1.getText();
        String op2 = this.option2.getText();
        String op3 = this.option3.getText();
        String op4 = this.option4.getText();
        Toggle selectedRadio = radioGroup.getSelectedToggle();
        System.out.println(selectedRadio);
        if(qu.trim().isEmpty() || 
                op1.trim().isEmpty() || 
                op2.trim().isEmpty() || op3.trim().isEmpty()
                || op4.trim().isEmpty()){
            
           Notifications.create()
                    .title("Question").position(Pos.CENTER)
                    .darkStyle().text("All Fields Are Required.... \n [Question , Option1 , Option 2 , Option 3 , Option 4]")
                    .showError();
           return false;
            
            
            
        }else{
            if(selectedRadio == null){
                Notifications.create()
                    .title("Question").position(Pos.CENTER)
                    .darkStyle().text("Please Select A Answer....")
                    .showError();
                return false;
            }else{
                return true;   // save Quistion and add next 
            }
        }
    }

    @FXML
    private void addNextQuestion(ActionEvent event) {
       addQuestions();
    }
    
    private boolean addQuestions(){
         boolean valid = validateFields();
        Question question = new Question();
        if(valid){
            //save  
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());
            Toggle selected = radioGroup.getSelectedToggle();
            String ans = null;
            if(selected == option1radio){
                ans = option1.getText().trim();
            }else if(selected == option2radio){
                ans = option2.getText().trim();
            }
            else if(selected == option3radio){
                ans = option3.getText().trim();
            }
            else if(selected == option4radio){
                ans = option4.getText().trim();
            }
            question.setAnswer(ans);
            question.setQuestion(this.question.getText().trim());
           
            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            questions.add(question);
            question.setQuiz(quiz);
            System.out.println("Save Question...");
            System.out.println(questions);
            System.out.println(quiz);
        }
        
        return valid;
    }

    @FXML
    private void submitQuiz(ActionEvent event) {
       boolean flag = addQuestions();
       if(flag){
           flag = quiz.save(questions);
           if(flag){
               // success
               this.quizTitle.setDisable(false);
               Notifications.create()
                    .title("Success").position(Pos.CENTER)
                    .darkStyle().text("Quiz Successfully Saved...")
                    .showInformation();

           }else{
               // eoor
               Notifications.create()
                    .title("Fail..").position(Pos.CENTER)
                    .darkStyle().text("cant Save Quiz.. Try Again..")
                    .showError();
           }
       }
    }
    
    
}
