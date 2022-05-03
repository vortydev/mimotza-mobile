package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJouer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bouton Jouer
        btnJouer = (Button) findViewById(R.id.btnJouer);
        btnJouer.setOnClickListener(this);

        //Intent intentInsc = new Intent(MainActivity.this, LogInSignIn.class); // redirection vers l'écran de connexion
        //startActivity(intentInsc);

        Button isa = (Button) findViewById(R.id.btnInsc);
        isa.setOnClickListener(this);
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
                startActivity(intentInsc);
                break;
            default:
                // rien :)
                break;
        }
    }
}