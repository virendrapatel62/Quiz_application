package controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import java.net.URL;
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
    @FXML private TableView studentTable;
    @FXML private TableColumn studentIdColumn;
    @FXML private TableColumn firstNameColumn;
    @FXML private TableColumn lastNameColumn;
    @FXML private TableColumn mobileNumberColumn;
    @FXML private TableColumn genderColumn;

//     Non FXML Varibales
    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAll();
        radioButtonSetup();
    }


    private void radioButtonSetup(){
        this.male.setSelected(true);
        this.male.setToggleGroup(toggleGroup);
        this.female.setToggleGroup(toggleGroup);
    }

    private void initAll(){
        toggleGroup = new ToggleGroup();
    }

    public void saveStudent(ActionEvent actionEvent) {
        System.out.println("Button Clicked...");

        String firstName = this.firstName.getText().trim();
        String lastName = this.lastName.getText().trim();
        String mobile = this.mobileNumber.getText().trim();
        String email = this.email.getText().trim();
        String password = this.password.getText().trim();
        String stringGender = "Male";
        JFXRadioButton gender =(JFXRadioButton)toggleGroup.getSelectedToggle();

        if(gender != null){
            if(gender == female){
                stringGender = "Female";
            }
        }

        String message = null;
        Pattern p = Pattern.compile("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");


        if(firstName.length() < 4){
            message = "First Name must be more then 4 char long";
        }else if(lastName.length() < 2){
            message = "Last Name must be more than 2 char long";

        }else if(!p.matcher(email).matches()){
            message = "Please Enter Valid Email..";
        }else if(password.length() <= 6){
            message = "password must be more than 6 char long";
        }
        else if(mobile.length() < 10){
            message  = "Enter valid contact number ";
        }

        if(message != null ){
            Notifications.create()
                    .title("Fill Student Form Correctly")
                    .text(message)
                    .showError();

            return;
        }

        // save code will be here
        System.out.println(firstName  + "\n " +
                lastName + "\n " +
                mobile + "\n " +
                stringGender);
    }

}
