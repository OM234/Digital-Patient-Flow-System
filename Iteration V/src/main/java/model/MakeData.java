package model;

import bean.ContactInfo;
import bean.User;
import services.DigiServices;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MakeData {

    DigiServices digiServices;
    List<bean.Patient> patientList1;
    List<bean.User> userList2;

    public MakeData() throws SQLException{

        digiServices = DigiServices.getInstance();
    }

    public void createRandomData() throws IOException, SQLException {

        addUsers();
        makeUnits();
        makePatients();
        addPatientsToUnits();
        makeHeightWeightBMIDOB();
        makeContactInformation();
        makeMedicalNotes();
        makeMedications();
    }

    public void addUsers() throws SQLException {

        User user1 = new User();
        user1.setID("admin");
        user1.setPassword("pass");
        digiServices.addUser(user1);
        for(int i = 0 ; i < 100 ; i++) {
            User user = new User();
            user.setID("healthProf" + (i+1));
            user.setPassword("password" + (i+1));
            digiServices.addUser(user);
        }
        userList2 = digiServices.getAllUsers();
    }

    public void makeUnits() throws SQLException {

        String[] unitNames = {"ER", "ICU", "Medicine", "Surgery", "Ophthalmology", "Cardiology", "Geriatrics", "Psychiatry",
            "Pediatrics", "Rehabilitation", "Dialysis"};

        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int indexNames = rand.nextInt(unitNames.length);
            Unit2 unit = new Unit2(Integer.toString(i * 123), unitNames[indexNames] + "#" + i);
            bean.Unit unit1 = new bean.Unit();
            unit1.setID(Integer.toString(i * 123));
            unit1.setName(unitNames[indexNames] + "#" + i);
            digiServices.addUnit(unit1);
        }
    }

    public void makePatients() throws FileNotFoundException, SQLException {

        ArrayList<String> namesList = new ArrayList<>();
        File names = new File("src/model/Names.txt");
        Scanner reader = new Scanner(names);

        while (reader.hasNext()) {
            namesList.add(reader.next());
        }

        Random rand = new Random();
        for (int i = 0; i < 100; i++) {

            Patient patient;

            int index = rand.nextInt(namesList.size());
            String firstName = namesList.get(index);
            index = rand.nextInt(namesList.size());
            String lastName = namesList.get(index);
            String id = Integer.toString((i + 1) * 312);
            char gender = index % 2 == 0 ? 'M' : 'F';

            patient = new Patient(id);
            patient.setLastName(lastName);
            patient.setGender(gender);
            new Patient(id, firstName, lastName, gender);
            bean.Patient patient1 = new bean.Patient();
            patient1.setID(id);
            patient1.setFirstName(firstName);
            patient1.setLastName(lastName);
            patient1.setGender(gender);
            digiServices.addPatient(patient1);
        }
    }

    public void addPatientsToUnits() throws SQLException {

        Random rand = new Random();
        List<bean.Unit> unitsList2 = new ArrayList<>();

        unitsList2 = digiServices.getAllUnits();
        patientList1 = digiServices.getAllPatients();

        for (int i = 0; i < 100; i++) {
            int patInd = rand.nextInt(patientList1.size());
            int unitInd = rand.nextInt(unitsList2.size());
            digiServices.addPatToUnit(unitsList2.get(unitInd), patientList1.get(patInd));
        }
    }

    public void makeHeightWeightBMIDOB() throws SQLException {

        Random rand = new Random();

        patientList1.stream().forEach(e -> {
            e.setHeight(rand.nextInt(30) + 150);
            e.setWeight(rand.nextInt(28) + 54);
            e.setBMI(e.getWeight() / Math.pow((e.getHeight()/100.0),2));
            e.setDOB(LocalDate.of(rand.nextInt(80)+1920, rand.nextInt(11)+1, rand.nextInt(28)+1));
            try {
                digiServices.updatePatient(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void makeContactInformation() throws FileNotFoundException {

        int i = 0;
        Random rand = new Random();
        ArrayList<String[]> addresses = new ArrayList<>();
        ArrayList<String> phoneNumbers = new ArrayList<>();
        File address = new File("src/model/Addresses.txt");
        Scanner reader = new Scanner(address);

        while (reader.hasNextLine()) {

            if(i % 2 == 0) {
                addresses.add(new String[5]);
                String line = reader.nextLine();
                String fields[] = line.split(" ");
                String strName = "";

                addresses.get(addresses.size() - 1)[0] = fields[0];

                for(int j = 1; j < fields.length; j++) {

                    strName += fields[j] + " ";
                }
                strName = strName.trim();
                addresses.get(addresses.size() - 1)[1] = strName;

            } else {

                String line = reader.nextLine();
                String fields[] = line.split(",");
                addresses.get(addresses.size() - 1)[2] = fields[0];
                fields = fields[1].trim().split(" ");
                addresses.get(addresses.size() - 1)[3] = fields[0];
                addresses.get(addresses.size() - 1)[4] = fields[1] + fields[2];
            }
            i++;
        }

        File phoneNumber = new File("src/model/PhoneNumbers.txt");
        reader = new Scanner(phoneNumber);

        while(reader.hasNextLine()) {
            phoneNumbers.add(reader.nextLine());
        }

        patientList1.forEach(e -> {

            ContactInfo contactInfo = new ContactInfo();

            String[] add = addresses.get(rand.nextInt(addresses.size()));
            contactInfo.setPatientID(e.getID());
            contactInfo.setStreetNumber(add[0]);
            contactInfo.setStreetName(add[1]);
            contactInfo.setPostalCode(add[4]);
            contactInfo.setCity(add[2]);
            contactInfo.setProvince(add[3]);
            contactInfo.setCountry("Canada");
            contactInfo.setPhoneNumber(phoneNumbers.get(rand.nextInt(phoneNumbers.size())));
            contactInfo.setEmail(e.getFirstName().toLowerCase() + "." + e.getLastName().toLowerCase() + "@gmail.com");

            try {
                digiServices.updateContactInfo(contactInfo);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


    }

    public void makeMedicalNotes() throws SQLException {

        Random random = new Random();
        List<User> userList = new ArrayList<>(digiServices.getAllUsers());

        patientList1.forEach(patient -> {

            for(int i = 0; i < random.nextInt(10)+1 ; i++) {

                bean.MedicalNote medicalNote = new bean.MedicalNote();
                medicalNote.setPatientID(patient.getID());
                medicalNote.setNoteID(i);
                medicalNote.setWriterID(userList.get(random.nextInt(userList.size())).getID());
                medicalNote.setTemp(random.nextDouble() * 2.5 + 35.5);
                medicalNote.setO2Sat(random.nextInt(8) + 93);
                medicalNote.setPulse(random.nextInt(71) + 50);
                medicalNote.setSbp(random.nextInt(50) + 110);
                medicalNote.setDbp(random.nextInt(30) + 55);
                medicalNote.setDeleted((random.nextInt(21) > 19) ? true : false);
                medicalNote.setDate(LocalDate.of(random.nextInt(30) + 1990, random.nextInt(12) + 1,
                        random.nextInt(26) + 1));

                String note = getNoteText(random, patient);
                medicalNote.setNote(note);
                try {
                    digiServices.addMedicalNote(medicalNote);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }

    private String getNoteText(Random random, bean.Patient patient) {

        String note = "";
        switch(random.nextInt(3)) {
            case 0:
                note += patient.getFirstName() + " " + patient.getLastName() + " was seen today for a ";
                break;
            case 1:
                note += "Patient was seen today for a ";
                break;
            case 2:
                note += "I saw patient " + patient.getFirstName() + " " + patient.getLastName() +  " today for a ";
                break;
        }
        switch(random.nextInt(3)){
            case 0 :
                note += "general wellness appointment.";
                break;
            case 1:
                note += "health problem follow-up appointment.";
                break;
            case 2:
                note += "counselling appointment.";
                break;
        }
        switch(patient.getGender()) {
            case 'F':
                note += " Her health ";
                break;
            case 'M':
                note += " His health ";
                break;
            case 'O':
                note += " Their health ";
                break;
        }
        switch(random.nextInt(3)){
            case 0 :
                note += "remains stable.";
                break;
            case 1:
                note += "is deteriorating.";
                break;
            case 2:
                note += "is improving.";
                break;
        }
        return note;
    }


    private String getNoteText(Random random, Patient patient) {

        String note = "";
        switch(random.nextInt(3)) {
            case 0:
                note += patient.getFirstName() + " " + patient.getLastName() + " was seen today for a ";
                break;
            case 1:
                note += "Patient was seen today for a ";
                break;
            case 2:
                note += "I saw patient " + patient.getFirstName() + " " + patient.getLastName() +  " today for a ";
                break;
        }
        switch(random.nextInt(3)){
            case 0 :
                note += "general wellness appointment.";
                break;
            case 1:
                note += "health problem follow-up appointment.";
                break;
            case 2:
                note += "counselling appointment.";
                break;
        }
        switch(patient.getGender()) {
            case 'F':
                note += " Her health ";
                break;
            case 'M':
                note += " His health ";
                break;
            case 'O':
                note += " Their health ";
                break;
        }
        switch(random.nextInt(3)){
            case 0 :
                note += "remains stable.";
                break;
            case 1:
                note += "is deteriorating.";
                break;
            case 2:
                note += "is improving.";
                break;
        }
        return note;
    }

    private void makeMedications() throws IOException {

        List<String> medNames = new ArrayList<>();
        Medication.medicationNames = new TreeSet<>();
        Random rand = new Random();

        Medication.frequencyList = new ArrayList<>(Arrays.asList("DIE", "BID", "TID", "QID"));
        Medication.unitsList = new ArrayList<>(Arrays.asList("mg", "g", "ml", "units"));
        Medication.routeList = new ArrayList<>(Arrays.asList("PO","PR","SC", "IM","SL", "IV"));


        getMedicationNames(medNames);

        patientList1.forEach(e -> {

            for(int i = 0; i < rand.nextInt(10); i++) {

                String name = medNames.get(rand.nextInt(medNames.size()));
                int dose = rand.nextInt(300)+50;
                String units = Medication.unitsList.get(rand.nextInt(Medication.unitsList.size()));
                String route = Medication.routeList.get(rand.nextInt(Medication.routeList.size()));
                String frequency = Medication.frequencyList.get(rand.nextInt(Medication.frequencyList.size()));
                String prescriberID = userList2.get(rand.nextInt(userList2.size())).getID();
                LocalDate prescribed = LocalDate.of(rand.nextInt(30)+1970, rand.nextInt(11)+1,
                        rand.nextInt(26)+1);
                LocalDate expires = LocalDate.of(rand.nextInt(30)+2000, rand.nextInt(11)+1,
                        rand.nextInt(26)+1);

                bean.Medication medication = new bean.Medication();
                medication.setPatientID(e.getID());
                medication.setPrescriberID(prescriberID);
                medication.setMedName(name);
                medication.setRoute(route);
                medication.setDose(dose);
                medication.setFrequency(frequency);
                medication.setUnits(units);
                medication.setPrescribed(prescribed);
                medication.setExpires(expires);

                try {
                    digiServices.addMedication(medication);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

    }

    private void getMedicationNames(List<String> medNames) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/model/Medications.txt"));
        String name = "";

        while((name = reader.readLine()) != null) {
            medNames.add(name);
            Medication.medicationNames.add(name);
        }
    }


}
