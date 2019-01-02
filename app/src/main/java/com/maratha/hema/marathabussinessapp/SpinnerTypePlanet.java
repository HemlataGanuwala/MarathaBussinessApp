package com.maratha.hema.marathabussinessapp;

public class SpinnerTypePlanet {

    private String OccupationId;
    private String Occupationname;

    public SpinnerTypePlanet(String occupationname)
    {
//        this.AgentId = agentId;
        this.Occupationname = occupationname;
    }

    public String getOccupationname() {
        return Occupationname;
    }

    public void setOccupationname(String occupationname) {
        Occupationname = occupationname;
    }
}
