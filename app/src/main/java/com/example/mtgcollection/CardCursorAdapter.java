package com.example.mtgcollection;

import static com.example.mtgcollection.CardDatabaseHelper.KEY_COLOR;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_COST;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_IMAGE;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_NAME;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_POWER;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_TOUGHNESS;
import static com.example.mtgcollection.CardDatabaseHelper.KEY_TYPE;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

public class CardCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public CardCursorAdapter(Context context, Cursor c) {
        super(context, c);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.card_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView iv_image = view.findViewById(R.id.iv_image);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_type = view.findViewById(R.id.tv_type);
        TextView tv_cost = view.findViewById(R.id.tv_cost);
        TextView tv_color = view.findViewById(R.id.tv_color);
        TextView tv_power = view.findViewById(R.id.tv_power);

        String color = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COLOR));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
        String cost = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COST));
        String power = cursor.getString(cursor.getColumnIndexOrThrow(KEY_POWER));
        String toughness = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TOUGHNESS));
        byte[] byteimage = cursor.getBlob(cursor.getColumnIndexOrThrow(KEY_IMAGE));

        InputStream is = new ByteArrayInputStream(byteimage);
        Bitmap bmpImage = BitmapFactory.decodeStream(is);
        iv_image.setImageBitmap(bmpImage);

        if (color.equals(Card.color.Black.toString()))
            color = "=";
        if (color.equals(Card.color.White.toString()))
            color = "@";
        if (color.equals(Card.color.Blue.toString()))
            color = "+";
        if (color.equals(Card.color.Green.toString()))
            color =">";
        if (color.equals(Card.color.Red.toString()))
            color = "<";

        tv_name.setText(name);
        tv_type.setText(type);
        tv_cost.setText(cost);
        tv_color.setText(color);
        tv_power.setText(power + "/" + toughness);
    }


}
