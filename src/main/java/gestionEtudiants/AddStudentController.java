
package gestionEtudiants;

import static gestionEtudiants.App.loadFXML;
import gestionEtudiants.data.DBConnection;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import gestionEtudiants.model.Student;
import gestionEtudiants.data.Queries;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddStudentController implements Initializable {

    @FXML
    private ComboBox<String> ageChoiceBox;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_save;

    @FXML
    private TextField fieldClasse;

    @FXML
    private TextField fieldFirstname;

    @FXML
    private TextField fieldLastname;

    @FXML
    private TextField fieldMatricule;

    @FXML
    private ComboBox<String> genderChoiceBox;

    @FXML
    private Label LabelGenderHidden;

    @FXML
    private Label LabelAgeHidden;

    private final String[] gender = {"Male", "Female"};

    //method to get gender
    public void getGender(ActionEvent event) {
        String myGender = genderChoiceBox.getValue();
        LabelGenderHidden.setText(myGender);
        // if (genderChoiceBox.getText().equalsIgnoreCase("male")){}

    }

    //method to get age
    public void getAge(ActionEvent event) {
        String myAgeString = ageChoiceBox.getValue();
         LabelAgeHidden.setText(String.valueOf(myAgeString));
       

    }


    //method to add Student
    @FXML
    public void addStudent() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add confirmation");
        dialog.setHeaderText(" Are you sure you want to add a new student?");
        dialog.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label("Name: " + fieldFirstname.getText() + " " + fieldLastname.getText());

        dialog.getDialogPane().setContent(label);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            
             // if (CheckStudentIDPresent() == true) {
//                dialog = new Dialog<>();
//                label = new Label( "Ce Matricule existe déjà. ");
//            dialog.getDialogPane().setContent(label);
          //  } else{ 
               //int age = Integer.parseInt(LabelAgeHidden.getText());

            Student student = new Student(fieldMatricule.getText(), fieldFirstname.getText(), fieldLastname.getText(), LabelAgeHidden.getText(), LabelGenderHidden.getText(), fieldClasse.getText());
            Queries query = new Queries();
            query.addStudent(student);
        }

           

       // }
    }

    //CLEAR METHOD
    private void clearFields() {
        fieldMatricule.setText("");
        fieldFirstname.setText("");
        fieldLastname.setText("");
        fieldClasse.setText("");
        LabelAgeHidden.setText("");
        LabelGenderHidden.setText("");
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("WelcomeScreen2"), 700, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clickClearButton() {
        clearFields();
    }

      @FXML
    public void closeApp(){
    System.exit(0);
    }
    
    @FXML
    private void MinimizeApp(ActionEvent event) {
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // we initialise the combo box with valuies in the array for gender 
        genderChoiceBox.getItems().addAll(gender);
        genderChoiceBox.setOnAction(this::getGender);

        //we initialise age 
        for (int age = 15; age <= 100; age++) {
            ageChoiceBox.getItems().add(String.valueOf(age));
        }

        ageChoiceBox.setOnAction(this::getAge);

    }

}
