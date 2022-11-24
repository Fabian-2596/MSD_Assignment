package com.example.mtgcollection;

public class Card {
    private int id;
    private String name;
    private int cost;
    private int power;
    private int toughness;
    private String type;
    private String color;
    byte[] image;

    public Card(){}

    public Card(String n, int c, int p, int t, String type, String color, byte[] i){
        this.name = n;
        this.cost = c;
        this.power = p;
        this.toughness = t;
        this.type = type;
        this.color = color;
        this.image = i;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getCost(){
        return this.cost;
    }

    public int getPower(){
        return this.power;
    }

    public int getToughness(){
        return this.toughness;
    }

    public String getType(){
        return this.type;
    }

    public String getColor(){
        return this.color;
    }

    public byte[] getImage(){
        return this.image;
    }

    public enum type {
        Land,
        Creature,
        Artifact,
        Enchantment,
        Planeswalker,
        Spell,
        Instant,
        Sorcery;
    }

    public enum color {
        Black,
        White,
        Red,
        Blue,
        Green;
    }

}
