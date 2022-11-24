package com.example.mtgcollection;

import static java.util.stream.Collectors.toList;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Arrays;
import java.util.List;

public class AddCard extends AppCompatActivity {

    EditText et_name = findViewById(R.id.et_name);
    EditText et_cost = findViewById(R.id.et_cost);
    EditText et_power = findViewById(R.id.et_power);
    EditText et_toughness = findViewById(R.id.et_toughness);
    Spinner spn_type = findViewById(R.id.spinner_type);
    Spinner spn_color = findViewById(R.id.spinner_color);
    Button btn_image = findViewById(R.id.btn_picture);
    Button btn_submit = findViewById(R.id.btn_submit);
    ImageButton btn_return = findViewById(R.id.btn_return);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);

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


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.getText().toString();
                et_cost.getText().toString();
                et_power.getText().toString();
                et_toughness.getText().toString();
                spn_type.getSelectedItem().toString();
                spn_color.getSelectedItem().toString();
                //TODO add to database
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
