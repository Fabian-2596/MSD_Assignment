
package com.example.mtgcollection;


import static com.example.mtgcollection.CardDatabaseHelper.KEY_CARDID;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_COLOR;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_COST;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_DECKID;
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

        void addCardtoDeck(long cardId, long deckId) {
            ContentValues values = new ContentValues();
            values.put(KEY_CARDID, cardId);
            values.put(KEY_DECKID, deckId);

            database.insert(TABLE_INDECK, null, values);
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

        public Cursor getCardsinDeck(long deckId) {
            String id = String.valueOf(deckId);
            String selectQuery = "SELECT * FROM " + TABLE_INDECK + " INNER JOIN " + TABLE_CARD + " ON " + TABLE_INDECK + "." + KEY_CARDID + " = " + TABLE_CARD + "." + KEY_ID + " WHERE " + KEY_DECKID + " = " + id;
            Cursor inDeckList = database.rawQuery(selectQuery, null);
            return inDeckList;
        }

        public void deleteCard(long id) {
            database.delete(TABLE_CARD, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });
            database.delete(TABLE_INDECK, KEY_CARDID + " = ?",
                    new String[] { String.valueOf(id) });
            database.close();
        }

        public void deleteDeck(long id) {
            database.delete(TABLE_DECK, KEY_ID + " = ?",
                    new String[] { String.valueOf(id) });
            database.delete(TABLE_INDECK, KEY_DECKID + " = ?", new String[] { String.valueOf(id)});
            database.close();
        }
    }

