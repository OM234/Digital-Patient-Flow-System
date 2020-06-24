package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DigiSystem;

import java.io.IOException;

public class Controller {

    DigiSystem digiSystem = DigiSystem.getInstance();

    @FXML
    private TextField userIDTextBox;
    @FXML
    private PasswordField passPassField;

    public void passChecker(ActionEvent event) throws IOException {

        String userID = userIDTextBox.getText();
        String pass = passPassField.getText();

        userIDTextBox.clear();
        passPassField.clear();

        if(digiSystem.passChecker(userID, pass)) {

            System.out.println("good pass");

            Stage stage = (Stage) userIDTextBox.getScene().getWindow();
            stage.close();
            loadDigiHealth();

        } else {

            userIDTextBox.setPromptText("Try again");

            System.out.println("wrong pass");
        }
    }

    private void loadDigiHealth() throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/DigiHealth.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DigiHealth");
        primaryStage.show();
    }
}
