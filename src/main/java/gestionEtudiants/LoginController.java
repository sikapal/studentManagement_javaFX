
package gestionEtudiants;

import static gestionEtudiants.App.loadFXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
          
          
            switchToMainMenu(event);
            System.out.println("Login successful!");
        } else {
            errorMessage.setText("Invalid username or password.");
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
    
        public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = new Scene(loadFXML("WelcomeScreen2"), 700, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateLogin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/gestionetudiant"; // Replace with your database URL
        String dbUser = "root"; // Replace with your database username
        String dbPassword = ""; // Replace with your database password

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Return true if a matching record is found

        } catch (SQLException e) {
            return false;
        }
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
