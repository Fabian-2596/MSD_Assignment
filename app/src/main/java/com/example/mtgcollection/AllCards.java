package com.example.mtgcollection;

import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class AllCards extends AppCompatActivity {


    byte[] image;
    int RESULT_CODE = 100;
    CardDatabaseManager cdm;
    ListView lv_cards;
    Cursor cards;
    CardCursorAdapter cca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        updateList();

        lv_cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent onCard = new Intent(AllCards.this, CardClicked.class);
                Cursor card = (Cursor) cca.getItem(position);
                String name = card.getString(card.getColumnIndexOrThrow(KEY_NAME));
                byte[] image = card.getBlob(card.getColumnIndexOrThrow(KEY_IMAGE));
                onCard.putExtra("id", id);
                onCard.putExtra("name", name);
                onCard.putExtra("image", image);
                startActivityForResult(onCard, RESULT_CODE);
            }
        });

        ImageButton btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(AllCards.this, AddCard.class);
                startActivityForResult(addCard, RESULT_CODE);
            }
        });

        AppCompatButton btn_decks = findViewById(R.id.btn_decks);
        btn_decks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent decks = new Intent(AllCards.this, AllDecks.class);
                startActivity(decks);
            }
        });

        ImageButton btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void updateList(){
        cdm = new CardDatabaseManager(this);
        lv_cards = findViewById(R.id.lv_cards);
        cdm.open();
        cards = cdm.getAllCards();
        cca = new CardCursorAdapter(this, cards);
        lv_cards.setAdapter(cca);
        cdm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateList();
    }
}