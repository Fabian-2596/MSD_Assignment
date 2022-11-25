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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

        //https://stackoverflow.com/questions/38352148/get-image-from-the-gallery-and-show-in-imageview
        ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result != null) {
                            Uri imageUri = result.getData().getData();
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

                Card card = new Card(et_name.getText().toString(), Integer.parseInt(et_cost.getText().toString()), Integer.parseInt(et_power.getText().toString()), Integer.parseInt(et_toughness.getText().toString()),
                        spn_type.getSelectedItem().toString(), spn_color.getSelectedItem().toString(), image);
                cdm.open();
                cdm.addCard(card);
                cdm.close();

                Toast toast = Toast.makeText(getApplicationContext(), et_name.getText().toString() + " added", Toast.LENGTH_SHORT);
                toast.show();
                Intent cardList = new Intent(AddCard.this, MainActivity.class);
                startActivity(cardList);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
