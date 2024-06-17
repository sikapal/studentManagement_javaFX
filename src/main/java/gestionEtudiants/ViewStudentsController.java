
package gestionEtudiants;

import static gestionEtudiants.App.loadFXML;
import gestionEtudiants.data.Queries;
import gestionEtudiants.model.Student;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ViewStudentsController implements Initializable {
    
    @FXML
    private Button btn_back;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_search;

    @FXML
    private TableColumn<Student, String> colAge;

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
    private TextField fieldSearch;

   
    @FXML
    private TableView<Student> tableView;
    private Student student;

     public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("WelcomeScreen"), 640, 480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showStudents() {
        Queries query = new Queries();
        ObservableList<Student> list = query.getStudentList();
        colid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        colMatricule.setCellValueFactory(new PropertyValueFactory<Student, String>("Matricule"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<Student, String>("Firstname"));
        colLastname.setCellValueFactory(new PropertyValueFactory<Student, String>("Lastname"));
        colAge.setCellValueFactory(new PropertyValueFactory<Student, String>("Age"));
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
    private void searchStudent() {
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
//            colMatricule.setText(student.getMatricule());
//            colFirstname.setText(student.getFirstname());
//            colLastname.setText(student.getLastname());
//
//            colClass.setText(student.getClasse());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents();
    }    
    
}
