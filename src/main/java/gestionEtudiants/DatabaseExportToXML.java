
package gestionEtudiants;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DatabaseExportToXML {

    public static void main(String[] args) {
        try {
            String fileName = "C:\\Users\\HP\\Desktop\\javafx\\student_data.xml";
            
            // Create a new XML document
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            // Create the root element
            Element root = doc.createElement("students");
            doc.appendChild(root);
            
            String url = "jdbc:mysql://localhost:3306/gestionetudiant";
            String username = "root";
            String password = "";
            
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps ;
            ResultSet rs ;
            
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
}