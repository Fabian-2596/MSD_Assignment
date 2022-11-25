package com.example.mtgcollection;

public class Deck {

    private int id;
    private String name;

    public Deck(){}

    public Deck(int id, String n){
        this.id = id;
        this.name = n;

    }

    public int getId() {return this.id; }
    public String getName(){
        return this.name;
    }
}
