package models;

import constants.DatabaseConstants;

import java.sql.*;
import java.util.Date;
import java.util.Map;

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
                "        %s Integer not null PRIMARY key AUTOINCREMENT,\n" +
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
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.QuizResults.createTable()");
            System.out.println(b);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    public void save(Map<Question , String> userAnsers){
        String raw = "INSERT INTO %s (%s , %s , %s , %s ) values \n" +
                "( ?, ? , ?  , CURRENT_TIMESTAMP)";
        String query  = String.format(raw,
                MetaData.TABLE_NAME ,
                MetaData.STUDENT_ID,
                MetaData.QUIZ_ID,
                MetaData.RIGHT_ANSWERS,
                MetaData.TIMESTAMP);

        System.err.println(query);
        try{
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1 , this.getStudent().getId());
            ps.setInt(2 , this.getQuiz().getQuizId());
            ps.setInt(3 , this.getRightAnswers());
            int result =  ps.executeUpdate();
            if(result > 0){
                ResultSet keys = ps.getGeneratedKeys();
                if(keys.next()){
                    this.setId(keys.getInt(1));
                    // now we will save details..
                    System.out.println(this);
//                    saveQuizResultDetails(userAnsers);
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    private void saveQuizResultDetails(Map<Question , String> userAnswers){

    }

}
