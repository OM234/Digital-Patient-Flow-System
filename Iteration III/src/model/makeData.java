package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public class makeData {

    DigiSystem digiSystem = DigiSystem.getInstance();

    public void debug() throws FileNotFoundException {

        addUsers();
        makeUnits();
        makePatients();
        addPatientsToUnits();
        makeHeightWeightBMIDOB();
        makeContactInformation();
        makeMedicalNotes();
    }

    public void addUsers() {

        new AUser("", "");
        new AUser("admin", "password");
    }

    public void makeUnits() {

        String[] unitNames = {"ER", "ICU", "Medicine", "Surgery", "Ophthalmology", "Cardiology", "Geriatrics", "Psychiatry",
            "Pediatrics", "Rehabilitation", "Dialysis"};

        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int indexNames = rand.nextInt(unitNames.length);
            new Unit2(Integer.toString(i * 123), unitNames[indexNames] + "#" + i);
        }
    }

    public void makePatients() throws FileNotFoundException {

        ArrayList<String> namesList = new ArrayList<>();
        File names = new File("src/model/Names.txt");
        Scanner reader = new Scanner(names);

        while (reader.hasNext()) {
            namesList.add(reader.next());
        }

        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            int index = rand.nextInt(namesList.size());
            String firstName = namesList.get(index);
            index = rand.nextInt(namesList.size());
            String lastName = namesList.get(index);
            String id = Integer.toString((i + 1) * 312);
            char gender = index % 2 == 0 ? 'M' : 'F';
            new Patient(id, firstName, lastName, gender);
        }
    }

    public void addPatientsToUnits() {

        Random rand = new Random();
        ArrayList<Unit2> unitsList = new ArrayList<>();
        ArrayList<Patient> patList = new ArrayList<>();

        for (String unitID : digiSystem.getMapOfUnits().keySet()) {
            unitsList.add(digiSystem.getMapOfUnits().get(unitID));
        }

        for (String patientID : digiSystem.getMapOfPatients().keySet()) {
            patList.add(digiSystem.getMapOfPatients().get(patientID));
        }

        for (int i = 0; i < 10000; i++) {
            int patInd = rand.nextInt(patList.size());
            int unitInd = rand.nextInt(unitsList.size());
            unitsList.get(unitInd).addPatientToUnit(patList.get(patInd).getPatientID());
        }
    }

    public void makeHeightWeightBMIDOB() {

        Collection<Patient> patientList = digiSystem.getMapOfPatients().values();
        Random rand = new Random();

        patientList.stream().forEach(e -> {
            e.setHeight(rand.nextInt(30) + 150);
            e.setWeight(rand.nextInt(28) + 54);
            //e.setBMI(e.getWeight() / Math.pow((e.getHeight()/100.0),2));
            e.setDOB(LocalDate.of(rand.nextInt(80)+1920, rand.nextInt(11)+1, rand.nextInt(28)+1));
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

        digiSystem.getMapOfPatients().values().stream().forEach(e -> {

            String[] add = addresses.get(rand.nextInt(addresses.size()));
            e.getContactInformation().setStreetNumber(add[0]);
            e.getContactInformation().setStreetName(add[1]);
            e.getContactInformation().setCity(add[2]);
            e.getContactInformation().setPostalCode(add[3]);
            e.getContactInformation().setProvince(add[4]);
            e.getContactInformation().setCountry("Canada");
            e.getContactInformation().setEmail(e.getFirstName().toLowerCase() + "." + e.getLastName().toLowerCase() + "@gmail.com");
            e.getContactInformation().setPhoneNumber(phoneNumbers.get(rand.nextInt(phoneNumbers.size())));
        });

    }

    public void makeMedicalNotes() {

        Random random = new Random();

        digiSystem.getMapOfPatients().values().forEach(patient -> {

            for(int i = 0; i < random.nextInt(10)+1 ; i++) {

                MedicalNote medicalNote = new MedicalNote();
                medicalNote.setNoteID(patient.getMedicalNotes().size() + 1);
                medicalNote.setTemperature(random.nextDouble() * 2.5 + 35.5);
                medicalNote.setO2Sat(random.nextInt(8) + 93);
                medicalNote.setPulse(random.nextInt(71) + 50);
                medicalNote.setBP(random.nextInt(50) + 110, random.nextInt(30) + 55);
                medicalNote.setDeleted((random.nextInt(21) > 19) ? true : false);
                medicalNote.setDate(LocalDate.of(random.nextInt(30) + 1990, random.nextInt(12) + 1,
                        random.nextInt(26) + 1));

                String note = getNoteText(random, patient);
                medicalNote.setNote(note);
                patient.addMedicalNote(medicalNote);
            }
        });


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
}
