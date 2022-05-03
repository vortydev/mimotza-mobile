package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inscription extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button insc = (Button) findViewById(R.id.sub);
        Button back = (Button) findViewById(R.id.back);

        insc.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.sub:
                //envoi de formulaire
                break;

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }


}