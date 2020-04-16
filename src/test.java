import models.Student;

public class test {
    public static void main(String[] args) {
//       Student.createTable();

        new Student("virendra2" , "patel" , "91656566" , 'M'  , "virendra62@gmail.com" , "545664656")
                .save();
        new Student("virendra3" , "patel" , "91656566" , 'M'  , "virendra62@gmail.com" , "545664656")
                .save();
    }
}
