package models;

import constants.DatabaseConstants;

import java.sql.*;
import java.util.*;

public class Quiz {
    // Properties

    private Integer quizId;
    private String title;

    // Constructers
    public Quiz(String title) {
        this.title = title;
    }

    public static class MetaData {

        public static final String TABLE_NAME = "QUIZZES";
        public static final String QUIZ_ID = "ID";
        static final String TITLe = "TITLE";

    }

    public Quiz() {
    }

    // Getter Setters
    public Integer getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    // Other Methods
    public static void createTable() {
        try {
            String raw = "CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50) );";
            String query = String.format(raw, MetaData.TABLE_NAME, MetaData.QUIZ_ID, MetaData.TITLe);
            System.err.println(query);
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.Quiz.createTable()");
            System.out.println(b);
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int save() {
        String raw = "Insert into %s (%s) values (?)";
        String query = String.format(raw, MetaData.TABLE_NAME, MetaData.TITLe);
        String connectionUrl = DatabaseConstants.CONNECTION_URL;

        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, this.title);
                int i = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

        return -1;
    }

    public boolean save(List<Question> questions) {
        boolean flag = true;
        this.quizId = this.save();

        for (Question q : questions) {
            flag = flag && q.save();
            System.out.println(flag);
        }
        return flag;
    }

    public static Map<Quiz, List<Question>> getAll() {
        Map<Quiz, List<Question>> quizes = new HashMap<>();
        Quiz key = null;

        String query = String.
                format("SELECT %s.%s , %s  ," +
                                " %s.%s , %s , " +
                                "%s , %s ," +
                                " %s , %s , \n" +
                                "%s\n" +
                                "FROM %s join %s on %s.%s = %s.%s",
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID,
                        MetaData.TITLe,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.QUESTION_ID,

                        Question.MetaData.QUESTION,
                        Question.MetaData.OPTION1,
                        Question.MetaData.OPTION2,
                        Question.MetaData.OPTION3,
                        Question.MetaData.OPTION4,
                        Question.MetaData.ANSWER,
                        MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.QUIZ_ID,
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID
                );
        String connectionUrl = DatabaseConstants.CONNECTION_URL;
        System.out.println(query);
        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                    Quiz temp = new Quiz();
                    temp.setQuizId(result.getInt(1));
                    temp.setTitle(result.getString(2));

                    Question tempQuestion = new Question();
                    tempQuestion.setQuestionId(result.getInt(3));
                    tempQuestion.setQuestion(result.getString(4));
                    tempQuestion.setOption1(result.getString(5));
                    tempQuestion.setOption2(result.getString(6));
                    tempQuestion.setOption3(result.getString(7));
                    tempQuestion.setOption4(result.getString(8));
                    tempQuestion.setAnswer(result.getString(9));

                    if (key != null && key.equals(temp)) {
                        quizes.get(key).add(tempQuestion);
                    } else {
                        ArrayList<Question> value = new ArrayList<>();
                        value.add(tempQuestion);
                        quizes.put(temp, value);
                    }

                    key = temp;

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return quizes;
    }


    public static Map<Quiz, Integer> getAllWithQuestionCount() {
        Map<Quiz, Integer> quizes = new HashMap<>();
        Quiz key = null;

        String query = String.
                format("SELECT %s.%s , %s  ," +
                                " COUNT(*) as question_count  " +

                                "FROM %s join %s on %s.%s = %s.%s GROUP BY %s.%s",
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID,
                        MetaData.TITLe,
                        MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.QUIZ_ID,
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID,
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID
                );
        String connectionUrl = DatabaseConstants.CONNECTION_URL;
        System.out.println(query);
        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                    Quiz temp = new Quiz();
                    temp.setQuizId(result.getInt(1));
                    temp.setTitle(result.getString(2));
                    int count = result.getInt(3);
                    quizes.put(temp, count);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return quizes;
    }

    //    get questions Using Quiz
    public List<Question> getQuestions() {
        List<Question> quizes = new ArrayList<>();

        String query = String.
                format("SELECT " +
                                " %s , %s , " +
                                "%s , %s ," +
                                " %s , %s , \n" +
                                "%s\n" +
                                "FROM %s  where %s = ?",

                        Question.MetaData.QUESTION_ID,
                        Question.MetaData.QUESTION,
                        Question.MetaData.OPTION1,
                        Question.MetaData.OPTION2,
                        Question.MetaData.OPTION3,
                        Question.MetaData.OPTION4,
                        Question.MetaData.ANSWER,

                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.QUIZ_ID

                );
        String connectionUrl = DatabaseConstants.CONNECTION_URL;
        System.out.println(query);
        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, this.quizId);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                    Question tempQuestion = new Question();
                    tempQuestion.setQuestionId(result.getInt(1));
                    tempQuestion.setQuestion(result.getString(2));
                    tempQuestion.setOption1(result.getString(3));
                    tempQuestion.setOption2(result.getString(4));
                    tempQuestion.setOption3(result.getString(5));
                    tempQuestion.setOption4(result.getString(6));
                    tempQuestion.setAnswer(result.getString(7));
                    tempQuestion.setQuiz(this);
                    quizes.add(tempQuestion);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return quizes;
    }


    public Integer getNumberOfQuestions() {
        String raw = "SELECT count(*) from %s WHERE %s = ?";
        String query = String.format(raw, Question.MetaData.TABLE_NAME
                , Question.MetaData.QUIZ_ID
        );


        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager

                    .getConnection(DatabaseConstants.CONNECTION_URL);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, this.quizId);
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



    public static Map<Quiz , Integer> getStudentCount() {
        Map<Quiz , Integer> data = new HashMap<>();
        String raw = "SELECT QUIZZES.ID , \n" +
                "QUIZZES.title  , \n" +
                "count(*) ,\n" +
                "QUIZ_RESULTS.id\n" +
                "FROM QUIZZES Left Join \n" +
                "QUIZ_RESULTS on \n" +
                "QUIZ_RESULTS.quiz_id = QUIZZES.ID \n" +
                "GROUP BY QUIZZES.id";
        String query = String.format(
                raw,
                MetaData.TABLE_NAME,
                MetaData.QUIZ_ID,

                MetaData.TABLE_NAME,
                MetaData.TITLe,

                QuizResult.MetaData.TABLE_NAME ,
                QuizResult.MetaData.ID,

                MetaData.TABLE_NAME,
                QuizResult.MetaData.TABLE_NAME,

                QuizResult.MetaData.TABLE_NAME,
                QuizResult.MetaData.QUIZ_ID,

                MetaData.TABLE_NAME,
                MetaData.QUIZ_ID,

                MetaData.TABLE_NAME,
                MetaData.QUIZ_ID

                );
        try {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager

                    .getConnection(DatabaseConstants.CONNECTION_URL);

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Quiz  quiz = new Quiz();
                quiz.setQuizId(result.getInt(1));
                quiz.setTitle(result.getString(2));
                int count = 0 ;
                Integer resultID = result.getInt(4);
                if(resultID > 0){
                    count = result.getInt(3);
                }
                data.put(quiz , count);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj == null)
                return false;

        if(!(obj instanceof Quiz))
            return  false;
        Quiz t = (Quiz)obj;

        if(this.quizId == t.quizId){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, title);
    }
}
