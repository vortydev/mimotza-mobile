/****************************************
 Fichier : MessReponseActivity.java
 Auteur : François-Nicolas Gitzhofer
 Fonctionnalité : MMZ-M-005
 Date : 10/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 10/05/2022 Nom: François-Nicolas Gitzhofer Description: Création de l'activité pour répondre à un message
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MessReponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse);

        DBWrapper dbWrapper = new DBWrapper(this, "mimotza");

        Context currentContext = MessReponseActivity.this;

        String homeIp = "192.168.2.83";
        String cegepIp = "10.170.13.52";
        String globalIp = "10.0.2.2";

        Intent intent = getIntent();

        String threadId = intent.getStringExtra("IDThread");
        int messId = intent.getIntExtra("IDMessage", 0);
        String messStr = intent.getStringExtra("Message");
        String auteur = intent.getStringExtra("Auteur");

        TextView viewAuteur = findViewById(R.id.auteur);
        TextView viewMessage = findViewById(R.id.message);
        EditText viewReponse = findViewById(R.id.reponse);
        Button submitButton = findViewById(R.id.submit);
        Button backButton = findViewById(R.id.btnRepReturn);

        viewAuteur.setText(auteur);
        viewMessage.setText(messStr);

        RequestQueue queue = Volley.newRequestQueue(MessReponseActivity.this);
        //String url = "http://" + cegepIp + ":8000/ajoutMedia";
        //String url = "http://" + homeIp + ":8000/ajoutMedia";
        String url = "http://" + globalIp + ":8000/ajoutMedia";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(MessReponseActivity.this, ThreadActivity.class);
                        intent.putExtra("ID", threadId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(MessReponseActivity.this, "Message ajouté avec succès", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MessReponseActivity.this, "That didn't work!\n" + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("idUser", dbWrapper.fetchUserId().toString());             // 16 à remplacer par l'id de l'utilisateur actuel
                params.put("contenu", viewReponse.getText().toString());
                params.put("idMessageParent", Integer.toString(messId));

                return params;
            }
        };

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (viewReponse.getText().toString().contentEquals("")) {
                    Toast.makeText(MessReponseActivity.this, "Le message ne peut pas être vide!", Toast.LENGTH_SHORT).show();
                }
                else {
                    queue.add(stringRequest);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessReponseActivity.this, ThreadActivity.class);
                intent.putExtra("ID", threadId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
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
                startActivity(new Intent(MessReponseActivity.this, Jeu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuSugg:
                //start intent suggestion
                return true;
            case R.id.menuForum:
                startActivity(new Intent(MessReponseActivity.this, ForumActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuProfil:
                startActivity(new Intent(MessReponseActivity.this, ProfilJoueur.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
                            startActivity(new Intent(MessReponseActivity.this, Connexion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 416){
                        Toast.makeText(MessReponseActivity.this,"Cet utilisateur n'existe pas.",Toast.LENGTH_LONG);
                    }else {
                        Toast.makeText(MessReponseActivity.this,"Une erreur est survenue.",Toast.LENGTH_LONG);
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
