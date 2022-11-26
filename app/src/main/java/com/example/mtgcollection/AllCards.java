package com.example.mtgcollection;


import static com.example.mtgcollection.CardDatabaseHelper.KEY_ID;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class AllCards extends AppCompatActivity {

    CardDatabaseManager cdm = new CardDatabaseManager(this);
    byte [] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list);

        ListView lv_cards = findViewById(R.id.lv_cards);

        cdm.open();
        Cursor cards = cdm.getAllCards();

        cards.moveToFirst();
        CardCursorAdapter cca = new CardCursorAdapter(this, cards);
        lv_cards.setAdapter(cca);

        lv_cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent onCard = new Intent(AllCards.this, CardClicked.class);
                cards.move(position);
                String name = cards.getString(cards.getColumnIndexOrThrow(KEY_NAME));
                byte[] image = cards.getBlob(cards.getColumnIndexOrThrow(KEY_IMAGE));
                onCard.putExtra("id", id);
                onCard.putExtra("name", name);
                onCard.putExtra("image", image);
                startActivity(onCard);
            }
        });

        cdm.close();

        ImageButton btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(AllCards.this, AddCard.class);
                startActivity(addCard);
                finish();
            }
        });
    }
}