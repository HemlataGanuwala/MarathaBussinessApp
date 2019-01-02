package com.maratha.hema.marathabussinessapp;

public class RegListPlanet {

    private String CustomerId;
    private String PersonName;
    private String Location;
//    private String TypeofBusiness;


    public RegListPlanet(String customerId, String personName, String location)
    {
//
        this.CustomerId = customerId;
        this.PersonName = personName;
        this.Location = location;
//        this.TypeofBusiness = typeofBusiness;

    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }


}
