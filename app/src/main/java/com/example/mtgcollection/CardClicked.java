package com.example.mtgcollection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CardClicked extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_card);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent cardList = new Intent(CardClicked.this, AllCards.class);
        CardDatabaseManager cdm = new CardDatabaseManager(this);


        Intent i = getIntent();
        long id = i.getLongExtra("id", 0);
        String name = i.getStringExtra("name");
        byte[] image = i.getByteArrayExtra("image");

        InputStream is = new ByteArrayInputStream(image);
        Bitmap bmpImage = BitmapFactory.decodeStream(is);

        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name);

        ImageView iv_image = findViewById(R.id.iv_card);
        iv_image.setImageBitmap(bmpImage);

        AppCompatButton btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdm.open();
                cdm.deleteCard(id);
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
