/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg5.backend;

import java.util.*;
import java.time.*;
import java.time.temporal.*;

import lab.pkg5.constants.*;
/**
 *
 * @author ahmadyasserhamad
 */
public class TrainerRole implements Role {

    private MemberDatabase memberDatabase = new MemberDatabase(FileNames.MEMBER_FILENAME);
    private ClassDatabase classDatabase = new ClassDatabase(FileNames.CLASS_FILENAME);
    private MemberClassRegistrationDatabase registrationDatabase = new MemberClassRegistrationDatabase(FileNames.REGISTRATION_FILENAME);

    public boolean addMember(String memberID, String name, String membershipType, String email, String phoneNumber, String status) {
        if (memberDatabase.contains(memberID)) {
//            System.out.println("Member already exists.");
            return false;
        } else {
            Member newMember = new Member(memberID, name, membershipType, email, phoneNumber, status);
            memberDatabase.insertRecord(newMember);
            return true;
        }
    }

    public ArrayList<Member> getListOfMembers() {
        return memberDatabase.returnAllRecords();
    }

    public boolean addClass(String classID, String className, String trainerID, int duration, int maxParticipants) {
        if (classDatabase.contains(classID)) {
//            System.out.println("Class already exists.");
            return false;
        } else {
            Class newClass = new Class(classID, className, trainerID, duration, maxParticipants);
            classDatabase.insertRecord(newClass);
            return true;
        }
    }

    public ArrayList<Class> getListOfClasses() {
        return classDatabase.returnAllRecords();
    }

    public int registerMemberForClass(String memberID, String classID, LocalDate registrationDate) {
        int difference = (int) ChronoUnit.DAYS.between(LocalDate.now(),registrationDate);
        Class record = (Class) classDatabase.getRecord(classID);
        if (classDatabase.contains(classID) && record.getAvailableSeats() > 0 && difference >= 0) {
            registrationDatabase.insertRecord(registrationDatabase.createRecordFrom(memberID + ", " + classID + ", " + registrationDate + ", " + "active"));
            record.setAvailableSeats(record.getAvailableSeats() - 1);
            return 1;
        }
        if (!classDatabase.contains(classID)) {
//            System.out.println("Class does not exist.");
            return 2;
        }
        if (!(record.getAvailableSeats() > 0)) {
//            System.out.println("No seats available in class.");
            return 3;
        }
        return 4;
    }

    public boolean cancelRegistration(String memberID, String classID) {
        MemberClassRegistration recordM = (MemberClassRegistration) registrationDatabase.getRecord(memberID.concat(classID));
        Class recordC = (Class) classDatabase.getRecord(classID);
        if (registrationDatabase.contains(memberID.concat(classID))) {
            long difference = Math.abs(ChronoUnit.DAYS.between(recordM.getRegistrationDate(), LocalDate.now()));
            String str = registrationDatabase.getRecord(memberID.concat(classID)).lineRepresentation();
            String[] divided = str.split(", ");
            if (difference <= 3 && "active".equals(divided[3])) {
                recordM.setRegistrationStatus("canceled");
                recordC.setAvailableSeats(recordC.getAvailableSeats() + 1);
//                System.out.println("Refund issued to member: " + memberID);
                return true;
            }
//            System.out.println("Cannot cancel registration. The class is in the coming three days or is already cancelled.");
            return false;
        }
//        System.out.println("Class does not exist.");
        return false;
    }

    public ArrayList<MemberClassRegistration> getListOfRegistrations() {
        return registrationDatabase.returnAllRecords();
    }

    @Override
    public void logout(){
        memberDatabase.saveToFile();
        classDatabase.saveToFile();
        registrationDatabase.saveToFile();
    }

}
