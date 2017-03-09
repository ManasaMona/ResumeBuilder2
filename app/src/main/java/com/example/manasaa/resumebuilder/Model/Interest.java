package com.example.manasaa.resumebuilder.Model;

/**
 * Created by manasa.a on 06-03-2017.
 */

public class Interest {
    private int user_id;
    private String interestName;

    public Interest (){
    }
    public Interest(int user_id, String interestName) {
        this.user_id = user_id;
        this.interestName = interestName;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getInterestName() {
        return interestName;
    }
    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

}
