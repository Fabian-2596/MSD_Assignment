package com.example.mtgcollection;

import static java.util.stream.Collectors.toList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);

        Spinner spn_type = findViewById(R.id.spinner_type);

        List<String> types = Arrays.stream(Card.type.values())
                .map(Enum::toString)
                .collect(toList());

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, types);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_type.setAdapter(typeAdapter);


        Spinner spn_color = findViewById(R.id.spinner_color);

        List<String> colors = Arrays.stream(Card.color.values())
                .map(Enum::toString)
                .collect(toList());

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, colors);
        colorAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_color.setAdapter(colorAdapter);

    }
}