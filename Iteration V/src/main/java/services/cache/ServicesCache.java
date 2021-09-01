package services.cache;

import services.*;

public class ServicesCache {

    private MedicalNoteServices medicalNoteServices;
    private MedicationServices medicationServices;
    private PatientServices patientServices;
    private PatOnUnitServices patOnUnitServices;
    private UnitServices unitServices;
    private UserServices userServices;

    public ServicesCache(MedicalNoteServices medicalNoteServices,
                         MedicationServices medicationServices, PatientServices patientServices,
                         PatOnUnitServices patOnUnitServices, UnitServices unitServices, UserServices userServices) {
        this.medicalNoteServices = medicalNoteServices;
        this.medicationServices = medicationServices;
        this.patientServices = patientServices;
        this.patOnUnitServices = patOnUnitServices;
        this.unitServices = unitServices;
        this.userServices = userServices;
    }

    public MedicalNoteServices getMedicalNoteServices() {
        return medicalNoteServices;
    }

    public MedicationServices getMedicationServices() {
        return medicationServices;
    }

    public PatientServices getPatientServices() {
        return patientServices;
    }

    public PatOnUnitServices getPatOnUnitServices() {
        return patOnUnitServices;
    }

    public UnitServices getUnitServices() {
        return unitServices;
    }

    public UserServices getUserServices() {
        return userServices;
    }
}
