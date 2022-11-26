package com.example.mtgcollection;

import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AllDecks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_list);

        CardDatabaseManager cdm = new CardDatabaseManager(this);
        ListView lv_decks = findViewById(R.id.lv_decks);

        String[] data = new String[]{KEY_NAME};
        int[] rowIDs = new int[]{R.id.tv_name};

        cdm.open();
        Cursor decks = cdm.getAllDecks();
        decks.moveToFirst();
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.deck_row, decks, data, rowIDs);
        lv_decks.setAdapter(sca);

        lv_decks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent onDeck = new Intent(AllDecks.this, DeckClicked.class);
                decks.move(position);
                String name = decks.getString(decks.getColumnIndexOrThrow(KEY_NAME));
                onDeck.putExtra("id", id);
                onDeck.putExtra("name", name);
                startActivity(onDeck);
            }
        });

        AppCompatButton btn_cards = findViewById(R.id.btn_cards);
        btn_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ImageButton btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDeck = new Intent(AllDecks.this, AddDeck.class);
                startActivity(addDeck);
                finish();
            }
        });

    }
}
