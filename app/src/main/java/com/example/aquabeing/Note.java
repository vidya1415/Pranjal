package com.example.aquabeing;

public class Note {
    private  String name;
    private String address;

    public Note(){
        //no const needed.
    }

    public Note(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}

