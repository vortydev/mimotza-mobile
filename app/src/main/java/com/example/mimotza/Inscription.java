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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
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

                sendToDataBase(b);

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

    private void sendToDataBase(Bundle b){
        final TextView view = (TextView) findViewById(R.id.resultRequest);

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://127.0.0.1:8000/adduserAPI";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        String url = "http://10.0.2.2:8000/adduserAPI";     //emulateur

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //redirige vers connexion
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.setText(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("prenom", b.getString("prenom"));
                params.put("nom", b.getString("nom"));
                params.put("email", b.getString("email"));
                params.put("username", b.getString("username"));
                params.put("mdp", b.getString("mdp"));
                return params;
            }
        };
        queue.add(stringRequest);
    }

}