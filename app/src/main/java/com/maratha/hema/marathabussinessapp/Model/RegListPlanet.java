package com.maratha.hema.marathabussinessapp.Model;

public class RegListPlanet {

    private String CustomerId;
    private String Contact;
    private String Location;
    private String PersonName;


    public RegListPlanet(String customerId, String contact, String location,String personName)
    {
//
        this.CustomerId = customerId;
        this.Contact = contact;
        this.Location = location;
        this.PersonName = personName;

    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }
}
