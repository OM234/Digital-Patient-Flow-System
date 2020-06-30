package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DigiSystem;
import model.Patient;
import model.Unit2;


public class DigiHealthController {

    DigiSystem digiSystem = DigiSystem.getInstance();

    @FXML
    private Button viewUnitsButton;
    @FXML
    private Button viewPatientsButton;
    @FXML
    private Button patientsOnUnitButton;
    @FXML
    private TableView<Unit2> centerLists;
    @FXML
    private TableView<Patient> patientsTableView;
    @FXML
    private Label numResultsLabel;


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

        patientsTableView.getColumns().addAll(patientIDCol, patientFirstNameCol, patientLastNameCol, patientGenderChar);
    }

    public void initializeUnitTable(){

        TableColumn<Unit2, String> unitIDCol = new TableColumn<>("Unit ID");
        TableColumn<Unit2, String> unitNameCol = new TableColumn<>("Unit Name");
        TableColumn<Unit2, String> numPatCol = new TableColumn<>("# Patients");

        unitIDCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitID"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("unitName"));
        numPatCol.setCellValueFactory(new PropertyValueFactory<Unit2, String>("NumPatients"));

        centerLists.getColumns().addAll(unitIDCol, unitNameCol, numPatCol);
    }

    public void TableViewAppear(ActionEvent event) {

        if (event.getSource() == viewUnitsButton) {

            populateUnitsTable();
        }

        if (event.getSource() == viewPatientsButton) {

            populatePatientsTable();
        }
    }

    public void populatePatientsTable() {

        patientsTableView.getItems().clear(); //TODO: This isn't efficient

        ObservableList<Patient> obsList = getPatientsObsList();
        patientsTableView.setItems(obsList);
        patientsTableView.setVisible(true);
        centerLists.setVisible(false);

        numResultsLabel.setVisible(true);
        numResultsLabel.setText("Viewing " + obsList.size() + " patients");

        patientsOnUnitButton.setDisable(true);
        patientsOnUnitButton.setOpacity(0.3);
    }

    public void populatePatientsOnUnitTable() {

        Unit2 selected = centerLists.getSelectionModel().getSelectedItem();

        if(selected != null) {

            patientsTableView.getItems().clear(); //TODO: This isn't efficient
            ObservableList<Patient> obsList = getPatientsOnUnitObsList(selected);
            patientsTableView.setItems(obsList);

            patientsTableView.setVisible(true);
            centerLists.setVisible(false);

            numResultsLabel.setVisible(true);
            numResultsLabel.setText("Viewing " + obsList.size() + " patients");

            patientsOnUnitButton.setDisable(true);
            patientsOnUnitButton.setOpacity(0.3);
        }
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

        for(String patientID : unit.getUnitPatientIDs()) {

            patientsOnUnitList.add(digiSystem.getPatient(patientID));
        }

        return patientsOnUnitList;
    }

    private void populateUnitsTable() {

        centerLists.getItems().clear();

        ObservableList<Unit2> obsList = getUnitsObsList();
        centerLists.setItems(obsList);

        centerLists.setVisible(true);
        patientsTableView.setVisible(false);

        numResultsLabel.setVisible(true);
        numResultsLabel.setText("Viewing " + obsList.size() + " units");

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
}
