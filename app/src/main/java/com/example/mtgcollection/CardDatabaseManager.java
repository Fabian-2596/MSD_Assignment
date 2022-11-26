
package com.example.mtgcollection;


import static com.example.mtgcollection.CardDatabaseHelper.KEY_COLOR;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_COST;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_ID;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_POWER;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_TOUGHNESS;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_TYPE;
import static com.example.mtgcollection.CardDatabaseHelper.TABLE_CARD;
import static com.example.mtgcollection.CardDatabaseHelper.TABLE_DECK;
import static com.example.mtgcollection.CardDatabaseHelper.TABLE_INDECK;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


    public class CardDatabaseManager
    {
        Context context;
        private CardDatabaseHelper CarddbHelper;
        private SQLiteDatabase database;


        public CardDatabaseManager(Context context)
        {
            this.context = context;

        }

        public CardDatabaseManager open() throws SQLException {
            CarddbHelper = new CardDatabaseHelper(context);
            database = CarddbHelper.getWritableDatabase();
            return this;
        }

        public void close() {
            CarddbHelper.close();
        }

        void addCard(Card card) {

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, card.getName());
            values.put(KEY_COST, card.getCost());
            values.put(KEY_POWER, card.getPower());
            values.put(KEY_TOUGHNESS, card.getToughness());
            values.put(KEY_TYPE, card.getType());
            values.put(KEY_COLOR, card.getColor());
            values.put(KEY_IMAGE, card.getImage());

            database.insert(TABLE_CARD, null, values);

        }

        void addDeck(Deck deck) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, deck.getName());

            database.insert(TABLE_DECK, null, values);
        }

        void addCardtoDeck(int cardId, int deckId) {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, cardId);
            values.put(KEY_ID, deckId);

            database.insert(TABLE_INDECK, null, values);
        }


        Card getCard(int id) {

            Cursor cursor = database.query(TABLE_CARD, new String[] { KEY_ID,
                            KEY_NAME, KEY_COST, KEY_POWER, KEY_TOUGHNESS, KEY_TYPE, KEY_COLOR, KEY_IMAGE }, KEY_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Card card = new Card(
                    cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getBlob(7));
            return card;
        }

        Deck getDeck(int id) {
            Cursor cursor = database.query(TABLE_DECK, new String[] { KEY_ID,
                            KEY_NAME}, KEY_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Deck deck = new Deck(
                    cursor.getString(1));
            return deck;
        }

        public Cursor getAllCards() {
            String selectQuery = "SELECT  * FROM " + TABLE_CARD;
            Cursor cardList = database.rawQuery(selectQuery, null);
            return cardList;
        }

        public Cursor getAllDecks() {
            String selectQuery = "SELECT  * FROM " + TABLE_DECK;
            Cursor deckList = database.rawQuery(selectQuery, null);
            return deckList;
        }

        public Cursor getCardsinDeck(int deckId) {
            String selectQuery = "SELECT * FROM" + TABLE_DECK + " WHERE " + KEY_ID + " = " + String.valueOf(deckId);
            Cursor inDeckList = database.rawQuery(selectQuery, null);
            return inDeckList;
        }


        public int updateCard(Card card) {

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, card.getName());
            values.put(KEY_COST, card.getCost());
            values.put(KEY_POWER, card.getPower());
            values.put(KEY_TOUGHNESS, card.getToughness());
            values.put(KEY_TYPE, card.getType());
            values.put(KEY_COLOR, card.getColor());
            values.put(KEY_IMAGE, card.getImage());

            return database.update(TABLE_CARD, values, KEY_ID + " = ?",
                    new String[] { String.valueOf(card.getId()) });
        }

        public void deleteCard(long id) {

            database.delete(TABLE_CARD, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });
            database.close();
        }

        public int getCardCount() {
            String countQuery = "SELECT  * FROM " + TABLE_CARD;
            Cursor cursor = database.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }


    }

