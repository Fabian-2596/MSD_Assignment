package com.example.mtgcollection;

public class Deck {

    private int id;
    private String name;

    public Deck(){}


    public Deck(String n){
        this.name = n;

    }

    public int getId() {return this.id; }
    public String getName(){
        return this.name;
    }
}
