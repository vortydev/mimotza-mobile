package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intentInsc = new Intent(MainActivity.this, LogInSignIn.class); // redirection vers l'écran de connexion
        //startActivity(intentInsc);

        Button isa = (Button) findViewById(R.id.isa);
        isa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.isa:
                Intent intentBD = new Intent(MainActivity.this, TestBd.class);
                startActivity(intentBD);
                break;
            case R.id.bdButton:

                Intent intentInsc = new Intent(MainActivity.this, LogInSignIn.class);
                startActivity(intentInsc);
                break;

            default:
                break;
        }
    }
}