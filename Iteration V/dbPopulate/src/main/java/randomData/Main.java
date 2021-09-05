package randomData;

import persistence.*;
import services.*;
import services.cache.ServicesCache;

public class Main {
    public static void main(String[] args) {
        createRandomData();
    }

    private static void createRandomData() {
        new MakeData(getServicesCache()).createRandomData();
        System.exit(0);
    }

    private static ServicesCache getServicesCache() {
        ContactInfoDAO contactInfoDAO = new ContactInfoDAO();
        MedicalNoteDAO medicalNoteDAO = new MedicalNoteDAO();
        MedicationDAO medicationDAO = new MedicationDAO();
        MedicationNameDAO medicationNameDAO = new MedicationNameDAO();
        PatientDAO patientDAO = new PatientDAO();
        PatOnUnitDAO patOnUnitDAO = new PatOnUnitDAO();
        UnitDAO unitDAO = new UnitDAO();
        UserDAO userDAO = new UserDAO();

        PatientServices patientServices = new PatientServices(patientDAO, contactInfoDAO);
        UnitServices unitServices = new UnitServices(unitDAO);
        MedicalNoteServices medicalNoteServices = new MedicalNoteServices(medicalNoteDAO, patientServices);
        MedicationServices medicationServices = new MedicationServices(medicationDAO, medicationNameDAO, patientServices);
        PatOnUnitServices patOnUnitServices = new PatOnUnitServices(patOnUnitDAO, unitServices, patientServices);
        UserServices userServices = new UserServices(userDAO);

        ServicesCache servicesCache = new ServicesCache(
                medicalNoteServices, medicationServices,
                patientServices, patOnUnitServices, unitServices, userServices);

        return servicesCache;
    }
}
