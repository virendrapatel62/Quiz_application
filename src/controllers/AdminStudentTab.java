package controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Student;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminStudentTab implements Initializable {


    @FXML private JFXPasswordField password;
    @FXML private JFXTextField email;
    @FXML
    private VBox formContainer;
    @FXML
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField mobileNumber;
    @FXML private JFXRadioButton male;
    @FXML private JFXRadioButton female;
    @FXML private JFXButton saveButton;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student , String> studentIdColumn;
    @FXML private TableColumn<Student , String> firstNameColumn;
    @FXML private TableColumn<Student , String> lastNameColumn;
    @FXML private TableColumn<Student , String> mobileNumberColumn;
    @FXML private TableColumn<Student , Character> genderColumn;
    @FXML private TableColumn<Student , String> emailColumn;
    @FXML private TableColumn<Student , String> passwordColumn;

//     Non FXML Varibales
    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAll();
        radioButtonSetup();
        renderTable();
    }

    private void renderTable() {
        List<Student> students = Student.getAll();
        studentTable.getItems().clear();

        this.studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.mobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        studentTable.getItems().addAll(students);
    }


    private void radioButtonSetup(){
        this.male.setSelected(true);
        this.male.setToggleGroup(toggleGroup);
        this.female.setToggleGroup(toggleGroup);
    }

    private void initAll(){
        toggleGroup = new ToggleGroup();
    }

    private void resetForm(){
        this.password.clear();
        this.email.clear();
        this.firstName.clear();
        this.lastName.clear();
        this.mobileNumber.clear();
    }

    private String validate(Student student){
        String message = null;
        Pattern p = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");


        if(student.getFirstName().length() < 4){
            message = "First Name must be more then 4 char long";
        }else if(student.getLastName().length() < 2){
            message = "Last Name must be more than 2 char long";

        }else if(!p.matcher(student.getEmail()).matches()){
            message = "Please Enter Valid Email..";
        }else if(student.getPassword().length() <= 6){
            message = "password must be more than 6 char long";
        }
        else if(student.getMobile().length() < 10){
            message  = "Enter valid contact number ";
        }
        return message;
    }

    public void saveStudent(ActionEvent actionEvent) {
        System.out.println("Button Clicked...");

        String firstName = this.firstName.getText().trim();
        String lastName = this.lastName.getText().trim();
        String mobile = this.mobileNumber.getText().trim();
        String email = this.email.getText().trim();
        String password = this.password.getText().trim();
        Character gen = 'M';
        JFXRadioButton gender =(JFXRadioButton)toggleGroup.getSelectedToggle();

        if(gender != null){
            if(gender == female){
                gen = 'F';
            }
        }

        String message = null;

        Student s = new Student(
                firstName ,
                lastName ,
                mobile ,
                gen ,
                email ,
                password
        );
        message = this.validate(s);

        if(message != null ){
            Notifications.create()
                    .title("Fill Student Form Correctly")
                    .text(message)
                    .showWarning();

            return;
        }

        System.out.println(s);

        if(s.isExists()){
            Notifications.create()
                    .title("Failed..")
                    .text("Student Already Registered...")
                    .showError();

            return;
        }


        s = s.save();

        if(s != null){
            Notifications.create().text("Student Registered..")
                    .title("Success")
                    .position(Pos.TOP_RIGHT)
                    .showInformation();
            this.resetForm();

            studentTable.getItems().add(0 , s);
        }else{
            Notifications.create().text("Student Registation Failed...")
                    .title("Failed..")
                    .position(Pos.TOP_RIGHT)
                    .showError();
        }
    }

}
