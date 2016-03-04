package com.example.shayanths.represent;

/**
 * Created by ShayanthS on 3/1/16.
 */
public class PersonData {


    String name;
    String email;
    String endDate;
    String party;
    String website;
    String twitterQuote;
    String[] committees;
    String[] bills;
    int image;
    int id_;

    public PersonData(String name, String email, String endDate, String party, String website,  String twitterQuote, String[] committees, String[] bills,  int image, int id_) {
        this.name = name;
        this.email = email;
        this.endDate = endDate;
        this.party = party;
        this.website = website;
        this.twitterQuote = twitterQuote;
        this.committees = committees;
        this.bills = bills;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}
