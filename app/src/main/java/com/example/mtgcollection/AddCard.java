package com.example.mtgcollection;

import static java.util.stream.Collectors.toList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddCard extends AppCompatActivity {

    private byte[] image;
    CardDatabaseManager cdm = new CardDatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent cardList = new Intent(AddCard.this, AllCards.class);
        EditText et_name = findViewById(R.id.et_name);
        EditText et_cost = findViewById(R.id.et_cost);
        EditText et_power = findViewById(R.id.et_power);
        EditText et_toughness = findViewById(R.id.et_toughness);
        AppCompatSpinner spn_type = findViewById(R.id.spinner_type);
        AppCompatSpinner spn_color = findViewById(R.id.spinner_color);
        AppCompatButton btn_image = findViewById(R.id.btn_picture);
        AppCompatButton btn_submit = findViewById(R.id.btn_submit);
        ImageButton btn_return = findViewById(R.id.btn_return);

        List<String> types = Arrays.stream(Card.type.values())
                .map(Enum::toString)
                .collect(toList());

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, types);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_type.setAdapter(typeAdapter);

        List<String> colors = Arrays.stream(Card.color.values())
                .map(Enum::toString)
                .collect(toList());

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, colors);
        colorAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_color.setAdapter(colorAdapter);

        //Image picker from https://stackoverflow.com/questions/38352148/get-image-from-the-gallery-and-show-in-imageview
        ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        ImageView iv_preview = findViewById(R.id.iv_preview);
                        if (result != null) {
                            Uri imageUri = result.getData().getData();
                            iv_preview.setImageURI(imageUri);
                            try {
                                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                image = stream.toByteArray();
                                
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                imagePickerActivityResult.launch(photoPickerIntent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String type = spn_type.getSelectedItem().toString();
                String color = spn_color.getSelectedItem().toString();

                if(name != null && et_cost.getText().toString() != null && et_power.getText().toString() != null && et_toughness.getText().toString() != null && image != null) {
                    int cost = Integer.parseInt(et_cost.getText().toString());
                    int power = Integer.parseInt(et_power.getText().toString());
                    int toughness = Integer.parseInt(et_toughness.getText().toString());

                    Card card = new Card(name, cost, power, toughness, type, color, image);

                    cdm.open();
                    cdm.addCard(card);
                    cdm.close();
                    Toast success = Toast.makeText(getApplicationContext(), et_name.getText().toString() + " added", Toast.LENGTH_SHORT);
                    success.show();
                    startActivity(cardList);
                    finish();
                }
                else {
                    Toast fail = Toast.makeText(getApplicationContext(),  "Check if everything is filled in", Toast.LENGTH_SHORT);
                    fail.show();
                }

            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(cardList);
                finish();
            }
        });
    }
}
