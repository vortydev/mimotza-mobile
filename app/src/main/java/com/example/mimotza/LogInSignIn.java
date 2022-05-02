package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogInSignIn extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_in);

        Button insc = (Button) findViewById(R.id.insc);
        insc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.insc:
                Intent intentInsc = new Intent(LogInSignIn.this, Inscription.class);
                startActivity(intentInsc);
                break;

            case R.id.conn:
                //connexion
                break;

            default:
                break;
        }
    }
}