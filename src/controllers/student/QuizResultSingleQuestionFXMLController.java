package controllers.student;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import models.Question;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizResultSingleQuestionFXMLController implements Initializable {
    public Label question;
    public Label option1;
    public Label option2;
    public Label option3;
    public Label option4;

    private  Question questionObject;
    private String userAnswer;

    public void setValues(Question questionObject, String userAnswer) {
        this.questionObject = questionObject;
        if(userAnswer==null)
            userAnswer = "";
        else
            this.userAnswer = userAnswer;

        setTexts();
    }

    private void setTexts(){
        this.question.setText(this.questionObject.getQuestion());
        this.option1.setText(this.questionObject.getOption1());
        this.option2.setText(this.questionObject.getOption2());
        this.option3.setText(this.questionObject.getOption3());
        this.option4.setText(this.questionObject.getOption4());

        settingColors();
    }

    private void settingColors() {
        Label rightAnswer = null;

        if(option1.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer().trim())){
            rightAnswer = option1;
        }
        else if(option2.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer().trim())){
            rightAnswer = option2;
        }
        else if(option3.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer().trim())){
            rightAnswer = option3;
        }
        else if(option4.getText().trim().equalsIgnoreCase(this.questionObject.getAnswer().trim())){
            rightAnswer = option4;
        }
        rightAnswer.setTextFill(Color.web("#26ae60"));
        rightAnswer.setText("✔ "+rightAnswer.getText());


        if(!userAnswer.trim().equalsIgnoreCase(this.questionObject.getAnswer().trim())){
            Label userAnswer = null;
            if(option1.getText().trim().equalsIgnoreCase(this.userAnswer.trim())){
                userAnswer = option1;
            }
            else if(option2.getText().trim().equalsIgnoreCase(this.userAnswer.trim().trim())){
                userAnswer = option2;
            }

            else if(option3.getText().trim().equalsIgnoreCase(this.userAnswer.trim().trim())){
                userAnswer = option3;
            }
            else if(option4.getText().trim().equalsIgnoreCase(this.userAnswer.trim().trim())){
                userAnswer = option4;
            }

            userAnswer.setTextFill(Color.web("#B83227"));
            userAnswer.setText("✖ " + userAnswer.getText());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
