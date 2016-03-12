package com.example.shayanths.represent;

import java.io.Serializable;

/**
 * Created by ShayanthS on 3/2/16.
 */
public class PersonData implements Serializable {


    String name;
    String email;
    String endDate;
    String party;
    String website;
    String twitterQuote;
    String[] committees;
    String[] bills;
    String cand_id;
    String image;
    int id_;

    public PersonData(String name, String email, String endDate, String party, String website,  String twitterQuote, String[] committees, String[] bills,  String image, String cand_id,  int id_) {
        this.name = name;
        this.email = email;
        this.endDate = endDate;
        this.party = party;
        this.website = website;
        this.twitterQuote = twitterQuote;
        this.committees = committees;
        this.bills = bills;
        this.image = image;
        this.cand_id = cand_id;
        this.id_ = id_;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getParty() { return party; }

    public String getWebsite() {
        return website;
    }

    public String getTwitterQuote(){
        return twitterQuote;
    }

    public String[] getCommittees() {
        return committees;
    }
    public String[] getBills() {
        return bills;
    }

    public String getImage() {
        return image;
    }

    public String getCand_id() {return cand_id;}

    public int getId() {
        return id_;
    }

    public void setCommittees(String[] committees){
        this.committees = committees;
    }

    public void setBills(String[] bills) {
        this.bills = bills;
    }
}