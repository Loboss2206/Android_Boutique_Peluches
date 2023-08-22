package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapters.CommandeAdapter;
import com.example.myapplication.others.Peluches;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CommandeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        ///////////////////////////////////////////////////
        //////////////// Echange de données ///////////////
        ///////////////////////////////////////////////////
        Intent intent = getIntent();
        ArrayList<Peluches.Peluche> peluchesintent = intent.getParcelableArrayListExtra("list");

        // Creation et initialisation de l'Adapter pour les peluches
        CommandeAdapter adapter = new CommandeAdapter(this, peluchesintent);

        // Recuperation du composant ListView pour afficher les peluches commandées
        ListView display = findViewById(R.id.listStitchsPanier);

        // Initialisation de la liste avec les donnees
        display.setAdapter(adapter);

        // Récupération payer afin de lui rajouter un ClickListener
        Button payer = findViewById(R.id.buttonValiderCommande);
        payer.setOnClickListener(view -> {

            // On créer une nouvelle alert pour récapituler la commande
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            StringBuilder sb = new StringBuilder();
            int prix = 0;
            // On rajoute a notre chaine de caractere les articles avec leurs prix
            sb.append("Articles de la commande : \n");
            for (Peluches.Peluche peluche : peluchesintent) {
                sb.append(peluche.getName()).append(" | ").append(peluche.getPrice()).append("€").append("\n");
                prix += peluche.getPrice();
                System.out.println(peluche.isSelect());
            }
            // On rajoute la ligne pour le prix total de la commande
            sb.append("\nPrix total de la commande : ").append(prix).append("€");
            builder.setTitle("Récapitulatif de la commande");
            builder.setMessage(sb);
            // On rajoute un bouton "Valider" dans notre alert afin d'afficher la réussite du paiement
            // Et créer l'activité faisant suite au paiement
            builder.setPositiveButton("Valider", (dialog, id) -> {
                Context context = getApplicationContext();
                CharSequence text = "Paiement validé";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intentpayer = new Intent(getApplicationContext(), PayerActivity.class);
                startActivity(intentpayer);
            });
            // On rajoute un bouton pour annuler le paiement
            builder.setNegativeButton("Annuler", (dialog, id) -> dialog.cancel());
            builder.show();
        });


    }
}