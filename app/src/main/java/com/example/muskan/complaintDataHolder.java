package com.example.muskan;

public class complaintDataHolder {
    private String image,name, age,sort;
    private long complaintID;
    private String latititude,longitude,typelabel;

    public complaintDataHolder() {
    }

    public complaintDataHolder(String image, String name, String age, String sort, long complaintID, String latititude, String longitude, String typelabel) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.sort = sort;
        this.complaintID = complaintID;
        this.latititude = latititude;
        this.longitude = longitude;
        this.typelabel = typelabel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTypelabel() {
        return typelabel;
    }

    public void setTypelabel(String typelabel) {
        this.typelabel = typelabel;
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
