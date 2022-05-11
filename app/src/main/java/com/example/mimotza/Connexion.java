/****************************************
 Fichier : Connexion.java
 Auteur : Isabelle Rioux
 Fonctionnalité : MMZ-M-003 Se connecter
 Date: 09/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 09/05/2022 Nom: Isabelle Rioux Description: Création de l'activité et début de codage.
 Date: 10/05/2022 Nom: Isabelle Rioux Description: Requête à la base de donnée web et envoi vers la base de données locale (début)
 =========================================================
 ****************************************/
package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Connexion extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        Button conn = (Button) findViewById(R.id.btnConnConn);
        Button back = (Button) findViewById(R.id.btnConnInsc);

        conn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnConnConn:
                //envoie la requete à la base de donnée
                EditText fieldUsername = (EditText) findViewById(R.id.username);
                EditText fieldMdp = (EditText) findViewById(R.id.password);

                Bundle b = new Bundle();
                b.putString("username", fieldUsername.getText().toString());
                b.putString("mdp", fieldMdp.getText().toString());

                checkInDataBase(b);
                break;

            case R.id.btnConnInsc:
                startActivity(new Intent(Connexion.this, Inscription.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;

            default:
                break;
        }
    }

    private void checkInDataBase(Bundle b){
        final TextView view = (TextView) findViewById(R.id.resultRequest);

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://127.0.0.1:8000/loginAPI";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        String url = "http://10.0.2.2:8000/loginAPI";     //emulateur

        DBWrapper bd = new DBWrapper(this, "mimotza");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonUser = new JSONObject(response);

                            bd.insertUser(jsonUser.getInt("idOrigin"),jsonUser.getString("username"),jsonUser.getString("nom"),jsonUser.getString("prenom"));
                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                        startActivity(new Intent(Connexion.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse.statusCode == 416){
//                    view.setText("Cet utilisateur n'existe pas.");
                    Toast.makeText(Connexion.this, "Cet utilisateur n'existe pas.", Toast.LENGTH_LONG).show();
                }else if (error.networkResponse.statusCode == 403) {
//                    view.setText("Cet utilisateur est banni.");
                    Toast.makeText(Connexion.this, "Cet utilisateur est banni.", Toast.LENGTH_LONG).show();
                }else if (error.networkResponse.statusCode == 401) {
//                    view.setText("Le mot de passe est érroné.");
                    Toast.makeText(Connexion.this, "Le mot de passe est érroné.", Toast.LENGTH_LONG).show();
                }else {
                    view.setText("Une erreur est survenue.");
                    Toast.makeText(Connexion.this, "Une erreur est survenue.", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() { //changer params
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", b.getString("username"));
                params.put("mdp", b.getString("mdp"));
                return params;
            }
        };

        queue.add(stringRequest);
    }

}