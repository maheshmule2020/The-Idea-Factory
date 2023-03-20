package com.krishna.iparker;

import java.io.Serializable;

public class ModelClass implements Serializable {
    String id,username,vehicle_no,pname,availslots,rate,ptype,details,confirmed,
           acc_balance;


    public String getAcc_balance() {
        return acc_balance;
    }

    public void setAcc_balance(String acc_balance) {
        this.acc_balance = acc_balance;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAvailslots() {
        return availslots;
    }

    public void setAvailslots(String availslots) {
        this.availslots = availslots;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
