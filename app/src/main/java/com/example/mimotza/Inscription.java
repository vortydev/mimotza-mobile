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
 Date: 05/05/2022 Nom: Isabelle Rioux Description: Envoi vers la base de données
 =========================================================
 ****************************************/
package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

/**
 * Activité d'inscription du joueur
 * @author Isabelle Rioux
 */
public class Inscription extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button insc = (Button) findViewById(R.id.btnInscInsc);
        Button back = (Button) findViewById(R.id.btnInscReturn);

        insc.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnInscInsc:
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

            case R.id.btnInscReturn:
                finish();
                break;

            default:
                break;
        }
    }

    /**
     * Valide un champs du formulaire
     * @author Étienne Ménard
     * @param field champs du formulaire
     * @param toast message a afficher
     * @return s'il est valide ou non
     */
    private boolean validateField(EditText field, String toast) {
        if (TextUtils.isEmpty(field.getText().toString())) {
            Toast.makeText(Inscription.this, toast,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Valide un champs du formulaire
     * @author Étienne Ménard
     * @param field champs du formulaire
     * @param toast message a afficher
     * @param regex pattern regex à matcher
     * @return s'il est valide ou non
     */
    private boolean validateField(EditText field, String toast, String regex) {
        if (Pattern.matches(regex, field.getText().toString())) {
            return true;
        }
        else {
            Toast.makeText(Inscription.this, toast,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Valide un champs du formulaire
     * @author Étienne Ménard
     * @param field champs du formulaire
     * @param toast message a afficher
     * @param field2 champs du formulaire comparé
     * @return s'il est valide ou non
     */
    private boolean validateField(EditText field, String toast, EditText field2) {
        if (field.getText().toString().equals(field2.getText().toString())){
            return true;
        }else{
            Toast.makeText(Inscription.this, toast,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Ajoute un utilisateur à la base de donnée serveur
     * @author Isabelle Rioux
     * @param b Bundle d'information pour l'inscription
     */
    private void sendToDataBase(Bundle b){
        final TextView view = (TextView) findViewById(R.id.resultRequest);

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://127.0.0.1:8000/adduserAPI";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        String url = "http://10.0.2.2:8000/adduserAPI";     //emulateur

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(Inscription.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse.statusCode == 416){
//                    view.setText("Cet utilisateur existe déjà");
                    Toast.makeText(Inscription.this, "Cet utilisateur existe déjà.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Inscription.this, "Une erreur est survenue.", Toast.LENGTH_LONG).show();
//                    view.setText("Une erreur est survenue");
                }
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