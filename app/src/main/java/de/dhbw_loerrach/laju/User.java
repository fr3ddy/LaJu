package de.dhbw_loerrach.laju;

/**
 * Created by Frederik on 26.05.2015.
 */
public class User {

    private static User userInstance = null;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    private User(String username , String firstname , String lastname, String email){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public void login(String username , String firstname , String lastname , String email){
        userInstance = new User(username, firstname , lastname , email);
    }

    public void logout(){
        userInstance = null;
    }

    public boolean loggedIn(){
        if(userInstance == null){
            return true;
        }else{
            return false;
        }
    }

    public static User getInstance(){
        return userInstance;
    }

}
