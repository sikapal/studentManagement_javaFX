
package gestionEtudiants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DatabaseToExcel {
    public static void main(String[] args) {
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
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Matricule");
            headerRow.createCell(2).setCellValue("First Name");
            headerRow.createCell(3).setCellValue("Last Name");
            headerRow.createCell(4).setCellValue("Age");
            headerRow.createCell(5).setCellValue("Gender");
            headerRow.createCell(6).setCellValue("Class");

            // Write the data to the Excel sheet
            int rowCount = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(resultSet.getInt("id"));
                row.createCell(1).setCellValue(resultSet.getString("matricule"));
                row.createCell(2).setCellValue(resultSet.getString("firstname"));
                row.createCell(3).setCellValue(resultSet.getString("lastname"));
                row.createCell(4).setCellValue(resultSet.getInt("age"));
                row.createCell(5).setCellValue(resultSet.getString("gender"));
                row.createCell(6).setCellValue(resultSet.getString("classe"));
            }

            // Save the Excel file
            String fileName = "student_data.xlsx";
            Path filePath = Paths.get("C:\\Users\\HP\\Desktop\\javafx", fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());
            workbook.write(fileOutputStream);
            fileOutputStream.close();

            System.out.println("Data exported succesfully " );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}