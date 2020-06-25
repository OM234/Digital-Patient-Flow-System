package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DigiSystem;
import model.Unit2;

import java.io.IOException;

public class Controller {

    DigiSystem digiSystem = DigiSystem.getInstance();

    @FXML private TextField userIDTextBox;
    @FXML private PasswordField passPassField;
    @FXML private Button viewUnitsButton;
    @FXML private Button viewPatientsButton;
    @FXML private TableView<Unit2> centerLists;

    public void passChecker(ActionEvent event) throws IOException {

        String userID = userIDTextBox.getText();
        String pass = passPassField.getText();

        userIDTextBox.clear();
        passPassField.clear();

        if(digiSystem.passChecker(userID, pass)) {

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

    public void TableViewAppear(ActionEvent event) {

        centerLists.setVisible(true);


        if(event.getSource() == viewUnitsButton) {

            populateUnitsTable();
        }

        if(event.getSource() == viewPatientsButton) {

        }

    }

    private void populateUnitsTable(){

        centerLists.getColumns().clear(); //TODO: This isn't efficient

        TableColumn<Unit2, String> unitIDCol = new TableColumn<>("Unit ID");
        TableColumn<Unit2, String> unitNameCol = new TableColumn<>("Unit Name");
        TableColumn<Unit2, String> numPatCol = new TableColumn<>("# Patients");

        unitIDCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitID"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitName"));
        numPatCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("NumPatients"));

        centerLists.getColumns().addAll(unitIDCol, unitNameCol, numPatCol);


        ObservableList<Unit2> obsList = getUnitsObsList();
        centerLists.setItems(obsList);

    }

    private void loadDigiHealth() throws IOException {

        Stage stage = (Stage) userIDTextBox.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/DigiHealth.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../view/Styles.css").toExternalForm());
        primaryStage.setTitle("DigiHealth");
        primaryStage.show();
    }

    private ObservableList<Unit2> getUnitsObsList() {

        ObservableList<Unit2> allUnits = FXCollections.observableArrayList();

        for(String unitID : digiSystem.getListOfUnits().keySet()) {

            allUnits.add(digiSystem.getListOfUnits().get(unitID));
        }

        return allUnits;
    }
}
