package com.example.myapplication.activities;

import static com.example.myapplication.activities.MainActivity.peluchesSelected;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.others.Peluches;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class PelucheActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        ///////////////////////////////////////////////////
        //////////////// Echange de données ///////////////
        ///////////////////////////////////////////////////

        // On initialise peluche avec la peluche selectionnée
        Intent intent = getIntent();
        Peluches.Peluche peluche = intent.getParcelableExtra("peluche");

        // Puis on affiche ses informations
        TextView displayNom = findViewById(R.id.pelucheNameInfo);
        TextView displayPrice = findViewById(R.id.peluchePriceInfo);
        TextView displayDescription = findViewById(R.id.pelucheDescriptionInfo);
        TextView displayTaille = findViewById(R.id.pelucheTailleInfo);
        TextView displayType = findViewById(R.id.pelucheTypeInfo);
        ImageView img = findViewById(R.id.pelucheImageInfo);

        displayNom.setText(peluche.getName());
        displayPrice.setText(peluche.getPrice() + "€");
        displayDescription.setText(peluche.getDescription() + "");
        displayTaille.setText("Taille : " + peluche.getTaille() + "cm");
        displayType.setText("Type peluche : " + peluche.getType());
        Picasso.get().load(peluche.getImageURL()).into(img);

        Button panier = findViewById(R.id.buttonPanier);
        // Si le panier est vide, alors on affiche un mesage d'erreur
        // sinon, on envoie la liste des peluches selectionnees dans CommandeActivity
        panier.setOnClickListener(view -> {
            if (peluchesSelected.size() == 0) {
                Context context = getApplicationContext();
                CharSequence text = "Votre panier est vide";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {


                ///////////////////////////////////////////////////
                //////////////// Echange de données ///////////////
                ///////////////////////////////////////////////////
                Intent intent2 = new Intent(getApplicationContext(), CommandeActivity.class);
                intent2.putExtra("list", peluchesSelected);
                startActivity(intent2);
            }
        });


        ////////////////////////////////////////////////////
        //////////////////// Animations ////////////////////
        ////////////////////////////////////////////////////

        // Ajout d'une animation sur les boutons ajouter
        // et supprimer au panier
        Button ajouter = findViewById(R.id.buttonAjouter);
        Button supprimer = findViewById(R.id.buttonSupprimer);

        // On masque le bouton ajouter si la peluche est déjà dans le panier
        if (peluche.isSelect()) {
            ajouter.setVisibility(View.INVISIBLE);
        } else {
            supprimer.setVisibility(View.INVISIBLE);
        }

        // On ajoute une animation lorsqu'on ajoute la peluche au panier et on affiche un message
        ajouter.setOnClickListener(view -> {
            if (!peluche.isSelect()) {
                peluche.setSelect(true);
                peluchesSelected.add(peluche);

                Context context = getApplicationContext();
                CharSequence text = "Peluche ajoutée au panier";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                ajouter.startAnimation(AnimationUtils.loadAnimation(PelucheActivity.this, R.anim.fondu));
                ajouter.setVisibility(View.INVISIBLE);
                supprimer.startAnimation(AnimationUtils.loadAnimation(PelucheActivity.this, R.anim.reapparition));
                supprimer.setVisibility(View.VISIBLE);

            }
        });

        // On ajoute une animation lorsqu'on enlève la peluche du panier et on affiche un message
        supprimer.setOnClickListener(view -> {
            if (peluche.isSelect()) {
                peluche.setSelect(false);
                peluchesSelected.remove(peluche);

                Context context = getApplicationContext();
                CharSequence text = "Peluche enlevée au panier";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                supprimer.startAnimation(AnimationUtils.loadAnimation(PelucheActivity.this, R.anim.fondu));
                supprimer.setVisibility(View.INVISIBLE);
                ajouter.startAnimation(AnimationUtils.loadAnimation(PelucheActivity.this, R.anim.reapparition));
                ajouter.setVisibility(View.VISIBLE);
            }
        });
    }
}