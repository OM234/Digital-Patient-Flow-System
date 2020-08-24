package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Medication;
import model.Patient;

import java.time.LocalDate;

public class MedicationsController {

    @FXML private Label medicationsTopLabel;
    @FXML private TableView<Medication> medicationTableView;
    @FXML private TableColumn<Medication, String> prescriberIDCol;
    @FXML private TableColumn<Medication, LocalDate>  datePrescribedCol;
    @FXML private TableColumn<Medication, LocalDate>  dateExpireCol;
    @FXML private TableColumn<Medication, String>  nameCol;
    @FXML private TableColumn<Medication, Integer>  doseCol;
    @FXML private TableColumn<Medication, String> routeCol;
    @FXML private TableColumn<Medication, String> unitsCol;
    @FXML private TableColumn<Medication, String> frequencyCol;
    private Patient patient;
    private Medication selected;

    public MedicationsController(){

        this.selected = null;
    }

    public void setPatient(Patient patient) {

        this.patient = patient;
    }

    public void setTopLabel() {

        medicationsTopLabel.setText("Medications for Patient # " + patient.getPatientID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
    }

    public void setCellFactoryValues(){

        prescriberIDCol.setCellValueFactory(new PropertyValueFactory<>("prescriberID"));
        datePrescribedCol.setCellValueFactory(new PropertyValueFactory<>("prescribed"));
        dateExpireCol.setCellValueFactory(new PropertyValueFactory<>("expires"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        doseCol.setCellValueFactory(new PropertyValueFactory<>("dose"));
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        ObservableList<Medication> obsList = getMedicationObsList();
        medicationTableView.setItems(obsList);
    }

    private ObservableList<Medication> getMedicationObsList() {

        ObservableList<Medication> obsList = FXCollections.observableList(patient.getMedications());

        return obsList;
    }

    public void deleteMedication() {

        selected = medicationTableView.getSelectionModel().getSelectedItem();

        if(selected != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this medication?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES) {

                patient.removeMedication(selected);
                medicationTableView.refresh();
            }
        }

    }
}
