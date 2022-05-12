package com.example.mimotza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Context;
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

public class ProfilJoueur extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_joueur);

        DBHandler bd = new DBHandler(this);
        
        ImageView img;
       // syncMotJeu();

        Button btnBack = (Button) findViewById(R.id.btnProfilReturn);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProfilReturn:
                startActivity(new Intent(ProfilJoueur.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
                break;
        }
    }

    private void syncMotJeu(){
        Intent info = getIntent();
        Toast.makeText(ProfilJoueur.this, info.getStringExtra("user"),Toast.LENGTH_LONG).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        
        TextView nbParties = findViewById(R.id.stat1);
        TextView partiesGagnes = findViewById(R.id.stat2);
        TextView temps = findViewById(R.id.stat3);
        TextView date = findViewById(R.id.stat4);
        ImageView img = findViewById(R.id.imageProfile);

        String url = "http://10.0.2.2:8000/userProfile";     //emulateur
        TextView titleProfile = findViewById(R.id.usernameProfil);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject infoJson = null;
                        try {
                            infoJson = new JSONObject(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //view.setText(error.toString());
                Toast.makeText(ProfilJoueur.this, "Une erreur est survenue.\n" + error.toString(), Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.menuJouer:
                startActivity(new Intent(ProfilJoueur.this, Jeu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuSugg:
                //start intent suggestion
                return true;
            case R.id.menuForum:
                startActivity(new Intent(ProfilJoueur.this, ForumActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuProfil:
                return true;
            case R.id.menuDeco:
                //deconnecter joueur avant de start intent
                disconnect();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void disconnect(){
        //vas chercher dans la bd le joueur qui se déconnecte puis envoie une requete au serveur pour dire que le joueur est inactif
        DBWrapper bd = new DBWrapper(this, "mimotza");
        Integer idOrigin = bd.disconnectUser();

        if (idOrigin > 0){
            RequestQueue queue = Volley.newRequestQueue(this);
            //String url = "http://127.0.0.1:8000/logoutAPI";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
            String url = "http://10.0.2.2:8000/logoutAPI";     //emulateur

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            startActivity(new Intent(ProfilJoueur.this, Connexion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 416){
                        Toast.makeText(ProfilJoueur.this,"Cet utilisateur n'existe pas.",Toast.LENGTH_LONG);
                    }else {
                        Toast.makeText(ProfilJoueur.this,"Une erreur est survenue.",Toast.LENGTH_LONG);
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() { //changer params
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", idOrigin.toString());
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }
}