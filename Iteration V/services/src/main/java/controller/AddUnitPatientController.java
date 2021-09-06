package controller;

import bean.Patient;
import bean.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import services.PatientServices;
import services.UnitServices;
import services.cache.ServicesCache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddUnitPatientController {
    private final ServicesCache servicesCache;
    private final PatientServices patientServices;
    private final UnitServices unitServices;
    TableView<Patient> patientsTableView;
    TableView<Unit> unitsTableView;

    @FXML private Label patientCreatedLabel;
    @FXML private Label unitCreatedLabel;
    @FXML private TextField ptIDTextArea;
    @FXML private TextField firstNameTextArea;
    @FXML private TextField lastNameTextArea;
    @FXML private TextField unitIDTextField;
    @FXML private TextField unitNameTextField;
    @FXML private ToggleGroup genderToggleGroup;
    @FXML private ToggleButton maleToggleButton;
    @FXML private ToggleButton femaleToggleButton;

    public AddUnitPatientController(ServicesCache servicesCache) throws SQLException {
        this.servicesCache = servicesCache;
        patientServices = servicesCache.getPatientServices();
        unitServices = servicesCache.getUnitServices();
    }

    public void addPatient() throws SQLException {

        String patientID = ptIDTextArea.getText();
        String firstName = firstNameTextArea.getText();
        String lastName = lastNameTextArea.getText();
        char gender = getGender();
        boolean allInputtedWell;

        allInputtedWell = textFieldsNotEmpty(new ArrayList<>(Arrays.asList(ptIDTextArea, firstNameTextArea, lastNameTextArea)));
        allInputtedWell = allInputtedWell && checkID(ptIDTextArea);
        allInputtedWell = allInputtedWell & genderSelected(); //TODO : change

        if (allInputtedWell && !patientServices.hasPatient(patientID)) {

            Patient added = new Patient(patientID);
            added.setFirstName(firstName);
            added.setLastName(lastName);
            added.setGender(gender);
            patientServices.addPatient(added);

            ptIDTextArea.clear();
            firstNameTextArea.clear();
            lastNameTextArea.clear();
            genderToggleGroup.selectToggle(null);

            patientCreatedLabel.setVisible(true);
            patientCreatedLabel.setText("Patient #" + patientID + " " + firstName + " " + lastName + " created");

            scrollToPatientSelection(added);

        } else if (patientServices.hasPatient(patientID)) {

            ptIDTextArea.clear();
            turnTextFieldToRed(ptIDTextArea, "already exists");
        }
    }

    public void addUnit() throws SQLException {

        String unitID = unitIDTextField.getText();
        String unitName = unitNameTextField.getText();
        boolean allInputtedWell;

        allInputtedWell = textFieldsNotEmpty(new ArrayList<>(Arrays.asList(unitIDTextField, unitNameTextField)));
        allInputtedWell = allInputtedWell && checkID(unitIDTextField);

        if (allInputtedWell && !unitServices.hasUnit(unitID)) {

            Unit added = new Unit();
            added.setID(unitID);
            added.setName(unitName);
            unitServices.addUnit(added);

            unitIDTextField.clear();
            unitNameTextField.clear();

            unitCreatedLabel.setVisible(true);
            unitCreatedLabel.setText("Unit #" + unitID + " " + unitName + " created");

            scrollToSelection(added);

        } else if (unitServices.hasUnit(unitID)) {

            unitIDTextField.clear();
            turnTextFieldToRed(unitIDTextField, "already exists");
        }
    }

    public boolean textFieldsNotEmpty(List<TextField> textFields) {

        boolean allInputted = true;

        for (TextField textField : textFields) {

            if (textField.getText().equals("")) {

                turnTextFieldToRed(textField, "Enter value");
                allInputted = false;
            }
        }

        return allInputted;
    }

    public boolean checkID(TextField IDField) {

        if (!IDField.getText().equals("") && !checkNumberic(IDField.getText())) {

            IDField.clear();
            turnTextFieldToRed(IDField, "Only numbers");
            return false;
        }

        return true;
    }


    public boolean checkNumberic(String ID) {

        try {

            Integer.parseInt(ID);

        } catch (NumberFormatException ex) {

            return false;
        }

        return true;
    }

    public void turnTextFieldToRed(TextField textField, String prompt) {

        textField.setStyle("-fx-control-inner-background: RED;");
        textField.setPromptText(prompt);
    }

    public void turnTextAreaToDefaultColorKeyBoard(KeyEvent event) {

        ((TextField) event.getSource()).setPromptText("");
        ((TextField) event.getSource()).setStyle("-fx-control-inner-background: WHITE;");
    }

    public void turnTextAreaToDefaultColorMouse(MouseEvent event) {

        ((TextField) event.getSource()).setPromptText("");
        ((TextField) event.getSource()).setStyle("-fx-control-inner-background: WHITE;");
    }

    public void setToggleButtonsGrey() {

        for (Toggle toggleButton : genderToggleGroup.getToggles())
            ((ToggleButton) toggleButton).setStyle(null);
    }

    public char getGender() {

        char gender;

        ToggleButton selected = (ToggleButton) genderToggleGroup.getSelectedToggle();

        if (selected == maleToggleButton) {

            gender = 'M';

        } else if (selected == femaleToggleButton) {

            gender = 'F';

        } else {

            gender = 'O';
        }

        return gender;
    }

    public boolean genderSelected(){

        if(genderToggleGroup.getSelectedToggle() == null) {

            for(Toggle toggleButton : genderToggleGroup.getToggles()) {

                ((ToggleButton)toggleButton).setStyle("-fx-background-color: RED");
            }
        }
        return !(genderToggleGroup.getSelectedToggle() == null);
    }

    public void setTableViews(TableView<Patient> patientsTableView, TableView<Unit> unitsTableView) {

        this.patientsTableView = patientsTableView;
        this.unitsTableView = unitsTableView;
    }

    public void scrollToSelection(Unit added) {

        int index;

        unitsTableView.getItems().add(added);
        //unitsTableView.setItems(unitsTableView.getItems());
        index = unitsTableView.getItems().indexOf(added);
        unitsTableView.getSelectionModel().select(added);
        unitsTableView.scrollTo(index);
    }

    public void scrollToPatientSelection(Patient added) {

        int index;

        patientsTableView.getItems().add(added);
        //patientsTableView.setItems(patientsTableView.getItems());
        index = patientsTableView.getItems().indexOf(added);
        patientsTableView.getSelectionModel().select(added);
        patientsTableView.scrollTo(index);
    }
}
