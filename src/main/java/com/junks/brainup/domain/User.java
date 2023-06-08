package com.junks.brainup.domain;

public class User {

    private String firstName;
    private String lastName;
    private String password;
    private String country;
    private String login;
    private String sex;
    private int scoreWon;
    private int scoreLost;

    public User(String firstName, String lastName, String password, String country, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.country = country;
        this.login = login;
        scoreWon = 0;
        scoreLost = 0;
    }

    public void setScoreWon(int scoreWon) {
        this.scoreWon = scoreWon;
    }

    public void setScoreLost(int scoreLost) {
        this.scoreLost = scoreLost;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getLogin() {
        return login;
    }

    public String getSex() {
        return sex;
    }


    public int getScoreWon() {
        return scoreWon;
    }

    public int getScoreLost() {
        return scoreLost;
    }




    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", login='" + login + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
