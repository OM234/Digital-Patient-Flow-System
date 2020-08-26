package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DigiSystem;

import java.io.IOException;

public class passPromptController {

    DigiSystem digiSystem = DigiSystem.getInstance();

    @FXML private TextField userIDTextBox;
    @FXML private PasswordField passPassField;
    private String userID;
    private String pass;

    public void passChecker() throws IOException {

        userID = userIDTextBox.getText();
        pass = passPassField.getText();

        userIDTextBox.clear();
        passPassField.clear();

        if (digiSystem.passChecker(userID, pass)) {

            loadDigiHealth();

        } else {

            userIDTextBox.setPromptText("Try again");
            userIDTextBox.setStyle("-fx-control-inner-background: RED;");
        }
    }

    public void turnUserBoxToDefaultColor() {

        userIDTextBox.setPromptText("Enter your user ID");
        userIDTextBox.setStyle("-fx-control-inner-background: WHITE;");
    }

    private void loadDigiHealth() throws IOException {

        digiSystem.setCurrentUser(userID);

        Stage stage = (Stage) userIDTextBox.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DigiHealth.fxml"));
        Parent root = fxmlLoader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("/view/DigiHealth.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.setTitle("DigiHealth");
        primaryStage.show();

        DigiHealthController digiHealthController = fxmlLoader.getController();
        digiHealthController.setDigiHealthController(digiHealthController);
    }
}
