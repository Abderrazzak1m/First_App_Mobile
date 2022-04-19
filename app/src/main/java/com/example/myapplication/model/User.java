package com.example.myapplication.model;

public class User {
    private String mFirstName;

    public User(String firstName) {
        mFirstName = firstName;
    }

    public User() {
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String toString() {
        String s = "user{" + this.mFirstName + '\'' +
                '}';
        return s;

    }
}
