package de.dhbw_loerrach.laju;

/**
 * Created by Frederik on 26.05.2015.
 */
public class User {

    private static User userInstance = null;
    public String username;
    public String firstname;
    public String lastname;
    public String email;

    private User(String username , String firstname , String lastname, String email){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public static void login(String username , String firstname , String lastname , String email){
        userInstance = new User(username, firstname , lastname , email);
    }

    public static void logout(){
        userInstance = null;
    }

    public static boolean isLoggedIn(){
        if(userInstance == null){
            return false;
        }else{
            return true;
        }
    }

    public static User getInstance() {
        return userInstance;
    }

}
