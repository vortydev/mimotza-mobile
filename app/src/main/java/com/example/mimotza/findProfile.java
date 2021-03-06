/****************************************
 Fichier : ProfilJoueur
 Auteur : Alebrto Oviedo
 Fonctionnalité : petite activite qui sert a inserer un stirng pour trouver des information sur un certain user
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class findProfile extends AppCompatActivity implements View.OnClickListener{
    private EditText txt;
    private Button btnFindProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_activity);

        txt = (EditText)findViewById(R.id.editTextProfile) ;



        btnFindProfile = (Button) findViewById(R.id.FindProfile);
        btnFindProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.FindProfile:
                Intent intent = new Intent(findProfile.this, ProfilJoueur.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(findProfile.this,  txt.getText().toString(), Toast.LENGTH_LONG).show();
                intent.putExtra("user",txt.getText().toString());
                startActivity(intent);
                break;
            default:
                // rien :)
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
                startActivity(new Intent(findProfile.this, Jeu.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuSugg:
                startActivity(new Intent(findProfile.this, addSuggestion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuForum:
                startActivity(new Intent(findProfile.this, ForumActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuProfil:
                startActivity(new Intent(findProfile.this, ProfilJoueur.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                return true;
            case R.id.menuDeco:
                //deconnecter joueur avant de start intent
                disconnect();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Déconnecte le joueur de son compte
     * @author Isabelle Rioux
     */
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
                            startActivity(new Intent(findProfile.this, Connexion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 416){
                        Toast.makeText(findProfile.this,"Cet utilisateur n'existe pas.",Toast.LENGTH_LONG);
                    }else {
                        Toast.makeText(findProfile.this,"Une erreur est survenue.",Toast.LENGTH_LONG);
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