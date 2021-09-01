package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.PatOnUnitServices;
import services.PatientServices;
import services.UnitServices;
import services.cache.ServicesCache;

import java.sql.SQLException;

public class AddPatientToUnitController {

    private final ServicesCache servicesCache;
    private final PatientServices patientServices;
    private final UnitServices unitServices;
    private final PatOnUnitServices patOnUnitServices;
    private String unitID;

    @FXML private TextField ptIDTextArea;
    @FXML private Label addPatientLabel;
    @FXML private DigiHealthController digiHealthController;

    public AddPatientToUnitController(ServicesCache servicesCache) throws SQLException {
        this.servicesCache = servicesCache;
        patientServices = servicesCache.getPatientServices();
        unitServices = servicesCache.getUnitServices();
        patOnUnitServices = servicesCache.getPatOnUnitServices();
    }

    public void addPatientToUnit() throws SQLException {

        String patientID = ptIDTextArea.getText();

        if(patientServices.hasPatient(patientID)) {

            if (unitServices.hasUnit(unitID)) {

                if (!patOnUnitServices.patientOnUnit(patientID, unitID)) {

                    //System.out.println("pt id: " + patientID + "\n" + "unit ID: " + unitID + "\n" + "on unit: " + digiSystem.patientOnUnit(patientID, unitID));
                    patOnUnitServices.addPatToUnit(unitID, patientID);
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

    public void setUnitID(String unitID) throws SQLException {

        this.unitID = unitID;

        addPatientLabel.setText("Add patient to " + unitServices.getUnit(unitID).getName());
    }

}
