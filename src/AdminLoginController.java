
import constants.AdminEmailPassword;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class AdminLoginController implements Initializable {
    @FXML
    private TextField adminEmail;
    @FXML
    private PasswordField adminPassword;
    @FXML
    private Button adminLoginButton;
    @FXML
    private TextField studentEmail;
    @FXML
    private PasswordField studentPassword;
    @FXML
    private Button studentLoginButton;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void loginAdmin(ActionEvent event) {
        String email = adminEmail.getText();
        String password = adminPassword.getText();
        if(email.trim().equalsIgnoreCase(AdminEmailPassword.email) && password.trim().equalsIgnoreCase(AdminEmailPassword.password)){
            
            try {
                Parent root = FXMLLoader.load(getClass().getResource("fxml/AdminHomeScreenFXML.fxml"));
                Stage stage = (Stage)studentPassword.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
            
            System.out.println("Admin Login Success");
        }else{
            System.out.println("Admin Login Failed.");
        }
        System.out.println("AdminLoginController.loginAdmin()");
    }

    @FXML
    private void loginStudent(ActionEvent event) {
        System.out.println("AdminLoginController.loginStudent()");
        
    }
    
}
