

package com.example.mtgcollection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CardDatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mtgc";

    public static final String TABLE_CARD = "card";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COST = "cost";
    public static final String KEY_POWER = "power";
    public static final String KEY_TOUGHNESS = "toughness";
    public static final String KEY_TYPE = "type";
    public static final String KEY_COLOR = "color";
    public static final String KEY_IMAGE = "image";

    public static final String TABLE_DECK = "deck";

    public static final String TABLE_INDECK = "indeck";
    public static final String KEY_DECKID = "deckid";
    public static final String KEY_CARDID = "cardid";


    public CardDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MTGC_TABLE_CARD = "CREATE TABLE " + TABLE_CARD + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_COST + " TEXT,"
                + KEY_POWER + " TEXT,"
                + KEY_TOUGHNESS + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_COLOR + " TEXT,"
                + KEY_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_MTGC_TABLE_CARD);

        String CREATE_MTGC_TABLE_DECK = "CREATE TABLE " + TABLE_DECK + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_MTGC_TABLE_DECK);

        String CREATE_MTGC_TABLE_INDECK = "CREATE TABLE " + TABLE_INDECK + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CARDID + " INTEGER,"
                + KEY_DECKID + " INTEGER,"
                + "FOREIGN KEY ("+KEY_ID+") REFERENCES "+TABLE_CARD+"("+KEY_ID+"),"
                + "FOREIGN KEY ("+KEY_DECKID+") REFERENCES "+TABLE_DECK+"("+KEY_ID+")"
                + ")";
        db.execSQL(CREATE_MTGC_TABLE_INDECK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDECK);
        onCreate(db);
    }
}