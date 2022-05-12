/****************************************
 Fichier : ProfilJoueur
 Auteur : Alebrto Oviedo
 Fonctionnalité : page qui fetch les données dèun joueur et les affiche a lecran
 Date : 2022-05-11
 Vérification :
 Date Nom Approuvé
 2022-05-11 Alberto
 =========================================================
 Historique de modifications :
 Date Nom Description
 2022-05-05 : creation de l'objet
 2022-05-11 : commentaire et enttetes
 =========================================================
 ****************************************/

package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfilJoueur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DBHandler bd = new DBHandler(this);

        super.onCreate(savedInstanceState);
        ImageView img;
        getInfo();
    }
    /**
     * cherche les info avec un requete api et les insere dans les conainer
     * @author Alberto oviedo
     */
    private void getInfo(){
        Intent info = getIntent();
        Toast.makeText(ProfilJoueur.this, info.getStringExtra("user"),Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_profil_joueur);
        TextView nbParties = findViewById(R.id.stat1);
        TextView partiesGagnes = findViewById(R.id.stat2);
        TextView temps = findViewById(R.id.stat3);
        TextView date = findViewById(R.id.stat4);
        ImageView img = findViewById(R.id.imageProfile);

        TextView titleProfile = findViewById(R.id.usernameProfil);
        //String url = "http://127.0.0.1:8000/userProfile";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        //String url = "http://10.0.2.2:8000/ajoutSuggestion";     //emulateur
        String url = "http://10.0.0.246:8000/userProfile";     //home
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject infoJson = null;
                        try {
                            infoJson = new JSONObject(response);
                            nbParties.setText("Nombre de parties terminés : " + (infoJson.getJSONObject(info.getStringExtra("user")).getString("parties")));
                            partiesGagnes.setText("Nombre de parties gagnées : " + (infoJson.getJSONObject(info.getStringExtra("user")).getString("partiesWin")));
                            temps.setText("Temps joué : " + (infoJson.getJSONObject(info.getStringExtra("user")).getString("tempsJoue")));
                            date.setText("Date de création : " + (infoJson.getJSONObject(info.getStringExtra("user")).getString("date")));
                            titleProfile.setText(info.getStringExtra("user"));
                            RequestOptions options = new RequestOptions();
                            options.centerCrop();
                            Glide.with(ProfilJoueur.this)
                                    .load(infoJson.getJSONObject(info.getStringExtra("user")).getString("img"))
                                    .override(400,400)
                                    .into(img);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // view.setText(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", info.getStringExtra("user"));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}