package models;

import sun.net.www.MeteredStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

public class QuizResult {
    private Integer id ;
    private  Quiz quiz ;
    private  Student student ;
    private Integer rightAnswers ;
    private Timestamp timestamp;

    public static  class MetaData{
        public static final String TABLE_NAME ="QUIZ_RESULTS";
        public static final String ID ="id";
        public static final String QUIZ_ID ="QUIZ_ID";
        public static final String STUDENT_ID ="STUDENT_ID";
        public static final String RIGHT_ANSWERS ="RIGHT_ANSWERS";
        public static final String TIMESTAMP ="date_time";
    }

    {
        timestamp = new Timestamp(new Date().getTime());
    }

    public QuizResult() {
    }

    public QuizResult(Integer id, Quiz quiz, Student student, Integer rightAnswers) {
        this.id = id;
        this.quiz = quiz;
        this.student = student;
        this.rightAnswers = rightAnswers;
    }

    public QuizResult(Quiz quiz, Student student, Integer rightAnswers) {
        this.quiz = quiz;
        this.student = student;
        this.rightAnswers = rightAnswers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setRightAnswers(Integer rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Student getStudent() {
        return student;
    }

    public Integer getRightAnswers() {
        return rightAnswers;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


    public static void createTable(){

        String raw = "CREATE table %s (\n" +
                "        %s int not null PRIMARY key ,\n" +
                "        %s int not null,\n" +
                "        %s int not null ,\n" +
                "        %s int not null ,\n" +
                "        %s timestamp NOT null ,\n" +
                "        FOREIGN KEY (%s) REFERENCES %s(%s),\n" +
                "        FOREIGN KEY (%s) REFERENCES %s(%s)\n" +
                "        )";
        String query  = String.format(raw,
                MetaData.TABLE_NAME ,
                MetaData.ID,
                MetaData.STUDENT_ID,
                MetaData.QUIZ_ID,
                MetaData.RIGHT_ANSWERS,
                MetaData.TIMESTAMP,
                MetaData.STUDENT_ID,
                Quiz.MetaData.TABLE_NAME ,
                Quiz.MetaData.QUIZ_ID ,
                MetaData.STUDENT_ID ,
                Student.MetaData.TABLE_NAME,
                Student.MetaData.ID
                );

        System.err.println(query);
        try{
            String connectionUrl = "jdbc:sqlite:quiz.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.QuizResults.createTable()");
            System.out.println(b);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
