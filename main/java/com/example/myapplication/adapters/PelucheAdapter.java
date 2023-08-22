package com.example.myapplication.adapters;

import static com.example.myapplication.activities.MainActivity.peluchesSelected;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.others.Peluches;
import com.example.myapplication.R;
import com.example.myapplication.activities.ClickableActivity;
import com.squareup.picasso.Picasso;



///////////////////////////////////////////////////
/////////////// Adapter personnalisé //////////////
///////////////////////////////////////////////////
public class PelucheAdapter extends BaseAdapter {
    private final Peluches items;
    private final LayoutInflater mInflater;
    private final ClickableActivity activity;

    public PelucheAdapter(ClickableActivity activity, Peluches items) {
        this.activity = activity;
        this.items = items;
        mInflater = LayoutInflater.from(activity.getContext());
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        int n = 0;
        for (Peluches.Peluche pe : peluchesSelected) {
            if (pe.equals(items.get(position))) {
                n++;
            }
        }
        items.get(position).setSelect(n > 0);

        View layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = convertView == null ? mInflater.inflate(R.layout.activity_peluche, parent, false) : convertView;

        //(2) : Récupération des TextView de notre layout
        TextView displayNom = layoutItem.findViewById(R.id.pelucheName);
        TextView displayPrice = layoutItem.findViewById(R.id.peluchePrice);
        ImageView img = layoutItem.findViewById(R.id.imagePeluche);

        //(3) : Renseignement des valeurs
        displayNom.setText(items.get(position).getName());
        displayPrice.setText(items.get(position).getPrice() + "€");
        Picasso.get().load(items.get(position).getImageURL()).into(img);

        //(4) : On change la couleur en fonction de si l'item est selectionné ou pas
        boolean select = items.get(position).isSelect();
        if (select) {
            displayNom.setTextColor(Color.GREEN);
        } else {
            displayNom.setTextColor(Color.WHITE);
        }

        //(5) On met un écouteur d'evenement pour quand ntre item est cliqué
        displayNom.setTag(position);
        layoutItem.setOnClickListener(v -> activity.onClickPeluche(items.get(position)));

        return layoutItem; //On retourne l'item créé.
    }
}