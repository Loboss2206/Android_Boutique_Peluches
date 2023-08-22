package com.example.myapplication.activities;

import android.content.Context;

import com.example.myapplication.others.Peluches;

public interface ClickableActivity {
    void onClickPeluche(Peluches.Peluche item);

    Context getContext();
}
