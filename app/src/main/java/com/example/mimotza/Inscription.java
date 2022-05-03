package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                EditText fieldNom = (EditText) findViewById(R.id.nom);
                EditText fieldPrenom = (EditText) findViewById(R.id.prenom);
                EditText fieldUsername = (EditText) findViewById(R.id.username);
                EditText fieldEmail = (EditText) findViewById(R.id.email);
                EditText fieldMdp = (EditText) findViewById(R.id.password);

                if(TextUtils.isEmpty(fieldNom.getText().toString())){
                    Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Nom\"",Toast.LENGTH_LONG).show();
                    break;
                } else if (TextUtils.isEmpty(fieldPrenom.getText().toString())){
                    Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Prénom\"",Toast.LENGTH_LONG).show();
                    break;
                }else if (TextUtils.isEmpty(fieldUsername.getText().toString())){
                    Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Nom d\'utilisateur\"",Toast.LENGTH_LONG).show();
                    break;
                }else if (TextUtils.isEmpty(fieldEmail.getText().toString())){
                    Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Adresse courriel\"",Toast.LENGTH_LONG).show();
                    break;
                }else if (TextUtils.isEmpty(fieldMdp.getText().toString())){
                    Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Mot de passe\"",Toast.LENGTH_LONG).show();
                    break;
                }

                Bundle b = new Bundle();

                b.putString("nom", fieldNom.getText().toString());
                b.putString("prenom", fieldPrenom.getText().toString());
                b.putString("username", fieldUsername.getText().toString());
                b.putString("email", fieldEmail.getText().toString());
                b.putString("mdp", fieldMdp.getText().toString());

                break;

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }


}