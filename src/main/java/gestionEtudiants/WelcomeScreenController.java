package gestionEtudiants;

import static gestionEtudiants.App.loadFXML;

import java.io.StringWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gestionEtudiants.data.DBConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Element;

public class WelcomeScreenController implements Initializable {

    @FXML
    private ComboBox<String> myChoiceBox;

    @FXML
    private ComboBox<String> myFileComboBox;

    private final String[] Liste = {"New Student", "Update Student", "Delete Student", "View All Students"};
    private final String[] Liste2 = {"Print", "Exit"};

    public void getScreen(ActionEvent event) throws IOException {

        String SelectedScene = myChoiceBox.getSelectionModel().getSelectedItem();
        if (null != SelectedScene) {
            switch (SelectedScene) {
                case "New Student": {
                    Scene scene = new Scene(loadFXML("AddStudent"), 640, 480);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    break;
                }
                case "Update Student": {
                    Scene scene = new Scene(loadFXML("UpdateStudent"), 640, 480);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    break;
                }
                case "Delete Student": {
                    Scene scene = new Scene(loadFXML("DeleteStudent"), 640, 480);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    break;
                }
                case "View All Students": {
                    Scene scene = new Scene(loadFXML("ViewStudents"), 640, 480);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    break;
                }
                default:
                    break;
            }
        }
    }

    public void ClickFile(ActionEvent event) throws IOException {
        String SelectedScene = myFileComboBox.getSelectionModel().getSelectedItem();

        if ("Exit".equals(SelectedScene)) {
                     showCloseConfirmation();
                    switchToLogin(event);


        } else if ("Print".equals(SelectedScene)) {
            showPrintOptions();
        }
    }
    
