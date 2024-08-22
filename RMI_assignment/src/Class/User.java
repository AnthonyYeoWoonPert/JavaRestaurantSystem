/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import Enum.Role;
import java.io.Serializable;



public class User implements Serializable {
    private static final long serialVersionUID = -3193738285965303013L;
    
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private Role role;
    private String passport;
   

    public User(String username, String password, String firstName, String lastName, String passportNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
    }
    
    public User(String username, String firstName, String lastName, String passportNumber, Role role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
        this.role = role;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public User(String userID, String username, String firstName, String lastName, String passport, Role role) {
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.role = role;
//        this.passport = passport;
//        this.userID = userID;
//    }



    public String getPassport() {
        return passport;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", passportNumber=" + passportNumber + ", role=" + role + '}';
    }

    
}
