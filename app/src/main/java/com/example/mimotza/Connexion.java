package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Connexion extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        Button conn = (Button) findViewById(R.id.sub);
        Button back = (Button) findViewById(R.id.back);

        conn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.sub:
                //envoie la requete à la base de donnée

                break;

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }
}