    public void switchToLogin(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("Login"), 640, 480);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showCloseConfirmation() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Close application");
        alert.setHeaderText("Do you really want to quit this session");
        alert.setContentText("All unsaved data will be lost!");

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                // Handle Option 1
                System.out.println("ok Button Selected");
              //  Platform.exit();

            } else {
                // Handle Cancel
                System.out.println("CancelButton selected");
            }
        });

    }

    private void showPrintOptions() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Export Options");
        alert.setHeaderText("Choose an export option:");

        ButtonType option1 = new ButtonType("PDF");
        ButtonType option2 = new ButtonType("EXCEL");
        ButtonType option3 = new ButtonType("HTML");
        ButtonType option4 = new ButtonType("XML");
        ButtonType cancel = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(option1, option2, option3,option4, cancel);

        alert.showAndWait().ifPresent(type -> {
            if (type == option1) {
                DatabaseExportToPDF();
                System.out.println("PDF selected");
            } else if (type == option2) {
                DatabaseToExcel();
                System.out.println("EXCEL selected");
            } else if (type == option3) {
                DatabaseExportToHTML();
                System.out.println("HTML selected");
            } else if (type == option4) {
                DatabaseExportToXML();
                System.out.println("XML selected");

            } else {
                
                System.out.println("Cancel selected");
            }
        });
    }

    // method to export as HTML
    public void DatabaseExportToHTML() {

        try {
            String fileName = "C:\\Users\\HP\\Desktop\\javafx\\student_data.html";
            StringWriter writer = new StringWriter();

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph para = new Paragraph("Students Data", boldFont);
            para.setAlignment(Paragraph.ALIGN_CENTER);
            writer.write("<h1>" + para.getContent() + "</h1>");

            String url = "jdbc:mysql://localhost:3306/gestionetudiant";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM student";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            // Create an HTML table
            writer.write("<table border=\"1\">");
            writer.write("<tr><th>Matricule</th><th>FirstName</th><th>LastName</th><th>AGE</th><th>Gender</th><th>Classe</th></tr>");

            while (rs.next()) {
                writer.write("<tr>");
                writer.write("<td>" + rs.getString("matricule") + "</td>");
                writer.write("<td>" + rs.getString("firstname") + "</td>");
                writer.write("<td>" + rs.getString("lastname") + "</td>");
                writer.write("<td>" + rs.getString("age") + "</td>");
                writer.write("<td>" + rs.getString("gender") + "</td>");
                writer.write("<td>" + rs.getString("classe") + "</td>");
                writer.write("</tr>");
            }

            writer.write("</table>");

            String html = writer.toString();

            // Write the HTML to a file
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(html.getBytes());
            }

        } catch (SQLException | IOException e) {
            System.out.println("Erreur dans la requete");
        }

    }

    //method to export as pdf
    public void DatabaseExportToPDF() {
        try {
            String fileName = "C:\\Users\\HP\\Desktop\\javafx\\student_Data.pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            //add image in pdf
            document.add(new Paragraph(" "));
            Image image = Image.getInstance("C:\\Users\\HP\\Documents\\NetBeansProjects\\GestionEtudiants\\src\\main\\resources\\images\\supptic.png");
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
            document.add(new Paragraph(""));

            //add title heading
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph para = new Paragraph("Students Data From Database", boldFont);
            para.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(new Paragraph(" "));
            document.add(para);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            //add databse elements
            String url = "jdbc:mysql://localhost:3306/gestionetudiant";
            String username = "root";
            String password = "";

            DBConnection dbConnection = new DBConnection();
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM student";
            ps = connection.prepareStatement(query);
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

            while (rs.next()) {

                table.setHeaderRows(1);

                table.addCell(rs.getString("matricule"));
                table.addCell(rs.getString("firstname"));
                table.addCell(rs.getString("lastname"));
                table.addCell(rs.getString("age"));
                table.addCell(rs.getString("gender"));
                table.addCell(rs.getString("classe"));

            }
            document.add(table);
            document.close();

        } catch (DocumentException | IOException | SQLException e) {

        }

    }

    public void DatabaseToExcel() {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/gestionetudiant";
        String username = "root";
        String password = "";

        // SQL query to retrieve data
        String query = "SELECT * FROM student";

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery(query);

            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Students Data");

            // Add column headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Matricule");

            headerRow.createCell(1).setCellValue("First Name");
            headerRow.createCell(2).setCellValue("Last Name");
            headerRow.createCell(3).setCellValue("Age");
            headerRow.createCell(4).setCellValue("Gender");
            headerRow.createCell(5).setCellValue("Class");

            // Write the data to the Excel sheet
            int rowCount = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowCount++);

                row.createCell(0).setCellValue(resultSet.getString("matricule"));
                row.createCell(1).setCellValue(resultSet.getString("firstname"));
                row.createCell(2).setCellValue(resultSet.getString("lastname"));
                row.createCell(3).setCellValue(resultSet.getInt("age"));
                row.createCell(4).setCellValue(resultSet.getString("gender"));
                row.createCell(5).setCellValue(resultSet.getString("classe"));
            }

            // Save the Excel file
            String fileName = "student_data.xlsx";
            Path filePath = Paths.get("C:\\Users\\HP\\Desktop\\javafx", fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            System.out.println("Data exported succesfully ");
        } catch (Exception e) {

        }

    }

    public void DatabaseExportToXML() {

        try {
            String fileName = "C:\\Users\\HP\\Desktop\\javafx\\student_data.xml";

            // Create a new XML document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.newDocument();

            // Create the root element
            Element root = doc.createElement("students");
            doc.appendChild(root);

            String url = "jdbc:mysql://localhost:3306/gestionetudiant";
            String username = "root";
            String password = "";

            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM student";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            // Populate the XML document with student data
            while (rs.next()) {
                Element student = doc.createElement("student");
                root.appendChild(student);

                Element matricule = doc.createElement("matricule");
                matricule.setTextContent(rs.getString("matricule"));
                student.appendChild(matricule);

                Element firstname = doc.createElement("firstname");
                firstname.setTextContent(rs.getString("firstname"));
                student.appendChild(firstname);

                Element lastname = doc.createElement("lastname");
                lastname.setTextContent(rs.getString("lastname"));
                student.appendChild(lastname);

                Element age = doc.createElement("age");
                age.setTextContent(rs.getString("age"));
                student.appendChild(age);

                Element gender = doc.createElement("gender");
                gender.setTextContent(rs.getString("gender"));
                student.appendChild(gender);

                Element classe = doc.createElement("classe");
                classe.setTextContent(rs.getString("classe"));
                student.appendChild(classe);
            }

            // Write the XML document to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

            System.out.println("XML file generated: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myChoiceBox.getItems().addAll(Liste);
        myFileComboBox.getItems().addAll(Liste2);
    }

}
