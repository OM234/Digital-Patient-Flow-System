package controller;

import bean.Medication;
import bean.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.MedicationServices;
import services.UserServices;
import services.cache.ServicesCache;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicationsController {

    private final ServicesCache servicesCache;
    private final MedicationServices medicationServices;
    private final UserServices userServices;
    private List<ComboBox<String>> comboBoxList;
    private List<TextField> textFields;
    private Patient patient;
    private Medication selected;
    @FXML private AnchorPane allMedicationsPane;
    @FXML private AnchorPane addMedicationPane;
    @FXML private Label medicationsTopLabel;
    @FXML private Label medicationsTopLabel2;
    @FXML private Label medicationAddedLabel;
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

    public MedicationsController(ServicesCache servicesCache) throws SQLException {
        this.servicesCache = servicesCache;
        medicationServices = servicesCache.getMedicationServices();
        userServices = servicesCache.getUserServices();
        this.selected = null;
    }

    public void initialize() throws SQLException {

        viewAllMedicationsPane();

        setLists();

        setNamesComboxBox();
        setUnitsComboxBox();
        setRouteComboxBox();
        setFrequencyComboxBox();

        nameTextField.setVisible(false);
        unitsTextField.setVisible(false);
        routeTextField.setVisible(false);
        frequencyTextField.setVisible(false);

        setExpirationDatePicker();
    }

    private void setLists() {

        comboBoxList = new ArrayList<>(Arrays.asList(nameComboBox, routeComboBox, unitsComboBox,
                frequencyComboBox));
        textFields = new ArrayList<>(Arrays.asList(nameTextField, routeTextField, unitsTextField,
                doseTextField, frequencyTextField));
    }

    private void setNamesComboxBox() throws SQLException {

        nameComboBox.setItems(FXCollections.observableList(medicationServices.getMedicationNames()));
        setCommonComboBoxStuff(nameComboBox, nameTextField);
    }

    public void setUnitsComboxBox() {

        unitsComboBox.setItems(FXCollections.observableList(new ArrayList<>(Medication.UNITS_LIST)));
        setCommonComboBoxStuff(unitsComboBox, unitsTextField);
    }

    public void setRouteComboxBox() {

        routeComboBox.setItems(FXCollections.observableList(new ArrayList<>(Medication.ROUTE_LIST)));
        setCommonComboBoxStuff(routeComboBox, routeTextField);
    }

    public void setFrequencyComboxBox() {

        frequencyComboBox.setItems(FXCollections.observableList(new ArrayList<>(Medication.FREQUENCY_LIST)));
        setCommonComboBoxStuff(frequencyComboBox, frequencyTextField);
    }

    private void setCommonComboBoxStuff(ComboBox<String> comboBox, TextField textField) {

        comboBox.getItems().add("...");

        comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if(newValue == null) return;

            if(newValue.equals("...")) {
                textField.setVisible(true);
            } else {
                textField.setVisible(false);
            }
        });

        comboBox.setOnMouseClicked((event) -> {
            comboBox.setStyle(null);
        });
    }

    private void setExpirationDatePicker() {

        expirationDatePicker.setValue(LocalDate.now().plusYears(1));

        expirationDatePicker.setOnMouseClicked((event) -> {
            expirationDatePicker.setStyle("-fx-background-color: WHITE");
        });
        expirationDatePicker.getEditor().setOnMouseClicked((event) -> {
            expirationDatePicker.setStyle("-fx-background-color: WHITE");
        });
    }

    public void setPatient(Patient patient) {

        this.patient = patient;
    }

    public void setTopLabel() {

        medicationsTopLabel.setText("Medications for Patient # " + patient.getID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
        medicationsTopLabel2.setText("Medications for Patient # " + patient.getID() + " " + patient.getFirstName()
                + " " + patient.getLastName() );
    }

    public void setCellFactoryValues() throws SQLException {

        prescriberIDCol.setCellValueFactory(new PropertyValueFactory<>("prescriberID"));
        datePrescribedCol.setCellValueFactory(new PropertyValueFactory<>("prescribed"));
        dateExpireCol.setCellValueFactory(new PropertyValueFactory<>("expires"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("medName"));
        doseCol.setCellValueFactory(new PropertyValueFactory<>("dose"));
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        ObservableList<Medication> obsList = getMedicationObsList();
        medicationTableView.setItems(obsList);
    }

    private ObservableList<Medication> getMedicationObsList() throws SQLException {

        ObservableList<Medication> obsList = FXCollections.observableList(medicationServices.getPatientMedications(patient.getID()));

        return obsList;
    }

    public void deleteMedication() throws SQLException {

        selected = medicationTableView.getSelectionModel().getSelectedItem();

        if(selected != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this medication?",
                    ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES) {

                medicationServices.removeMedication(selected);
                medicationTableView.setItems(getMedicationObsList());
            }
        }
    }

    public void enterMedication() throws SQLException {

        boolean allValidFields = true;
        allValidFields = checkComboBoxes();
        allValidFields = checkTextFields() && allValidFields;
        allValidFields = checkDatePicker() && allValidFields;

        if(allValidFields) {

            createNewMedication();
        }
    }

    public boolean checkComboBoxes() {

        boolean allFieldValid = true;

        for(ComboBox<String> comboBox : comboBoxList) {

            if(comboBox.getSelectionModel().getSelectedItem() == null) {
                turnNodeErrorColor(comboBox);
                allFieldValid = false;
            }
        }

        return allFieldValid;
    }

    public boolean checkTextFields() {

        boolean allFieldValid = true;

        for(TextField textField : textFields) {

            if(textField.isVisible() && textField.getText().equals("")){

                turnNodeErrorColor(textField);
                allFieldValid = false;
            }
        }

        allFieldValid = checkDoseValid() && allFieldValid;

        return allFieldValid;
    }

    public boolean checkDatePicker() {

        if(expirationDatePicker.getEditor().getText().equals("")) {

            turnNodeErrorColor(expirationDatePicker);
            return false;
        }

        if(!expirationDatePicker.getEditor().getText().equals("")) {

            try {

                LocalDate date = expirationDatePicker.getValue();

            } catch (DateTimeParseException e) {

                turnNodeErrorColor(expirationDatePicker);
                return false;
            }
        }

        if(expirationDatePicker.getValue().isBefore(LocalDate.now())) {

            turnNodeErrorColor(expirationDatePicker);
            return false;
        }

        return true;
    }

    public boolean checkDoseValid() {

        try {

            Double.parseDouble(doseTextField.getText());

        } catch (NumberFormatException e) {

            turnNodeErrorColor(doseTextField);
            return false;
        }

        return true;
    }

    public void createNewMedication() throws SQLException {

        String name;
        double dose;
        String units;
        String route;
        String frequency;
        String prescriberID;
        LocalDate prescribed;
        LocalDate expires;

        name = !nameComboBox.getSelectionModel().getSelectedItem().equals("...") ? nameComboBox.getSelectionModel()
                .getSelectedItem() : nameTextField.getText();
        dose =  Double.parseDouble(doseTextField.getText());
        units = !unitsComboBox.getSelectionModel().getSelectedItem().equals("...") ? unitsComboBox.getSelectionModel()
                .getSelectedItem() : unitsTextField.getText();
        route = !routeComboBox.getSelectionModel().getSelectedItem().equals("...") ? routeComboBox.getSelectionModel()
                .getSelectedItem() : routeTextField.getText();
        frequency = !frequencyComboBox.getSelectionModel().getSelectedItem().equals("...") ? frequencyComboBox.getSelectionModel()
                .getSelectedItem() : frequencyTextField.getText();
        prescriberID = userServices.getCurrentUser().getID();
        prescribed = LocalDate.now();
        expires = expirationDatePicker.getValue();

        Medication medication = new Medication();

        medication.setPatientID(patient.getID());
        medication.setMedName(name);
        medication.setDose(dose);
        medication.setUnits(units);
        medication.setRoute(route);
        medication.setFrequency(frequency);
        medication.setPrescriberID(prescriberID);
        medication.setPrescribed(prescribed);
        medication.setExpires(expires);

        medicationServices.addMedication(medication);

        clearValues();
        displayMedicationAdded(medication);
        setCellFactoryValues();
    }

    private void displayMedicationAdded(Medication medication) {

        medicationAddedLabel.setVisible(true);
        medicationAddedLabel.setText(medication.getMedName() + " " + medication.getDose() + " " +  medication.getUnits() +
                " " + medication.getRoute() + " " + medication.getFrequency() + " by" + medication.getPrescriberID()
                + " prescribed"  );
    }

    private void clearValues() {

        for(TextField textField : textFields) {
            textField.clear();
        }
        for(ComboBox<String> comboBox : comboBoxList) {
            comboBox.setValue(null);
        }
    }

    public void turnNodeErrorColor(ComboBox comboBox) {

        comboBox.setStyle("-fx-background-color: RED");
    }

    public void turnNodeErrorColor(TextField textField) {

        textField.setStyle("-fx-control-inner-background: RED;");
    }

    public void turnNodeErrorColor(DatePicker datePicker) {

        datePicker.setStyle("-fx-control-inner-background: RED;");
    }

    public void turnNodeDefaultColor(MouseEvent e) {

        ((TextField)e.getSource()).setStyle("-fx-control-inner-background: WHITE;");
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
