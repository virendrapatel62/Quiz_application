
package start;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import models.*;
import org.omg.CORBA.TIMEOUT;
import util.DataCollector;

public class Test {



    public static void main(String[] args) throws Exception {
//        Quiz.createTable();
//        Question.createTable();
//        Student.createTable();
//        QuizResult.createTable();
//        QuizResultDetails.createTable();
//        DataCollector.readAndSaveQuizzesData();
//        DataCollector.readAndSaveUserData();
//        totalSec = 75;

//        Student student = new Student();
//        student.setId(4);
//        System.out.println(QuizResult.getQuezzes(student));
//
//        student.setId(5);
//        System.out.println(QuizResult.getQuezzes(student));

        Quiz quiz
                = new Quiz();
        quiz.setQuizId(8);
        System.out.println(QuizResult.getStudents(quiz));;

    }
}
