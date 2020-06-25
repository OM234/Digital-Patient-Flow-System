package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DigiSystem;
import model.*;

import javax.swing.*;
import java.io.IOException;

public class Controller {

    DigiSystem digiSystem = DigiSystem.getInstance();

    @FXML
    private TextField userIDTextBox;
    @FXML
    private PasswordField passPassField;
    @FXML
    private Button viewUnitsButton;
    @FXML
    private Button viewPatientsButton;
    @FXML
    private ListView centerLists;

    public void passChecker() throws IOException {

        String userID = userIDTextBox.getText();
        String pass = passPassField.getText();

        userIDTextBox.clear();
        passPassField.clear();

        if(digiSystem.passChecker(userID, pass)) {

            //System.out.println("good pass");
            loadDigiHealth();

        } else {

            userIDTextBox.setPromptText("Try again");

            //System.out.println("wrong pass");
        }
    }

    public void listViewAppear(ActionEvent event) {

        centerLists.setVisible(true);

        Unit unit = new AUnit("123", "ER");

        System.out.println(unit.toString());
        if(event.getSource() == viewUnitsButton) {
//            centerLists.getItems().add(unit);
//            centerLists.getItems().add("unit2");
//            centerLists.getItems().add("unit3");
        }

        if(event.getSource() == viewPatientsButton) {
//            centerLists.getItems().add("patient1");
//            centerLists.getItems().add("patient2");
//            centerLists.getItems().add("patient3");
        }

    }

    private void loadDigiHealth() throws IOException {

        Stage stage = (Stage) userIDTextBox.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/DigiHealth.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DigiHealth");
        primaryStage.show();
    }
}
