package gestionEtudiants;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static gestionEtudiants.App.loadFXML;
import gestionEtudiants.data.DBConnection;
import gestionEtudiants.data.Queries;
import gestionEtudiants.model.Student;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        Scene scene = new Scene(loadFXML("WelcomeScreen2"), 700, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showStudents() {
        Queries query = new Queries();
        ObservableList<Student> list = query.getStudentList();
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMatricule.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("Firstname"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("Lastname"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("Classe"));
        tableView.setItems(list);
    }

    //search method
    @FXML
    private void filterData(String searchName) {
        ObservableList<Student> filterData = FXCollections.observableArrayList();
        Queries query = new Queries();
        ObservableList<Student> list = query.getStudentList();
        for (Student studente : list) {
            if (studente.getMatricule().toLowerCase().contains(searchName.toLowerCase())) {
                filterData.add(studente);
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
            Student studente = tableView.getSelectionModel().getSelectedItem();
            studente = new Student(studente.getId(), studente.getMatricule(), studente.getFirstname(), studente.getLastname(), studente.getAge(), studente.getGender(), studente.getClasse());
            this.student = studente;
            this.student.getMatricule();

        } catch (Exception ex) {
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
    public void DatabaseExportUniqueToPDF() {
        try {
            String fileName = "C:\\Users\\HP\\Desktop\\javafx\\studentUnique_Data.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Add image in pdf
            document.add(new Paragraph(" "));
            Image image = Image.getInstance("C:\\Users\\HP\\Documents\\NetBeansProjects\\GestionEtudiants\\src\\main\\resources\\images\\supptic.png");
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
            document.add(new Paragraph(""));

            // Add title heading
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph para = new Paragraph("Student Data From Database", boldFont);
            para.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(new Paragraph(" "));
            document.add(para);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // Add database elements
            String url = "jdbc:mysql://localhost:3306/gestionetudiant";
            String username = "root";
            String password = "";

            DBConnection dbConnection = new DBConnection();
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM student WHERE id = ?";

            ps = connection.prepareStatement(query);
            ps.setInt(1, student.getId()); // Use the selected student's ID
            rs = ps.executeQuery();

            PdfPTable table = new PdfPTable(6);

            PdfPCell c1 = new PdfPCell(new Phrase("Matricule"));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("FirstName"));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("LastName"));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("AGE"));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Gender"));
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Classe"));
            table.addCell(c1);

            table.setHeaderRows(1);

            if (rs.next()) {
                table.addCell(rs.getString("matricule"));
                table.addCell(rs.getString("firstname"));
                table.addCell(rs.getString("lastname"));
                table.addCell(rs.getString("age"));
                table.addCell(rs.getString("gender"));
                table.addCell(rs.getString("classe"));
            } else {
                // Handle case where no student was found with the given ID
                document.add(new Paragraph("No student found with ID: " + student.getId()));
            }

            document.add(table);
            document.close();

        } catch (DocumentException | IOException | SQLException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showStudents();
    }

}
