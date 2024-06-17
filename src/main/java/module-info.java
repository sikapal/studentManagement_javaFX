 module gestionEtudiants {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;
    requires itextpdf;
   // requires com.itextpdf.text;
    
   
   
    
    opens gestionEtudiants to javafx.fxml;
    exports gestionEtudiants;
    exports gestionEtudiants.data;
    exports gestionEtudiants.model;
}
