package models;

import constants.DatabaseConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;
import java.util.Set;

public class QuizResultDetails {

    private Integer id;
    private Question question;
    private  String userAnswer ;
    private QuizResult quizResult;

    public static  class MetaData{
        public static final String TABLE_NAME ="QUIZ_RESULT_DETAILS";
        public static final String ID ="ID";
        public static final String USER_ANSWER ="USER_ANSWER";
        public static final String QUESTION_ID ="QUESTION_ID";
        public static final String QUIZ_RESULT_ID ="QUIZ_RESULT_ID";

    }


    public QuizResultDetails(Integer id, Question question, String userAnswer, QuizResult quizResult) {
        this.id = id;
        this.question = question;
        this.userAnswer = userAnswer;
        this.quizResult = quizResult;
    }

    public QuizResultDetails(Question question, String userAnswer, QuizResult quizResult) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.quizResult = quizResult;
    }

    public QuizResultDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public QuizResult getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(QuizResult quizResult) {
        this.quizResult = quizResult;
    }



    public static void createTable(){

        String raw = "   CREATE table %s(\n" +
                "        %s INTEGER NOT null PRIMARY key  AUTOINCREMENT,\n" +
                "        %s int not null ,\n" +
                "        %s int NOT NULL ,\n" +
                "        %s varchar(200) not null ,\n" +
                "        FOREIGN KEY (%s) REFERENCES %s(%s),\n" +
                "        FOREIGN KEY (%s) REFERENCES questions(id)\n" +
                "        )";
        String query  = String.format(raw,
                MetaData.TABLE_NAME ,
                MetaData.ID,
                MetaData.QUIZ_RESULT_ID,
                MetaData.QUESTION_ID,
                MetaData.USER_ANSWER,
                MetaData.QUIZ_RESULT_ID,
                QuizResult.MetaData.TABLE_NAME,
                QuizResult.MetaData.ID,
                MetaData.QUESTION_ID,
                Question.MetaData.TABLE_NAME,
                Question.MetaData.QUESTION_ID
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

    public static boolean saveQuizResultDetails(QuizResult quizResult , Map<Question , String> userAnswers){
        String raw = "INSERT INTO QUIZ_RESULT_DETAILS (QUIZ_RESULT_ID,QUESTION_ID,USER_ANSWER) VALUES (\n" +
                "? , ?  , ? )";
        String query  = String.format(raw,
                MetaData.TABLE_NAME ,
                MetaData.QUIZ_RESULT_ID ,
                MetaData.QUESTION_ID ,
                MetaData.USER_ANSWER

        );

        System.err.println(query);
        try{
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);

            Set<Question> questions  = userAnswers.keySet();
            for(Question question : questions){
                ps.setInt(1 , quizResult.getId());
                ps.setInt(2 , question.getQuestionId());
                ps.setString(3 , userAnswers.get(question));
               ps.addBatch();

            }


           int[] result  =  ps.executeBatch();
            if(result.length > 0){
                return true;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }

        return false;
    }


}
