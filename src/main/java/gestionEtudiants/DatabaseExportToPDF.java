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
import gestionEtudiants.data.DBConnection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseExportToPDF {

    public static void main(String[] args) {
        try {
            String fileName = "C:\\Users\\HP\\Desktop\\javafx\\student_data.pdf";
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
            PreparedStatement ps ;
            ResultSet rs ;

            String query = "SELECT * FROM student";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
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
            
            
            table.addCell(rs.getString("matricule"));
            table.addCell(rs.getString("firstname"));
            table.addCell(rs.getString("lastname"));
            table.addCell(rs.getString("age"));
            table.addCell(rs.getString("gender"));
            table.addCell(rs.getString("classe"));

            document.add(table);
   
            }

      
           
            document.close();

        } catch (DocumentException | IOException | SQLException e) {
           
        }
    }

}
