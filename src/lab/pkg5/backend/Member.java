/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg5.backend;

/**
 *
 * @author ahmadyasserhamad
 */
public class Member implements BaseClass{

    private String memberId;
    private String name;
    private String membershipType;
    private String email;
    private String phoneNumber;
    private String status;

    public Member(String memberId, String name, String membershipType, String email, String phoneNumber, String status) {
        this.memberId = memberId;
        this.name = name;
        this.membershipType = membershipType;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    @Override
    public String lineRepresentation() {
        return memberId + ", " + name + ", " + membershipType + ", " + email + ", " + phoneNumber + ", " + status;
    }
    
    @Override
    public String getSearchKey(){
        return memberId;
    }

}
