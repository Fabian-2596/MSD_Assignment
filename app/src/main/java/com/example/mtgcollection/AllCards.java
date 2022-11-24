package com.example.mtgcollection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class AllCards extends Fragment {


    private int allCards;
    public AllCards(){}

    public static AllCards newInstance(Integer getAllCards) {
        AllCards fragment = new AllCards();
        Bundle args = new Bundle();
        args.putInt("all Cards", getAllCards);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            allCards = getArguments().getInt("all Cards");
        }

    }
}
