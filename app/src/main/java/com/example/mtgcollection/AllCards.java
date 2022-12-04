package com.example.mtgcollection;

import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;

import static java.util.stream.Collectors.toList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllCards extends AppCompatActivity {

    CardDatabaseManager cdm;
    ListView lv_cards;
    Cursor cards;
    CardCursorAdapter cca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        updateList();

        lv_cards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent onCard = new Intent(AllCards.this, CardClicked.class);
                Cursor card = (Cursor) cca.getItem(position);
                String name = card.getString(card.getColumnIndexOrThrow(KEY_NAME));
                byte[] image = card.getBlob(card.getColumnIndexOrThrow(KEY_IMAGE));
                onCard.putExtra("id", id);
                onCard.putExtra("name", name);
                onCard.putExtra("image", image);
                startActivityForResult(onCard, 42);
            }
        });

        ImageButton btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCard = new Intent(AllCards.this, AddCard.class);
                startActivityForResult(addCard, 42);
            }
        });

        AppCompatButton btn_decks = findViewById(R.id.btn_decks);
        btn_decks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent decks = new Intent(AllCards.this, AllDecks.class);
                startActivity(decks);
            }
        });

        ImageButton btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder filter = new AlertDialog.Builder(AllCards.this);
                filter.setCancelable(true);
                LayoutInflater inflater = AllCards.this.getLayoutInflater();
                View filterView = inflater.inflate(R.layout.filter, null);

                RangeSlider rl_cost = filterView.findViewById(R.id.slider_cost);
                cdm.open();
                List<Float> minmax = cdm.getMinMax();
                Log.i("minmaxValues", minmax.get(0).toString() + ", " + minmax.get(1).toString());
                rl_cost.setValueFrom(minmax.get(0));
                rl_cost.setValueTo(minmax.get(1));
                rl_cost.setValues(minmax);

                AppCompatSpinner filterColor = filterView.findViewById(R.id.spinner_color);
                List<String> colors = Arrays.stream(AllCards.filterColor.values())
                        .map(Enum::toString)
                        .collect(toList());
                ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(filterView.getContext(), R.layout.spinner_item, colors);
                colorAdapter.setDropDownViewResource(R.layout.spinner_item);
                filterColor.setAdapter(colorAdapter);

                AppCompatSpinner filterType = filterView.findViewById(R.id.spinner_type);
                List<String> types = Arrays.stream(AllCards.filterType.values())
                        .map(Enum::toString)
                        .collect(toList());

                ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(filterView.getContext(), R.layout.spinner_item, types);
                typeAdapter.setDropDownViewResource(R.layout.spinner_item);
                filterType.setAdapter(typeAdapter);

                filter.setView(filterView);
                filter.setPositiveButton(
                        "Apply filter",
                        (dialog, id) -> {
                            List<Float> values = rl_cost.getValues();
                            String color = filterColor.getSelectedItem().toString();
                            String type = filterType.getSelectedItem().toString();
                            Cursor filteredCards = cdm.filterCards(values, color, type);
                            Log.i("minmaxValues", values.get(0).toString() + ", " + values.get(1).toString());
                            Log.i("Color", color);
                            Log.i("Type", type);
                            cca = new CardCursorAdapter(filterView.getContext(), filteredCards);
                            lv_cards.setAdapter(cca);
                            cdm.close();
                            dialog.cancel();
                        }
                );

                filter.setNegativeButton(
                        "Cancel",
                        (dialog, id) -> {;
                            cdm.close();
                            dialog.cancel();
                        });

                AlertDialog showFilter = filter.create();
                showFilter.show();
            }
        });
    }

    private void updateList() {
        cdm = new CardDatabaseManager(this);
        lv_cards = findViewById(R.id.lv_cards);
        cdm.open();
        cards = cdm.getAllCards();
        cca = new CardCursorAdapter(this, cards);
        lv_cards.setAdapter(cca);
        cdm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateList();
    }

    public enum filterType {
        All,
        Land,
        Creature,
        Artifact,
        Enchantment,
        Planeswalker,
        Spell,
        Instant,
        Sorcery;
    }

    public enum filterColor {
        All,
        Black,
        White,
        Red,
        Blue,
        Green;
    }
}