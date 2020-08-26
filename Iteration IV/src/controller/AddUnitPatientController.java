package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.DigiSystem;
import model.Patient;
import model.Unit2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddUnitPatientController {

    DigiSystem digiSystem = DigiSystem.getInstance();
    TableView<Patient> patientsTableView;
    TableView<Unit2> unitsTableView;

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

    public void addPatient() {

        String patientID = ptIDTextArea.getText();
        String firstName = firstNameTextArea.getText();
        String lastName = lastNameTextArea.getText();
        char gender = getGender();
        boolean allInputtedWell;

        allInputtedWell = textFieldsNotEmpty(new ArrayList<>(Arrays.asList(ptIDTextArea, firstNameTextArea, lastNameTextArea)));
        allInputtedWell = allInputtedWell && checkID(ptIDTextArea);
        allInputtedWell = allInputtedWell & genderSelected(); //TODO : change

        if (allInputtedWell && !digiSystem.hasPatient(patientID)) {

            Patient added = new Patient(patientID, firstName, lastName, gender);

            ptIDTextArea.clear();
            firstNameTextArea.clear();
            lastNameTextArea.clear();
            genderToggleGroup.selectToggle(null);

            patientCreatedLabel.setVisible(true);
            patientCreatedLabel.setText("Patient #" + patientID + " " + firstName + " " + lastName + " created");

            scrollToPatientSelection(added);

        } else if (digiSystem.hasPatient(patientID)) {

            ptIDTextArea.clear();
            turnTextFieldToRed(ptIDTextArea, "already exists");
        }
    }

    public void addUnit() {

        String unitID = unitIDTextField.getText();
        String unitName = unitNameTextField.getText();
        boolean allInputtedWell;

        allInputtedWell = textFieldsNotEmpty(new ArrayList<>(Arrays.asList(unitIDTextField, unitNameTextField)));
        allInputtedWell = allInputtedWell && checkID(unitIDTextField);

        if (allInputtedWell && !digiSystem.hasUnit(unitID)) {

            Unit2 added = new Unit2(unitID, unitName);

            unitIDTextField.clear();
            unitNameTextField.clear();

            unitCreatedLabel.setVisible(true);
            unitCreatedLabel.setText("Unit #" + unitID + " " + unitName + " created");

            scrollToSelection(added);

        } else if (digiSystem.hasUnit(unitID)) {

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

    public void setTableViews(TableView<Patient> patientsTableView, TableView<Unit2> unitsTableView) {

        this.patientsTableView = patientsTableView;
        this.unitsTableView = unitsTableView;
    }

    public void scrollToSelection(Unit2 added) {

        int index;

        unitsTableView.getItems().add(added);
        unitsTableView.setItems(unitsTableView.getItems());
        index = unitsTableView.getItems().indexOf(added);
        unitsTableView.getSelectionModel().select(added);
        unitsTableView.scrollTo(index);
    }

    public void scrollToPatientSelection(Patient added) {

        int index;

        patientsTableView.getItems().add(added);
        patientsTableView.setItems(patientsTableView.getItems());
        index = patientsTableView.getItems().indexOf(added);
        patientsTableView.getSelectionModel().select(added);
        patientsTableView.scrollTo(index);
    }
}
