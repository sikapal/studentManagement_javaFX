
package gestionEtudiants.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import gestionEtudiants.model.Student;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class Queries {
    
     private  DBConnection c = new DBConnection();
    //Query to add into db
     
     
     public void addStudent(Student student){
        try {
            c.getDBConn();
            try (java.sql.PreparedStatement ps = c.getCon().prepareStatement("insert into student(Matricule,Firstname,Lastname,Age,Gender,Classe)values(?,?,?,?,?,?)")) {
                
                ps.setString(1,student.getMatricule());
                ps.setString(2,student.getFirstname());
                ps.setString(3,student.getLastname());
                ps.setString(4,student.getAge());
                ps.setString(5,student.getGender());
                ps.setString(6,student.getClasse());
                ps.execute();
            }
            DBConnection.closeCOnnection();
        } catch (SQLException e) {
        }
     }
     
     //to get all student from database
       public ObservableList<Student>getStudentList(){
       ObservableList<Student> studentList = FXCollections.observableArrayList();
        try {
            String query="select id, Matricule, Firstname,Lastname, Age,Gender,Classe from student order by id desc";
            c.getDBConn();
            Statement st =c.getCon().createStatement();
            ResultSet rs =st.executeQuery(query);
           Student s;
            
            while(rs.next()){
         s= new Student(rs.getInt("id"),rs.getString("Matricule"),rs.getString("Firstname"),rs.getString("Lastname"),rs.getString("Age"),rs.getString("Gender"),rs.getString("Classe") );
         studentList.add(s);
            
        }
            rs.close();
            st.close();
            DBConnection.closeCOnnection();
            
        } catch (SQLException e) {
           
        }
        return studentList;
    }
     
       //update query
       
          public void updateStudent(Student student){
        try {
            c.getDBConn();
            java.sql.PreparedStatement ps =c.getCon().prepareStatement("UPDATE `student` \n"
                    + "SET \n"
                    + "`Matricule`= ?, \n"
                    + "`Firstname`= ?, \n"
                    + "`Lastname`= ?, \n"
                    + "`Age`= ?, \n"
                    + "`Gender`= ?, \n"
                    + "`Classe`= ? \n"
                    + "WHERE `id`= ? ");
                    
            ps.setString(1, student.getMatricule());
            ps.setString(2, student.getFirstname());
            ps.setString(3, student.getLastname());
             ps.setString(4, student.getAge());
            ps.setString(5, student.getGender());
            ps.setString(6, student.getClasse());
            ps.setInt(7, student.getId());
            
            ps.execute();
            ps.close();
            c.closeCOnnection();
            
                    
                    
        } catch (SQLException ex) {
           
        }
    }
     
          public void deleteStudent(Student student){
     
         try {
              c.getDBConn();
            java.sql.PreparedStatement ps =c.getCon().prepareStatement("delete from `student` \n"
                    
                    + "WHERE id= ?; ");
            
              ps.setInt(1, student.getId());
            
            ps.execute();
            ps.close();
            c.closeCOnnection();
         } catch (Exception ex) {
             ex.printStackTrace();
         }
     
     }

}
