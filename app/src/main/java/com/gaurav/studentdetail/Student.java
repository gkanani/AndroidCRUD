package com.gaurav.studentdetail;

public class Student {

    String id;
    String name,city,email,stream;

    public Student(String id, String name, String city, String email, String stream) {
        this.id=id;
        this.name = name;
        this.city = city;
        this.email = email;
        this.stream = stream;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getStream() {
        return stream;
    }
    public String getId() {
        return id;
    }
}
