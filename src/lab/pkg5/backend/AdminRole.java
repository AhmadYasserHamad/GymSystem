/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg5.backend;

import java.io.IOException;
import java.util.*;
import lab.pkg5.constants.*;


/**
 *
 * @author ahmadyasserhamad
 */
public class AdminRole implements Role {

    private TrainerDatabase database = new TrainerDatabase(FileNames.TRAINER_FILENAME);

    public boolean addTrainer(String trainerId, String name, String email, String specialty, String phoneNumber) {
        if (database.contains(trainerId)) {
//            System.out.println("Trainer already exists.");
            return false;
        } else {
            Trainer newTrainer = new Trainer(trainerId, name, email, specialty, phoneNumber);
            database.insertRecord(newTrainer);
            return true;
        }
    }

    public ArrayList<Trainer> getListOfTrainers() {
        return database.returnAllRecords();
    }

    public boolean removeTrainer(String key) {
        if (database.contains(key)) {
            database.deleteRecord(key);
            return true;
        } else {
//            System.out.println("Trainer not found in database.");
            return false;
        }
    }

    @Override
    public void logout(){
            database.saveToFile();
    }

}
