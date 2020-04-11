package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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

public class AdminStudentTab implements Initializable {

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

        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String mobile = this.mobileNumber.getText();
        String stringGender = "Male";
        JFXRadioButton gender =(JFXRadioButton)toggleGroup.getSelectedToggle();
        if(gender != null){
            if(gender == female){
                stringGender = "Female";
            }
        }

        String message = null;

        if(firstName.length() < 4){
            message = "First Name must be more then 4 char long";
        }else if(lastName.length() < 2){
            message = "Last Name must be more than 2 char long";
        }else if(mobile.length() < 10){
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
