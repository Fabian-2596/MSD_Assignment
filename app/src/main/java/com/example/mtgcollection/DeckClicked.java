package com.example.mtgcollection;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.List;


public class DeckClicked extends AppCompatActivity {

    CardDatabaseManager cdm;
    Cursor cardInDeck;
    CardCursorAdapter cca;
    ListView lv_cards;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_deck);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent i = getIntent();
        id = i.getLongExtra("id", 0);
        String name = i.getStringExtra("name");

        updateList();

        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);

        AppCompatButton btn_addcard = findViewById(R.id.btn_add);
        btn_addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCard = new Intent(DeckClicked.this, AddCardDeck.class);
                addCard.putExtra("deckId", id);
                startActivityForResult(addCard, 42);
            }
        });

        AppCompatButton btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdm.open();
                cdm.deleteDeck(id);
                cdm.close();
                finish();
            }
        });
        ImageButton btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateList(){
        cdm = new CardDatabaseManager(this);
        cdm.open();
        cardInDeck = cdm.getCardsinDeck(id);
        cca = new CardCursorAdapter(this, cardInDeck);
        lv_cards = findViewById(R.id.lv_cardsindeck);
        lv_cards.setAdapter(cca);
        cdm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateList();
    }
}
