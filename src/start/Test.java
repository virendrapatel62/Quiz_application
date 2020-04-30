
package start;

import java.sql.Connection;
import java.util.ArrayList;

import models.*;
import util.DataCollector;

public class Test {
    public static void main(String[] args) throws Exception {
        Quiz.createTable();
        Question.createTable();
        Student.createTable();
        QuizResult.createTable();
        QuizResultDetails.createTable();
        DataCollector.readAndSaveQuizzesData();
        DataCollector.readAndSaveUserData();
    }
}
