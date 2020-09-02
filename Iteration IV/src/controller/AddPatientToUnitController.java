package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.DigiSystem;

import java.sql.SQLException;

public class AddPatientToUnitController {

    private DigiSystem digiSystem = DigiSystem.getInstance();
    private String unitID;

    @FXML private TextField ptIDTextArea;
    @FXML private Label addPatientLabel;
    @FXML private DigiHealthController digiHealthController;


    public void addPatientToUnit() throws SQLException {

        String patientID = ptIDTextArea.getText();

        if(digiSystem.hasPatient(patientID)) {

            if (digiSystem.hasUnit(unitID)) {

                if (!digiSystem.patientOnUnit(patientID, unitID)) {

                    System.out.println("pt id: " + patientID + "\n" + "unit ID: " + unitID + "\n" + "on unit: " + digiSystem.patientOnUnit(patientID, unitID));
                    digiSystem.addPatientToUnit(patientID, unitID);
                    ptIDTextArea.clear();
                    ptIDTextArea.setPromptText("Patient added to unit");

                    digiHealthController.populatePatientsOnUnitTable();

                } else {

                    turnTextFieldToRed(ptIDTextArea, "Already on unit");
                }
            } else {

                turnTextFieldToRed(ptIDTextArea, "Unit does not exist");
            }
        } else {

            turnTextFieldToRed(ptIDTextArea, "Patient does not exist");
        }

    }

    public void setDigiHealthController(DigiHealthController digiHealthController) {

        this.digiHealthController = digiHealthController;
    }

    public void turnTextFieldToRed(TextField textField, String prompt) {

        textField.clear();
        textField.setStyle("-fx-control-inner-background: RED;");
        textField.setPromptText(prompt);
    }

    public void turnTextAreaToDefaultColorMouse(MouseEvent event) {

        ((TextField) event.getSource()).setPromptText("");
        ((TextField) event.getSource()).setStyle("-fx-control-inner-background: WHITE;");
    }

    public void setUnitID(String unitID) {

        this.unitID = unitID;

        addPatientLabel.setText("Add patient to " + digiSystem.getUnitName(unitID));
    }

}
