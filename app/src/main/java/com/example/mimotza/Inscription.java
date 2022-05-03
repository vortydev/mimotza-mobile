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
                EditText fieldValidateMdp = (EditText) findViewById(R.id.password2);

                if (!(validateField(fieldPrenom,"Veuillez compléter le champ \"Prénom\"")
                        && validateField(fieldNom,"Veuillez compléter le champ \"Nom\"")
                        && validateField(fieldUsername,"Veuillez compléter le champ \"Nom d\'utilisateur\"")
                        && validateField(fieldEmail,"Veuillez compléter le champ \"Adresse courriel\"", "[a-zA-Z0-9.!#$%&'*+-/=?^_`{}|]+@[a-zA-Z0-9.-]+.[a-zA-Z]+")
                        && validateField(fieldMdp,"Votre mot de passe doit contenir au moins une majuscule et un chiffre et avoir une longueur de 8 caractères","(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}")
                        && validateField(fieldMdp,"Vous devez valider votre mot de passe", fieldValidateMdp)))
                    break;

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
    private boolean validateField(EditText field, String toast) {
        if (TextUtils.isEmpty(field.getText().toString())) {
            Toast.makeText(Inscription.this, toast,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateField(EditText field, String toast, String regex) {
        if (Pattern.matches(regex, field.getText().toString())) {
            return true;
        }
        else {
            Toast.makeText(Inscription.this, toast,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean validateField(EditText field, String toast, EditText field2) {
        if (field.getText().toString().equals(field2.getText().toString())){
            return true;
        }else{
            Toast.makeText(Inscription.this, toast,Toast.LENGTH_LONG).show();
            return false;
        }
    }

}