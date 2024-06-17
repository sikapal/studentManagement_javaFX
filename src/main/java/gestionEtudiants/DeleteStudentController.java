/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteStudentController implements Initializable {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_delete;

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
    private TextField fieldSearch;

    @FXML
    private TextField fieldFirstname;

    @FXML
    private TextField fieldClass;

    @FXML
    private TextField fieldLastname;

    @FXML
    private TextField fieldMatricule;

    @FXML
    private TableView<Student> tableView;

    Student student;

    UpdateStudentController update = new UpdateStudentController();

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

    @FXML
    public void deleteStudent() {
        try {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Delete confirmation");
            dialog.setHeaderText(" Are you sure you want to delete a  student?");
            dialog.initModality(Modality.APPLICATION_MODAL);
            Label label = new Label("Name:" + colFirstname.getText() + "" + colLastname.getText());

            dialog.getDialogPane().setContent(label);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                Queries query = new Queries();
                Student student = new Student(this.student.getId(), colMatricule.getText(), colFirstname.getText(), colLastname.getText(), colAge.getText(), colGender.getText(), colClass.getText());
                query.deleteStudent(student);

                showStudents();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents();
    }

}
