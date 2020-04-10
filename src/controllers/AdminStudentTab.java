package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class AdminStudentTab {

    private VBox formContainer;
    private JFXTextField firstName;
    private JFXTextField lastName;
    private JFXTextField mobileNumber;
    private JFXCheckBox male;
    private JFXCheckBox female;
    private JFXButton saveButton;
    private TableView studentTable;
    private TableColumn studentIdColumn;
    private TableColumn firstNameColumn;
    private TableColumn lastNameColumn;
    private TableColumn mobileNumberColumn;
    private TableColumn genderColumn;

    public void saveStudent(MouseEvent mouseEvent) {
        System.out.println("Save Button Clicked...");
    }
}
