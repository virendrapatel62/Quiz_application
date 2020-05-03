package models;

import constants.DatabaseConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;
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


    public boolean save(Map<Question , String> userAnsers){
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
                   return saveQuizResultDetails(userAnsers);
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return false;
    }


    public Integer getNumberOfAttempedQuestions() {
        String raw = "SELECT COUNT(*) FROM %s  WHERE  %s = ?";
        String query = String.format(raw, QuizResultDetails.MetaData.TABLE_NAME
                , QuizResultDetails.MetaData.QUIZ_RESULT_ID
        );


        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager

                    .getConnection(DatabaseConstants.CONNECTION_URL);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, this.getId());
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                return result.getInt(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }



    public static Map<QuizResult , Quiz> getQuizzes(Student student){

        Map<QuizResult ,  Quiz > data = new HashMap<>();
        String raw = "SELECT %s.%s ,  " +
                "%s.%s ," +
                " %s.%s as quiz_id , " +
                "%s.%s FROM %s " +
                "JOIN %s on " +
                "%s.%s = %s.%s WHERE %s = ?";

        String query = String.format(raw ,
                MetaData.TABLE_NAME,
                MetaData.ID,
                MetaData.TABLE_NAME,
                MetaData.RIGHT_ANSWERS,
                Quiz.MetaData.TABLE_NAME,
                Quiz.MetaData.QUIZ_ID,
                Quiz.MetaData.TABLE_NAME,
                Quiz.MetaData.TITLe,
                MetaData.TABLE_NAME,
                Quiz.MetaData.TABLE_NAME,
                MetaData.TABLE_NAME,
                MetaData.QUIZ_ID ,
                Quiz.MetaData.TABLE_NAME,
                Quiz.MetaData.QUIZ_ID ,
                MetaData.STUDENT_ID
                );

        try{
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 , student.getId());
            ResultSet result  =  ps.executeQuery();

            while (result.next()){
                QuizResult quizResult = new QuizResult();
                quizResult.setId(result.getInt(1));
                quizResult.setRightAnswers(result.getInt(2));

                Quiz quiz = new Quiz();
                quiz.setQuizId(result.getInt(3));
                quiz.setTitle(result.getString(4));

                data.put(quizResult , quiz);
            }


        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return data;
    }

    public static List<Student> getStudents(Quiz quiz){
        List<Student> students = new ArrayList();
        String raw = "SELECT st.%s , st.%s ,\n" +
                "st.%s , st.%s ,\n" +
                "st.%s , st.%s \n" +
                "FROM %s  As qr\n" +
                "join %s as st\n" +
                "on st.%s = qr.%s\n" +
                "WHERE %s = ? GROUP by %s";
        String query = String.format(raw ,
                Student.MetaData.ID,
                Student.MetaData.FIRST_NAME,
                Student.MetaData.LAST_NAME,
                Student.MetaData.MOBILE,
                Student.MetaData.EMAIL,
                Student.MetaData.GENDER,
                MetaData.TABLE_NAME,
                Student.MetaData.TABLE_NAME,
                Student.MetaData.ID,
                MetaData.STUDENT_ID,
                MetaData.QUIZ_ID,
                MetaData.STUDENT_ID
        );

        try{
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1 , quiz.getQuizId());
            ResultSet result  =  ps.executeQuery();

            while (result.next()){
                Student student = new Student();
                student.setId(result.getInt(1));
                student.setFirstName(result.getString(2));
                student.setLastName(result.getString(3));
                student.setMobile(result.getString(4));
                student.setEmail(result.getString(5));
                student.setGender(result.getString(6).charAt(0));
                students.add(student);
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return students;

    }

    private boolean  saveQuizResultDetails(Map<Question , String> userAnswers){
        return QuizResultDetails.saveQuizResultDetails(this , userAnswers);
    }


    public static List<QuizResult> getResult(Student student){
        Map<QuizResult , Quiz > quizResultQuizMap = getQuizzes(student);
        return  new ArrayList(quizResultQuizMap.keySet());
    }

}
