package controller;

import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

public class makeData {

    DigiSystem digiSystem = DigiSystem.getInstance();

    public void debug() throws FileNotFoundException {

        addUsers();
        makeUnits();
        makePatients();
        addPatientsToUnits();
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
        System.out.println(new File("").getAbsolutePath());
        File names = new File("src/controller/Names.txt");
        Scanner reader = new Scanner(names);

        while (reader.hasNext()) {
            namesList.add(reader.next());
        }

        Random rand = new Random();
        for (int i = 0; i < 500; i++) {
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

        for (int i = 0; i < 25000; i++) {
            int patInd = rand.nextInt(patList.size());
            int unitInd = rand.nextInt(unitsList.size());
            unitsList.get(unitInd).addPatientToUnit(patList.get(patInd).getPatientID());
        }
    }
}
