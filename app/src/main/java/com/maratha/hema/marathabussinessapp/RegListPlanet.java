package com.maratha.hema.marathabussinessapp;

public class RegListPlanet {

    private String CustomerId;
    private String Contact;
    private String Location;
//    private String TypeofBusiness;


    public RegListPlanet(String customerId, String contact, String location)
    {
//
        this.CustomerId = customerId;
        this.Contact = contact;
        this.Location = location;
//        this.TypeofBusiness = typeofBusiness;

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


}
