package com.example.mtgcollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


public class DeckClicked extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_deck);

        CardDatabaseManager cdm = new CardDatabaseManager(this);

        Intent i = getIntent();
        long id = i.getLongExtra("id", 0);
        String name = i.getStringExtra("name");

        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);

        AppCompatButton btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdm.open();
                cdm.deleteDeck(id);
                cdm.close();
                Intent deckList = new Intent(DeckClicked.this, AllDecks.class);
                startActivity(deckList);
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
