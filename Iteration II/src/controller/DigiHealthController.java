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
import model.Patient;
import model.Unit2;

import java.io.IOException;

public class DigiHealthController {

    private DigiSystem digiSystem = DigiSystem.getInstance();
    private boolean viewingPatientsOnUnits = false;
    private String patientOnUnitID;

    @FXML
    private Button viewUnitsButton;
    @FXML
    private Button viewPatientsButton;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button patientsOnUnitButton;
    @FXML
    private TableView<Unit2> unitsTableView;
    @FXML
    private TableView<Patient> patientsTableView;
    @FXML
    private Label bottomViewingLabel;
    @FXML
    private Label bigUnitNameLabel;


    public void initialize() {

        initializePatientTable();
        initializeUnitTable();
    }

    public void initializePatientTable() {

        TableColumn<Patient, String> patientIDCol = new TableColumn<>("Patient ID");
        TableColumn<Patient, String> patientFirstNameCol = new TableColumn<>("First Name");
        TableColumn<Patient, String> patientLastNameCol = new TableColumn<>("Last Name");
        TableColumn<Patient, String> patientGenderChar = new TableColumn<>("Gender");

        patientIDCol.setCellValueFactory(new PropertyValueFactory<>("patientID"));
        patientFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientGenderChar.setCellValueFactory(new PropertyValueFactory<>("gender"));

        patientFirstNameCol.prefWidthProperty().bind(patientsTableView.widthProperty().multiply(0.25));
        patientLastNameCol.prefWidthProperty().bind(patientsTableView.widthProperty().multiply(0.25));

        patientsTableView.getColumns().addAll(patientIDCol, patientFirstNameCol, patientLastNameCol, patientGenderChar);
    }

