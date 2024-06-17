
package gestionEtudiants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static gestionEtudiants.App.loadFXML;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;



public class AccueilController implements Initializable {

    private Stage stage;
    private Scene scene;
   
    
   
     public void switchToAddStudent(ActionEvent event) throws IOException{
        scene = new Scene(loadFXML("AddStudent"), 640, 480);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
     
     public void switchToUpdateStudent(ActionEvent event) throws IOException{
        scene = new Scene(loadFXML("UpdateStudent"), 640, 480);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
     public void switchToDeleteStudent(ActionEvent event) throws IOException{
        scene = new Scene(loadFXML("DeleteStudent"), 640, 480);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToViewStudent(ActionEvent event) throws IOException{
        scene = new Scene(loadFXML("ViewStudents"), 640, 480);
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
