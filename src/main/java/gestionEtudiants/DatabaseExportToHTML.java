package gestionEtudiants;

import com.itextpdf.text.Font;

import com.itextpdf.text.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseExportToHTML {

    public static void main(String[] args) {
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
            PreparedStatement ps = null;
            ResultSet rs = null;

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
            e.printStackTrace();
        }
    }
}
