package controller;

import model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

public class Debugging {

    DigiSystem digiSystem = DigiSystem.getInstance();
    Scanner reader;

    public void debug() throws FileNotFoundException {

        AUser user = new AUser("", "");
        digiSystem.addUser(user);
        AUser user2 = new AUser("admin", "password");
        digiSystem.addUser(user2);

        new Unit2("123", "ER");
        new Unit2("234", "ICU");
        new Unit2("435", "Medicine");
        new Unit2("448", "Medicine2");
        new Unit2("501", "Cardiology");
        new Unit2("132", "Surgery");
        new Unit2("134", "Surgery2");
        new Unit2("934", "Ophthalmology");

        //make patients
        System.out.println(new File("").getAbsolutePath());
        ArrayList<String> namesList = new ArrayList<>();
        File names = new File("src/controller/Names.txt");
        reader = new Scanner(names);

        while(reader.hasNext()){
            namesList.add(reader.next());
        }

        Random rand = new Random();
        for(int i = 0; i < 25000; i++) {
            int index = rand.nextInt(namesList.size());
            String firstName = namesList.get(index);
            index = rand.nextInt(namesList.size());
            String lastName= namesList.get(index);
            String id = Integer.toString((i+1)*312);
            char gender = index % 2 == 0? 'M' : 'F';
            Patient newPatient = new Patient(id,firstName, lastName, gender);
        }

        //add patients to units
        ArrayList<Unit2> unitsList = new ArrayList<>();
        ArrayList<Patient> patList = new ArrayList<>();
        for(String unitID : digiSystem.getMapOfUnits().keySet()){
            unitsList.add(digiSystem.getMapOfUnits().get(unitID));
        }
        for(String patientID : digiSystem.getMapOfPatients().keySet()){
            patList.add(digiSystem.getMapOfPatients().get(patientID));
        }
        for(int i = 0; i < 25000; i++) {
            int patInd = rand.nextInt(patList.size());
            int unitInd = rand.nextInt(unitsList.size());
            unitsList.get(unitInd).addPatientToUnit(patList.get(patInd).getPatientID());
        }
    }
}
