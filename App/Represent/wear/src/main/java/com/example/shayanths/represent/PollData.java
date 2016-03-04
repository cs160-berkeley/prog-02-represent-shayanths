package com.example.shayanths.represent;

/**
 * Created by ShayanthS on 3/3/16.
 */
public class PollData {

    String county;
    String state;
    String president0;
    String president1;
    String vote0;
    String vote1;
    String zipCode;

    public PollData(String county, String state, String president0, String president1, String vote0, String vote1, String zipCode){
        this.county = county;
        this.state = state;
        this.president0 = president0;
        this.president1 = president1;
        this.vote0 = vote0;
        this.vote1 = vote1;
        this.zipCode = zipCode;
    }

    public String getCounty() { return county;}
    public String getState() {return state;}
    public String getPresident0() {return president0;}
    public String getPresident1() {return president1;}
    public String getVote0() {return vote0;}
    public String getVote1() {return vote1;}
    public String getZipCode() {return zipCode;}

}
