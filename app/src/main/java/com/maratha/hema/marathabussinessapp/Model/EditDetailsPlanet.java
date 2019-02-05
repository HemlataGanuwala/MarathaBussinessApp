package com.maratha.hema.marathabussinessapp.Model;

public class EditDetailsPlanet {

    private String CustomerId;
    private String Contact;
    private String PersonName;


    public EditDetailsPlanet(String customerId, String contact, String personName)
    {
//
        this.CustomerId = customerId;
        this.Contact = contact;
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

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }
}
