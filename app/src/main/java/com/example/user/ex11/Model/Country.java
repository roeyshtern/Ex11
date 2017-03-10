package com.example.user.ex11.Model;

import java.util.Comparator;

/**
 * Created by User on 1/20/2017.
 */

public class Country{
    private String name;
    private String flag;
    private String details;
    private String anthem;
    private String shortDetails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAnthem() {
        return anthem;
    }

    public void setAnthem(String anthem) {
        this.anthem = anthem;
    }

    public String getShortDetails() {
        return shortDetails;
    }

    public void setShortDetails(String shortDetails) {
        this.shortDetails = shortDetails;
    }

    public String toString(){return this.name;}

    public int compare(Country other){
        return this.getName().compareTo(other.getName());
    }
}