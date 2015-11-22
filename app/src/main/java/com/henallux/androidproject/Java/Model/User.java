package com.henallux.androidproject.Java.Model;

/**
 * Created by milou_000 on 25-10-15.
 */
public class User {

    private String identity, country, email, localite;

    public User(String id, String c, String emailAdr){
        identity = id;
        country = c;
        email = emailAdr;
    }
    public String getIdentity(){
        return identity;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getLocalite() {
        return localite;
    }
}
