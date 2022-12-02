package com.example.mtgcollection;

import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AllDecks extends AppCompatActivity {

    int REQUEST_CODE = 200;
    CardDatabaseManager cdm;
    ListView lv_decks;
    Cursor decks;
    SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        updateList();

        lv_decks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent onDeck = new Intent(AllDecks.this, DeckClicked.class);
                Cursor deck = (Cursor) sca.getItem(position);
                String name = deck.getString(deck.getColumnIndexOrThrow(KEY_NAME));
                onDeck.putExtra("id", id);
                onDeck.putExtra("name", name);
                startActivityForResult(onDeck, REQUEST_CODE);
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
                startActivityForResult(addDeck, REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateList();
    }

    private void updateList(){
        String[] data = new String[]{KEY_NAME};
        int[] rowIDs = new int[]{R.id.tv_name};
        cdm = new CardDatabaseManager(this);
        lv_decks = findViewById(R.id.lv_decks);
        cdm.open();
        decks = cdm.getAllDecks();
        decks.moveToFirst();
        sca = new SimpleCursorAdapter(this, R.layout.deck_row, decks, data, rowIDs);
        lv_decks.setAdapter(sca);
        cdm.close();
    }

}
