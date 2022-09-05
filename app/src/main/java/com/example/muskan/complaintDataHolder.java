package com.example.muskan;

public class complaintDataHolder {
    private String image,name, age,sort;
    private long complaintID;
    private String latititude,longitude;

    public complaintDataHolder() {
    }

    public complaintDataHolder(String name, String age, String sort, long complaintID, String latititude, String longitude) {

        this.name = name;
        this.age = age;
        this.sort = sort;
        this.complaintID = complaintID;
        this.latititude = latititude;
        this.longitude = longitude;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public long getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(long complaintID) {
        this.complaintID = complaintID;
    }

    public String getLatititude() {
        return latititude;
    }


    public void setLatititude(String latititude) {
        this.latititude = latititude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
