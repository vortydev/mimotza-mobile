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
       // syncMotJeu();
    }
    private void syncMotJeu(){
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