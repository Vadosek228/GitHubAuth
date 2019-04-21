package ru.vladislav_akulinin.githubauth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserList {

    private ArrayList<User> user = new ArrayList<>();

    public ArrayList<User> getUser(){
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }
}
