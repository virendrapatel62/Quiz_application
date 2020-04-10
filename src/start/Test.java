
package start;

import java.sql.Connection;
import java.util.ArrayList;
import models.Question;
import models.Quiz;

public class Test {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("MySql Quiz");
        Question question = new Question(quiz, "1 + 3 = ? ", "4", "5", "10", "45", "4");
        
        ArrayList<Question> arr = new ArrayList<>();
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        arr.add(question);
        question.setQuiz(quiz);
        quiz.save(arr);
    }
}
