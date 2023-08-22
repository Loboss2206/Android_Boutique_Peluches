package com.example.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.others.Peluches;
import com.example.myapplication.R;
import com.example.myapplication.adapters.PelucheAdapter;
import com.example.myapplication.services.BackgroundMusicService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickableActivity {
    private PelucheAdapter adapter;
    private boolean isPlaying = false;
    public static ArrayList<Peluches.Peluche> peluchesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On va créer notre liste de peluche visible à l'aide de l'adapter

        peluchesSelected = new ArrayList<>();
        Peluches peluches = new Peluches(getContext());

        //Creation et initialisation de l'Adapter pour les peluches
        adapter = new PelucheAdapter(MainActivity.this, peluches);

        //Recuperation du composant GridView
        GridView display = findViewById(R.id.listStitchs);

        //Initialisation de la liste avec les donnees
        display.setAdapter(adapter);


        Button valider = findViewById(R.id.buttonValider);

        // Si le nombre de peluche selectionnés est supérieur à 0,
        // on envoie l'utilisateur sur l'activité du panier, sinon on envoie un toast
        valider.setOnClickListener(view -> {
            if (peluchesSelected.size() == 0) {
                Context context = getApplicationContext();
                CharSequence text = "Il faut sélectionner au moins une peluche";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                //////////////// Echange de données ///////////////
                Intent intent = new Intent(getApplicationContext(), CommandeActivity.class);
                System.out.println(peluchesSelected);
                intent.putExtra("list", peluchesSelected);
                startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////
        //////////////////// Animation ////////////////////
        ///////////////////////////////////////////////////

        ///////////////////////////////////////////////////
        /////////////// Technologie non vue ///////////////
        ///////////////////////////////////////////////////

        // On ajoute au bouton "music" un clickListener
        // Qui permettra de jouer une animation et d'arreter
        // ou remettre la musique
        Button music = findViewById(R.id.boutonMusique);
        music.setOnClickListener(view -> {
            if (isPlaying) {
                stopService(new Intent(MainActivity.this, BackgroundMusicService.class));
                isPlaying = false;
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.musicclick));
            } else {
                startService(new Intent(MainActivity.this, BackgroundMusicService.class));
                isPlaying = true;
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.musicclick));
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ///////////////////////////////////////////////////
        /////////////// Technologie non vue ///////////////
        ///////////////////////////////////////////////////
        stopService(new Intent(MainActivity.this, BackgroundMusicService.class));
        isPlaying = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        ///////////////////////////////////////////////////
        /////////////// Technologie non vue ///////////////
        ///////////////////////////////////////////////////
        adapter.notifyDataSetChanged();
        if (!isPlaying) {
            startService(new Intent(MainActivity.this, BackgroundMusicService.class));
            isPlaying = true;
        }
    }

    @Override
    public void onClickPeluche(Peluches.Peluche item) {
        ///////////////////////////////////////////////////
        //////////////// Echange de données ///////////////
        ///////////////////////////////////////////////////
        Intent intent = new Intent(MainActivity.this, PelucheActivity.class);
        intent.putExtra("peluche", item);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

}