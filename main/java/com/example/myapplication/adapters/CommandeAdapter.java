package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.others.Peluches;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



///////////////////////////////////////////////////
/////////////// Adapter personnalisé //////////////
///////////////////////////////////////////////////
public class CommandeAdapter extends BaseAdapter {
    private final ArrayList<Peluches.Peluche> items;
    private final LayoutInflater mInflater;

    public CommandeAdapter(Activity activity, ArrayList<Peluches.Peluche> items) {
        this.items = items;
        mInflater = LayoutInflater.from(activity.getApplicationContext());
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
        View layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = convertView == null ? mInflater.inflate(R.layout.activity_commande_peluche, parent, false) : convertView;

        //(2) : Récupération des Widgets de notre layout
        TextView displayNom;
        TextView displayPrice;
        ImageView img;
        displayNom = layoutItem.findViewById(R.id.pelucheNameCommande);
        displayPrice = layoutItem.findViewById(R.id.peluchePriceCommande);
        img = layoutItem.findViewById(R.id.imagePelucheCommande);

        //(3) : Renseignement des valeurs
        displayNom.setText(items.get(position).getName());
        displayPrice.setText(items.get(position).getPrice() + "€");
        Picasso.get().load(items.get(position).getImageURL()).placeholder(R.drawable.ic_launcher_foreground).into(img);

        displayNom.setTag(position);
        return layoutItem; //On retourne l'item créé.
    }

}