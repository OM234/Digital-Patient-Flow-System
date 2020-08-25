package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Medication;
import model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;

public class MedicationsController {

    @FXML private AnchorPane allMedicationsPane;
    @FXML private AnchorPane addMedicationPane;
    @FXML private Label medicationsTopLabel;
    @FXML private Label medicationsTopLabel2;
    @FXML private TableView<Medication> medicationTableView;
    @FXML private TableColumn<Medication, String> prescriberIDCol;
    @FXML private TableColumn<Medication, LocalDate>  datePrescribedCol;
    @FXML private TableColumn<Medication, LocalDate>  dateExpireCol;
    @FXML private TableColumn<Medication, String>  nameCol;
    @FXML private TableColumn<Medication, Integer>  doseCol;
    @FXML private TableColumn<Medication, String> routeCol;
    @FXML private TableColumn<Medication, String> unitsCol;
    @FXML private TableColumn<Medication, String> frequencyCol;
    @FXML private ComboBox<String> nameComboBox;
    @FXML private ComboBox<String> unitsComboBox;
    @FXML private ComboBox<String> routeComboBox;
    @FXML private ComboBox<String> frequencyComboBox;
    @FXML private TextField nameTextField;
    @FXML private TextField doseTextField;
    @FXML private TextField unitsTextField;
    @FXML private TextField routeTextField;
    @FXML private TextField frequencyTextField;
    @FXML private DatePicker expirationDatePicker;
    private Patient patient;
    private Medication selected;

    public void initialize(){

        viewAllMedicationsPane();

        setNamesComboxBox();
        setUnitsComboxBox();
        setRouteComboxBox();
        setFrequencyComboxBox();

        nameTextField.setVisible(false);
        unitsTextField.setVisible(false);
        routeTextField.setVisible(false);
        frequencyTextField.setVisible(false);
    }

    private void setNamesComboxBox() {

        nameComboBox.setItems(FXCollections.observableList(new ArrayList<String>(Medication.medicationNames)));
        nameComboBox.getItems().add("...");

        nameComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue.equals("...")) {
                nameTextField.setVisible(true);
            } else {
                nameTextField.setVisible(false);
            }
        });
    }

    public void setUnitsComboxBox() {

        unitsComboBox.setItems(FXCollections.observableList(new ArrayList<>(Medication.unitsList)));
        unitsComboBox.getItems().add("...");

        unitsComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue.equals("...")) {
                unitsTextField.setVisible(true);
            } else {
                unitsTextField.setVisible(false);
            }
        });
    }

    public void setRouteComboxBox() {

        routeComboBox.setItems(FXCollections.observableList(new ArrayList<>(Medication.routeList)));
        routeComboBox.getItems().add("...");

        routeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue.equals("...")) {
                routeTextField.setVisible(true);
            } else {
                routeTextField.setVisible(false);
            }
        });
    }

    public void setFrequencyComboxBox() {

        frequencyComboBox.setItems(FXCollections.observableList(new ArrayList<>(Medication.frequencyList)));
        frequencyComboBox.getItems().add("...");

        frequencyComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(newValue.equals("...")) {
                frequencyTextField.setVisible(true);
            } else {
                frequencyTextField.setVisible(false);
            }
        });
    }
    public MedicationsController(){

        this.selected = null;
    }

    public void setPatient(Patient patient) {

        this.patient = patient;
    }

    public void setTopLabel() {

        medicationsTopLabel.setText("Medications for Patient # " + patient.getPatientID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
        medicationsTopLabel2.setText("Medications for Patient # " + patient.getPatientID() + " " + patient.getFirstName()
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

    public void viewEnterMedicationPane(){

        allMedicationsPane.setVisible(false);
        addMedicationPane.setVisible(true);
    }

    public void viewAllMedicationsPane() {

        allMedicationsPane.setVisible(true);
        addMedicationPane.setVisible(false);
    }
}
