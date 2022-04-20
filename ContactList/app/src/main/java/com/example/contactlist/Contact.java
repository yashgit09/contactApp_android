package com.example.contactlist;

public class Contact implements Comparable<Contact>{
    // variables for contact name and contact number.
    private int _id;
    private String name;
    private String number;

    // constructor
    public Contact(int id, String name, String number) {
        this._id = id;
        this.name = name;
        this.number = number;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Contact() {

    }

    // Getter methods
    public int getID(){
        return this._id;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    // Setter methods
    public void setID(int id){
        this._id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // overriding the compareTo method of Comparable class
    @Override public int compareTo(Contact contact) {
        //  For Ascending order
        return this.name.compareTo(contact.getName());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
