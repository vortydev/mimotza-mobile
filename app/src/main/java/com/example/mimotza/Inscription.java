/****************************************
 Fichier : Inscription.java
 Auteur : Isabelle Rioux
 Fonctionnalité : MMZ-M-002 Créer utilisateur
 Date : 02/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 02/05/2022 Nom: Isabelle Rioux Description: Création de l'activité pour le formulaire
 Date: 03/05/2022 Nom: Isabelle Rioux Description: Gestion du formulaire et des boutons
 =========================================================
 ****************************************/
package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

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

                if (!checkUp()){
                    break;
                }

                Bundle b = new Bundle();

                b.putString("nom", fieldNom.getText().toString());
                b.putString("prenom", fieldPrenom.getText().toString());
                b.putString("username", fieldUsername.getText().toString());
                b.putString("email", fieldEmail.getText().toString());
                b.putString("mdp", fieldMdp.getText().toString());

                // chargement pendant l'envoi fichier JSON à la base de données puis redirige à l'écran de connexion si validé pour se connecter

                break;

            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }

    public boolean checkUp(){
        EditText fieldNom = (EditText) findViewById(R.id.nom);
        EditText fieldPrenom = (EditText) findViewById(R.id.prenom);
        EditText fieldUsername = (EditText) findViewById(R.id.username);
        EditText fieldEmail = (EditText) findViewById(R.id.email);
        EditText fieldMdp = (EditText) findViewById(R.id.password);
        EditText fieldValidateMdp = (EditText) findViewById(R.id.password2);

        if (TextUtils.isEmpty(fieldPrenom.getText().toString())){
            Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Prénom\"",Toast.LENGTH_LONG).show();
            return false;
        }else if(TextUtils.isEmpty(fieldNom.getText().toString())){
            Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Nom\"",Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(fieldUsername.getText().toString())){
            Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Nom d\'utilisateur\"",Toast.LENGTH_LONG).show();
            return false;
        }else if (TextUtils.isEmpty(fieldEmail.getText().toString())){
            Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Adresse courriel\"",Toast.LENGTH_LONG).show();
            return false;
        }else if (TextUtils.isEmpty(fieldMdp.getText().toString())){
            Toast.makeText(Inscription.this, "Veuillez compléter le champ \"Mot de passe\"",Toast.LENGTH_LONG).show();
            return false;
        }else if (!Pattern.matches("[a-zA-Z0-9.!#$%&'*+-/=?^_`{}|]+@[a-zA-Z0-9.-]+.[a-zA-Z]+", fieldEmail.getText().toString())){
            Toast.makeText(Inscription.this, "Veuillez entrer une adresse courriel valide",Toast.LENGTH_LONG).show();
            return false;
        }else if(!Pattern.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}", fieldMdp.getText().toString())){
            Toast.makeText(Inscription.this, "Votre mot de passe doit contenir au moins une majuscule et un chiffre et avoir une longueur de 8 caractères",Toast.LENGTH_LONG).show();
            return false;
        }else if(!fieldMdp.getText().toString().equals(fieldValidateMdp.getText().toString())){
            Toast.makeText(Inscription.this, "Vous devez valider votre mot de passe",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}