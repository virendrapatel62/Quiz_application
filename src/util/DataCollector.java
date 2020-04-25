package util;

import models.Question;
import models.Quiz;
import models.Student;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataCollector {
    public static void main(String[] args) throws  Exception {
        Quiz.createTable();
        Question.createTable();
        Student.createTable();
        readAndSaveQuizzesData();
        readAndSaveUserData();
    }



    public static  void readAndSaveQuizzesData() throws  Exception{
        String folderPath = "src/util/sample_data/quizzes";

        File folder = new File(folderPath);
        String[] fileNames = folder.list();
        for(String filename : fileNames) {
            System.out.println(filename);


        File file = new File(folder + "/" + filename);
        FileReader  fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();
        String line ;
        while((line = bufferedReader.readLine()) != null){

            stringBuilder.append(line);
        }

        System.out.println(stringBuilder);

//
        JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        JSONArray result = jsonObject.getJSONArray("results");
        Quiz quiz = new Quiz();
        List<Question> questions = new ArrayList<>();
        for(int i = 0 ; i < result.length() ; i ++){
            String objString =result.get(i).toString();
            JSONObject object = new JSONObject(objString);
            Question question = new Question();

            quiz.setTitle(object.getString("category"));
            question.setQuestion(object.getString("question"));
            JSONArray incorrectOptions = object.getJSONArray("incorrect_answers");
            question.setOption1(incorrectOptions.get(0).toString());
            question.setOption2(incorrectOptions.get(1).toString());
            question.setOption3(incorrectOptions.get(2).toString());
            question.setOption4(object.getString("correct_answer"));
            question.setAnswer(object.getString("correct_answer"));
            questions.add(question);
            question.setQuiz(quiz);
            System.out.println(question);
            System.out.println(quiz);
        }
        quiz.save(questions);
    }


    }


    public static  void readAndSaveUserData() throws  Exception{

            // reading file
            File file = new File("src/util/sample_data/users.json");
            FileReader  fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line ;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            System.out.println(stringBuilder);

            // parsing Json
            JSONArray result = new JSONArray(stringBuilder.toString());
            for(int i = 0 ; i < result.length() ; i ++){
                String objString =result.get(i).toString();

                JSONObject object = new JSONObject(objString);
                Student student = new Student();
                student.setFirstName(object.getString("firstName"));
                student.setLastName(object.getString("lastName"));
                student.setEmail(object.getString("email"));
                student.setPassword(object.getInt("password") + "");
                student.setMobile(object.getInt("phone") + "");

                student.setGender('M');

                // saving Object
                student.save();
            }



    }
}



