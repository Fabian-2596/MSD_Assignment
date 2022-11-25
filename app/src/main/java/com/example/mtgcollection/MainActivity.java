package com.example.mtgcollection;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    CardDatabaseManager cdm = new CardDatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list);

        ListView lv_cards = findViewById(R.id.lv_cards);

        cdm.open();
        Cursor cards = cdm.getAllCards();

        if (cards != null) {
            cards.moveToFirst();
            CardCursorAdapter cca = new CardCursorAdapter(this, cards);
            lv_cards.setAdapter(cca);
        }
        cdm.close();

        ImageButton btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(MainActivity.this, AddCard.class);
                startActivity(addCard);
                finish();
            }
        });
    }
}