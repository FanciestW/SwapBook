package me.williamlin.swapbook;

/**
 * Created by william on 11/4/17.
 */

public class User {
    private String uid, firstName, lastName, university, email;

    public User(){}

    public User(String uid, String fName, String lName, String univ, String email){
        this.uid = uid;
        this. firstName = fName;
        this.lastName = lName;
        this.university = univ;
        this.email = email;
    }

    public String getUID(){ return uid; }
    public String getFirstName(){ return firstName; }
    public String getLastName(){ return lastName; }
    public String getUniversity(){ return university; }
    public String getEmail(){ return email; }
}
