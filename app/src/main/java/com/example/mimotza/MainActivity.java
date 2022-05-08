package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJouer;
    private Button btnForum;
    private Button btnInsc;
    private Button btnBD;
    private Button profilJoueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // jeu
        btnJouer = (Button) findViewById(R.id.btnJouer);
        btnJouer.setOnClickListener(this);

        // inscription
        btnInsc = (Button) findViewById(R.id.btnInsc);
        btnInsc.setOnClickListener(this);

        // forum
        btnForum = (Button) findViewById(R.id.btnForum);
        btnForum.setOnClickListener(this);

        // BD
        btnBD = (Button) findViewById(R.id.btnBD);
        btnBD.setOnClickListener(this);

        profilJoueur = (Button) findViewById(R.id.btnProfiljoueur);
        profilJoueur.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJouer:
                Intent intentJouer = new Intent(MainActivity.this, Jeu.class);
                intentJouer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentJouer);
                break;
            case R.id.btnInsc:
                Intent intentInsc = new Intent(MainActivity.this, LogInSignIn.class);
                intentInsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentInsc);
                break;
            case R.id.btnForum:
                Intent intentForum = new Intent(MainActivity.this, ForumActivity.class);
                intentForum.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentForum);
                break;
            case R.id.btnBD:
                Intent intentBD = new Intent(MainActivity.this, TestBd.class);
                intentBD.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentBD);
                break;
            case R.id.btnProfiljoueur:

                Intent IntentP = new Intent(MainActivity.this, ProfilJoueur.class);
                IntentP.putExtra("user", "vorty");
                IntentP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(IntentP);
                break;

            default:
                // rien :)
                break;
        }
    }
}