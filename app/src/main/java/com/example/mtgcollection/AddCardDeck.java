package com.example.mtgcollection;

import static com.example.mtgcollection.CardDatabaseHelper.KEY_ID;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddCardDeck extends AppCompatActivity {
    CardDatabaseManager cdm;
    Cursor cards;
    ListView lv_cards;
    CardCursorAdapter cca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cardtodeck);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        long deckId = intent.getLongExtra("deckId", 0);

        cdm = new CardDatabaseManager(this);
        cdm.open();
        cards = cdm.getAllCards();
        cca = new CardCursorAdapter(this, cards);
        lv_cards = findViewById(R.id.lv_cards);
        lv_cards.setAdapter(cca);
        cdm.close();

        lv_cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cdm.open();
                cdm.addCardtoDeck(id, deckId);
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
}
