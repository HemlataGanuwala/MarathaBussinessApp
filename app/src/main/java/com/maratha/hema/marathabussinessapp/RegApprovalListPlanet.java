package com.maratha.hema.marathabussinessapp;

public class RegApprovalListPlanet {

    private String CustomerId;
    private String Contact;
    private String PersonName;
    private boolean Checked;


    public RegApprovalListPlanet(String customerId, String contact, String personName,boolean checked)
    {
//
        this.CustomerId = customerId;
        this.Contact = contact;
        this.PersonName = personName;
        this.Checked = checked;

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

    public boolean getChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        this.Checked= checked;
    }
}
