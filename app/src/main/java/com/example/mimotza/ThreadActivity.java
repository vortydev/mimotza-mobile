/****************************************
 Fichier : ThreadActivity.java
 Fonctionnalité : MMZ-M-005
 Auteur : François-Nicolas Gitzhofer
 Date : 10/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 07/05/2022 Nom: François-Nicolas Gitzhofer Description: Création de l'activité
 Date: 09/05/2022 Nom: François-Nicolas Gitzhofer Description: Finalisation de l'affichage des réponses d'un thread
 Date: 10/05/2022 Nom: François-Nicolas Gitzhofer Description: Ajout ID Thread dans intent vers MessReponseActivity
 =========================================================
 ****************************************/

package com.example.mimotza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    private JSONObject jsonObject;
    private ArrayList<JSONObject> reponseList;

    RecyclerView recyclerView;
    RecycleResponseAdapter recycleResponseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Button backToForum = findViewById(R.id.backToForum);
        backToForum.setOnClickListener(this);

        TextView auteur = findViewById(R.id.auteur);
        TextView titre = findViewById(R.id.titre);
        TextView message = findViewById(R.id.message);

        String homeIp = "192.168.2.83";
        String cegepIp = "10.170.13.52";
        String globalIp = "10.0.2.2";

        Intent intent = getIntent();

        String threadId = intent.getStringExtra("ID");

        reponseList = new ArrayList<JSONObject>();

        recyclerView = findViewById(R.id.responseRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleResponseAdapter = new RecycleResponseAdapter(this, reponseList, threadId);
        recyclerView.setAdapter(recycleResponseAdapter);
        Context currentContext = ThreadActivity.this;

        RequestQueue queue = Volley.newRequestQueue(ThreadActivity.this);
        //String url = "http://" + cegepIp + ":8000/getMedia/" + threadId;
        //String url = "http://" + homeIp + ":8000/getMedia/" + threadId;
        String url = "http://" + globalIp + ":8000/getMedia/" + threadId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ThreadActivity.this, "Got a response.\nResponse: " + response, Toast.LENGTH_LONG).show();

                        try {
                            jsonObject = new JSONObject(response);

                            auteur.setText(getResources().getString(R.string.auteur, jsonObject.getString("Auteur")));
                            titre.setText(getResources().getString(R.string.titre, jsonObject.getString("Titre")));
                            message.setText(getResources().getString(R.string.message, jsonObject.getString("Message")));

                            message.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {

                                    try {
                                        Intent intent = new Intent(ThreadActivity.this, MessReponseActivity.class);
                                        intent.putExtra("IDThread", threadId);
                                        intent.putExtra("IDMessage", jsonObject.getInt("IDMessage"));
                                        intent.putExtra("Message", jsonObject.getString("Message"));
                                        intent.putExtra("Auteur", jsonObject.getString("Auteur"));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }
                            });

                            if (jsonObject.get("Reponses").getClass().getSimpleName().contentEquals("JSONObject")) {
                                JSONObject reponses = jsonObject.getJSONObject("Reponses");

                                for (int i = 0; i < reponses.names().length(); i++) {
                                    if (reponses.get(reponses.names().getString(i)).getClass().getSimpleName().contentEquals("JSONObject")) {
                                        reponseList.add(reponses.getJSONObject(reponses.names().getString(i)));
                                    }
                                }
                                recycleResponseAdapter.notifyItemRangeInserted(0, reponseList.size());
                            }

                            //Toast.makeText(ThreadActivity.this, "JSON String to JSON object worked!", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(ThreadActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThreadActivity.this, "That didn't work!\n" + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backToForum:
                Intent intent = new Intent(ThreadActivity.this, ForumActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
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
                startActivity(new Intent(ThreadActivity.this, Jeu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuSugg:
                //start intent suggestion
                return true;
            case R.id.menuForum:
                startActivity(new Intent(ThreadActivity.this, ForumActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuProfil:
                startActivity(new Intent(ThreadActivity.this, ProfilJoueur.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
                            startActivity(new Intent(ThreadActivity.this, Connexion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 416){
                        Toast.makeText(ThreadActivity.this,"Cet utilisateur n'existe pas.",Toast.LENGTH_LONG);
                    }else {
                        Toast.makeText(ThreadActivity.this,"Une erreur est survenue.",Toast.LENGTH_LONG);
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