    public void initializeUnitTable() {

        TableColumn<Unit2, String> unitIDCol = new TableColumn<>("Unit ID");
        TableColumn<Unit2, String> unitNameCol = new TableColumn<>("Unit Name");
        TableColumn<Unit2, String> numPatCol = new TableColumn<>("# Patients");

        unitIDCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitID"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitName"));
        numPatCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("NumPatients"));

        unitNameCol.prefWidthProperty().bind(unitsTableView.widthProperty().multiply(0.4));

        unitsTableView.getColumns().addAll(unitIDCol, unitNameCol, numPatCol);
    }

    public void TableViewAppear(ActionEvent event) {

        addButton.setDisable(false);
        removeButton.setDisable(false);
        bigUnitNameLabel.setVisible(false);

        viewingPatientsOnUnits = false;

        if (event.getSource() == viewUnitsButton) {

            patientsTableView.getSelectionModel().clearSelection();
            populateUnitsTable();
        }

        if (event.getSource() == viewPatientsButton) {

            unitsTableView.getSelectionModel().clearSelection();
            populatePatientsTable();
        }
    }

    public void populatePatientsTable() {

        patientsTableView.getItems().clear();

        ObservableList<Patient> obsList = getPatientsObsList();
        patientsTableView.setItems(obsList);

        patientsTableView.setVisible(true);
        unitsTableView.setVisible(false);

        bottomViewingLabel.setVisible(true);
        bottomViewingLabel.setText("Viewing " + obsList.size() + " patients");

        patientsOnUnitButton.setDisable(true);
        patientsOnUnitButton.setOpacity(0.3);
    }

    public void populatePatientsOnUnitTable() {

        Unit2 selected = unitsTableView.getSelectionModel().getSelectedItem();
        viewingPatientsOnUnits = true;

        if(selected == null) {

            selected = digiSystem.getUnit(patientOnUnitID);

        } else {

            patientOnUnitID = unitsTableView.getSelectionModel().getSelectedItem().getUnitID();
        }

        bigUnitNameLabel.setVisible(true);
        bigUnitNameLabel.setText(selected.getUnitName());

        patientsTableView.getItems().clear();
        ObservableList<Patient> obsList = getPatientsOnUnitObsList(selected);
        patientsTableView.setItems(obsList);

        patientsTableView.setVisible(true);
        unitsTableView.setVisible(false);

        bottomViewingLabel.setVisible(true);
        bottomViewingLabel.setText("Viewing " + obsList.size() + " patients on unit " + selected.getUnitName());

        patientsOnUnitButton.setDisable(true);
        patientsOnUnitButton.setOpacity(0.3);

        unitsTableView.getSelectionModel().clearSelection();

    }

    private ObservableList<Patient> getPatientsObsList() {

        ObservableList<Patient> patientsList = FXCollections.observableArrayList();

        for (String patientID : digiSystem.getMapOfPatients().keySet()) {

            patientsList.add(digiSystem.getMapOfPatients().get(patientID));
        }

        return patientsList;
    }

    private ObservableList<Patient> getPatientsOnUnitObsList(Unit2 unit) {

        ObservableList<Patient> patientsOnUnitList = FXCollections.observableArrayList();

        for (String patientID : unit.getUnitPatientIDs()) {

            patientsOnUnitList.add(digiSystem.getPatient(patientID));
        }

        return patientsOnUnitList;
    }

    private void populateUnitsTable() {

        unitsTableView.getItems().clear();

        ObservableList<Unit2> obsList = getUnitsObsList();
        unitsTableView.setItems(obsList);

        unitsTableView.setVisible(true);
        patientsTableView.setVisible(false);

        bottomViewingLabel.setVisible(true);
        bottomViewingLabel.setText("Viewing " + obsList.size() + " units");

        patientsOnUnitButton.setDisable(false);
        patientsOnUnitButton.setOpacity(1);
    }

    private ObservableList<Unit2> getUnitsObsList() {

        ObservableList<Unit2> allUnits = FXCollections.observableArrayList();

        for (String unitID : digiSystem.getMapOfUnits().keySet()) {

            allUnits.add(digiSystem.getMapOfUnits().get(unitID));
        }

        return allUnits;
    }

    public void deletePatOrUnit() {

        if (unitsTableView.getSelectionModel().getSelectedItem() != null) {

            deleteUnit();
            unitsTableView.getSelectionModel().clearSelection();

        } else if (patientsTableView.getSelectionModel().getSelectedItem() != null) {

            deletePatientFromSystemOrUnit();
            patientsTableView.getSelectionModel().clearSelection();
        }
    }

    public void deleteUnit() {

        Unit2 selected = unitsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getUnitName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            unitsTableView.getItems().remove(selected);
            digiSystem.removeUnit(selected.getUnitID());
            bottomViewingLabel.setText("Viewing " + unitsTableView.getItems().size() + " patients");
        }
    }

    public void deletePatientFromSystemOrUnit() {

        if (viewingPatientsOnUnits == false) {

            deletePatientFromSystem();

        } else {

            deletePatientFromUnit();
        }

    }

    public void deletePatientFromSystem() {

        Patient selected = patientsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getFirstName() + " " +
                selected.getLastName() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            patientsTableView.getItems().remove(selected);
            digiSystem.removePatient(selected.getPatientID());
            bottomViewingLabel.setText("Viewing " + patientsTableView.getItems().size() + " patients");
        }
    }

    public void deletePatientFromUnit() {

        String patientSelectedID = patientsTableView.getSelectionModel().getSelectedItem().getPatientID();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + digiSystem.getPatientFirstName(patientSelectedID) +
                " " + digiSystem.getPatientLastName(patientSelectedID) + " from " + digiSystem.getUnitName(patientOnUnitID) +
                "? ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {

            digiSystem.removePatientFromUnit(patientSelectedID, patientOnUnitID);
            populatePatientsOnUnitTable();
        }
    }

    public void addUnitOrPatient() throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddUnitPatient.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/view/Styles.css").toExternalForm());
        primaryStage.setTitle("Add Patient or Unit");
        primaryStage.show();
    }
}
