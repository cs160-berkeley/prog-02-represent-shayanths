package com.example.shayanths.represent;

/**
 * Created by ShayanthS on 3/2/16.
 */
public class PersonData {


    String name;
    String email;
    String party;
    int image;
    int id_;

    public PersonData(String name, String email, String party, int image, int id_) {
        this.name = name;
        this.email = email;
        this.party = party;
        this.image = image;
        this.id_ = id_;
    }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getParty() { return party; }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}
