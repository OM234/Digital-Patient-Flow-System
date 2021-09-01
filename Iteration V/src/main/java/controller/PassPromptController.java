package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.DigiServices;
import services.UserServices;
import services.cache.ServicesCache;

import java.io.IOException;
import java.sql.SQLException;

public class PassPromptController {

    private final UserServices userServices;
    private final ServicesCache servicesCache;
    @FXML private TextField userIDTextBox;
    @FXML private PasswordField passPassField;
    private String userID;
    private String pass;

    public PassPromptController(ServicesCache servicesCache) {
        this.servicesCache = servicesCache;
        userServices = servicesCache.getUserServices();
    }

    public void passChecker() throws IOException, SQLException {
        userID = userIDTextBox.getText();
        pass = passPassField.getText();

        userIDTextBox.clear();
        passPassField.clear();

        if (userServices.passChecker(userID, pass)) {
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
        Stage stage = (Stage) userIDTextBox.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DigiHealth.fxml"));
        fxmlLoader.setController(new DigiHealthController(servicesCache));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.setTitle("DigiHealth");
        primaryStage.show();

        DigiHealthController digiHealthController = fxmlLoader.getController();
        digiHealthController.setDigiHealthController(digiHealthController);
    }
}
