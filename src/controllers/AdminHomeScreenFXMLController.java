
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
public class AdminHomeScreenFXMLController implements Initializable {

    public Tab dashboard;
    @FXML
    private TabPane adminTabPane;
    @FXML
    private Tab addQuizTab;
            @FXML
            private Tab addStudentTab;
            @Override
            public void initialize(URL url, ResourceBundle rb) {
                // TODO
                try {
                    Parent node = FXMLLoader.load(getClass().getResource("/fxml/AddQuizFXML.fxml"));
                    addQuizTab.setContent(node);


                    Parent studentTabNode = FXMLLoader.load(getClass()
                            .getResource("/fxml/AdminStudentTab.fxml"));
                    addStudentTab.setContent(studentTabNode);


                    Parent dash = FXMLLoader.load(getClass()
                    .getResource("/fxml/AdminDashboardFXML.fxml"));
            dashboard.setContent(dash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
}
