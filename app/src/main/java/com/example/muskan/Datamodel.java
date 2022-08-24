package com.example.muskan;

import com.google.firebase.database.PropertyName;

public class Datamodel {
   private String name,age,sort,image;
    private long complaintID;
    public Datamodel() {
    }


    public Datamodel(String name, String age, String sort, String image, Long complaintID) {
        this.name = name;
        this.age = age;
        this.sort = sort;
        this.image = image;
        this.complaintID = complaintID;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }
    @PropertyName("name")

    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("age")
    public String getAge() {
        return age;
    }

    @PropertyName("age")
    public void setAge(String age) {
        this.age = age;
    }

    @PropertyName("sort")
    public String getSort() {
        return sort;
    }

    @PropertyName("sort")
    public void setSort(String type) {
        this.sort = type;
    }

    @PropertyName("image")
    public String getImage() {
        return image;
    }

    @PropertyName("image")
    public void setImage(String image) {
        this.image = image;
    }

    @PropertyName("complaintID")
    public long getComplaintID() {
        return complaintID;
    }

    @PropertyName("complaintID")
    public void setComplaintID(Long complaintID) {
        this.complaintID = complaintID;
    }
}