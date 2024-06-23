package gestionEtudiants;

import static gestionEtudiants.App.loadFXML;
import gestionEtudiants.data.Queries;
import gestionEtudiants.model.Student;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateStudentController  implements Initializable {

    @FXML
    private ComboBox<String> ageChoiceBox;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_update;
    
    @FXML
    private Button btn_search;

    @FXML
    private TableColumn<Student, Integer> colAge;

    @FXML
    private TableColumn<Student, String> colClass;

    @FXML
    private TableColumn<Student, String> colFirstname;

    @FXML
    private TableColumn<Student, String> colGender;

    @FXML
    private TableColumn<Student, String> colLastname;

    @FXML
    private TableColumn<Student, String> colMatricule;

    @FXML
    private TableColumn<Student, Integer> colid;

    @FXML
    private TextField fieldClass;

    @FXML
    private TextField fieldFirstname;

    @FXML
    private TextField fieldLastname;

    @FXML
    private TextField fieldMatricule;

    @FXML
    private TextField fieldSearch;

    @FXML
    private ComboBox<String> genderChoiceBox;

    @FXML
    private Label LabelGenderHidden;

    @FXML
    private Label LabelAgeHidden;

    @FXML
    private TableView<Student> tableView;

    private Student student;

    private final String[] gender = {"Female","Male"};

    //method to get gender
    public void getGender(ActionEvent event) {
        String myGender = genderChoiceBox.getValue();
        LabelGenderHidden.setText(myGender);
    }

    //method to get age
    public void getAge(ActionEvent event) {
        String myAgeString = ageChoiceBox.getValue();

        try {
            int myAge = Integer.parseInt(myAgeString);
            LabelAgeHidden.setText(String.valueOf(myAge));
        } catch (NumberFormatException e) {
            // Gestion de l'erreur si la valeur de l'âge n'est pas un entier
            LabelAgeHidden.setText("Invalid age");
        }

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

    @FXML
    public void showStudents() {
        Queries query = new Queries();
        ObservableList<Student> list = query.getStudentList();
        colid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colMatricule.setCellValueFactory(new PropertyValueFactory<Student, String>("Matricule"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<Student, String>("Firstname"));
        colLastname.setCellValueFactory(new PropertyValueFactory<Student, String>("Lastname"));
        colAge.setCellValueFactory(new PropertyValueFactory<Student, Integer>("Age"));
        colGender.setCellValueFactory(new PropertyValueFactory<Student, String>("Gender"));
        colClass.setCellValueFactory(new PropertyValueFactory<Student, String>("Classe"));
        tableView.setItems(list);
    }

    //search method
    @FXML
    private void filterData(String searchName) {
        ObservableList<Student> filterData = FXCollections.observableArrayList();
        Queries query = new Queries();
        ObservableList<Student> list = query.getStudentList();
        for (Student student : list) {
            if (student.getMatricule().toLowerCase().contains(searchName.toLowerCase())) {
                filterData.add(student);
            }
        }
        tableView.setItems(filterData);
    }
  
    @FXML
    private void searchStudent(){
        String searchName = fieldSearch.getText();
        filterData(searchName);
    }
    
    //method to sElect a student
    @FXML

    public void mouseClicked(MouseEvent e) {
        try {
            Student student = tableView.getSelectionModel().getSelectedItem();
            student = new Student(student.getId(), student.getMatricule(), student.getFirstname(), student.getLastname(), student.getAge(), student.getGender(), student.getClasse());
            this.student = student;
            fieldMatricule.setText(student.getMatricule());
            fieldFirstname.setText(student.getFirstname());
            fieldLastname.setText(student.getLastname());
           
            fieldClass.setText(student.getClasse());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
      @FXML
    public void updateStudent() {
        try {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update confirmation");
            dialog.setHeaderText(" Are you sure you want to update this student?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            Label label = new Label("Name:" + fieldFirstname.getText() + " " + fieldLastname.getText());

            dialog.getDialogPane().setContent(label);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                Queries query = new Queries();
                Student students = new Student(this.student.getId(),fieldMatricule.getText(), fieldFirstname.getText(), fieldLastname.getText(),LabelAgeHidden.getText(),LabelGenderHidden.getText(),fieldClass.getText());
                query.updateStudent(students);
                showStudents();
                clearFields();
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

     private void clearFields() {
        fieldMatricule.setText("");
        fieldFirstname.setText("");
        fieldLastname.setText("");
        fieldClass.setText("");
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("WelcomeScreen2"), 700, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents();

//        fieldSearch.textProperty().addListener((ObservableList, oldValue, newValue) -> {
//            filterData(newValue);
//        });
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
