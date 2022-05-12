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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJouer;
    private Button btnForum;
    private Button btnCon;
    private Button btnBD;
    private Button profilJoueur;
    private Button btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if no user actif go to connexion
        DBWrapper bd = new DBWrapper(this, "mimotza");

        if (!bd.checkUserConnected()){
            Intent intentInsc = new Intent(MainActivity.this, Connexion.class);
            intentInsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentInsc);
        }

        // jeu
        btnJouer = (Button) findViewById(R.id.btnJouer);
        btnJouer.setOnClickListener(this);

        // inscription
        btnCon = (Button) findViewById(R.id.btnMainConn);
        btnCon.setOnClickListener(this);
        btnCon.setVisibility(View.INVISIBLE);

        // forum
        btnForum = (Button) findViewById(R.id.btnForum);
        btnForum.setOnClickListener(this);

        // BD
        btnBD = (Button) findViewById(R.id.btnBD);
        btnBD.setOnClickListener(this);
        btnBD.setVisibility(View.INVISIBLE);
        initBD();

        btns = (Button) findViewById(R.id.btnSuggestion);
        btns.setOnClickListener(this);

        profilJoueur = (Button) findViewById(R.id.btnProfiljoueur);
        profilJoueur.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJouer:
                Intent intentJouer = new Intent(MainActivity.this, Jeu.class);
                intentJouer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentJouer);
                break;
            case R.id.btnMainConn:
                Intent intentInsc = new Intent(MainActivity.this, Connexion.class);
                intentInsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentInsc);
                break;
            case R.id.btnForum:
                Intent intentForum = new Intent(MainActivity.this, ForumActivity.class);
                intentForum.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentForum);
                break;
            case R.id.btnBD:
                Intent intentBD = new Intent(MainActivity.this, TestBd.class);
                intentBD.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentBD);
                break;
            case R.id.btnProfiljoueur:
                Intent IntentP = new Intent(MainActivity.this, findProfile.class);
                IntentP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(IntentP);
                break;
            case R.id.btnSuggestion:
                Intent s = new Intent(MainActivity.this, addSuggestion.class);
                s.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(s);
                break;

            default:
                // rien :)
                break;
        }
    }

    private void initBD() {
        DBWrapper bd = new DBWrapper(this,"mimotza");

//        bd.dropTable("utilisateur");
        DBTable temp = new DBTable("utilisateur");
        temp.addColumn("idOrigin",DBType.INTEGER);// id provenant de la bd dans le serveur
        temp.addColumn("username",DBType.TEXT);
        temp.addColumn("email",DBType.TEXT);
        temp.addColumn("mdp",DBType.TEXT);
        temp.addColumn("nom",DBType.TEXT);
        temp.addColumn("prenom",DBType.TEXT);
        temp.addColumn("avatar",DBType.TEXT);
        temp.addColumn("dateCreation",DBType.TEXT);
        temp.addColumn("statut",DBType.INTEGER);
        bd.addTable(temp);

//        bd.dropTable("partie");
        temp = new DBTable("partie");
        temp.addColumn("idUser",DBType.INTEGER);
        temp.addColumn("win",DBType.INTEGER);
        temp.addColumn("score",DBType.INTEGER);
        temp.addColumn("idMot",DBType.INTEGER);
        temp.addColumn("temps",DBType.TEXT);
        temp.addColumn("date", DBType.TEXT);
        bd.addTable(temp);

//        bd.dropTable("motJouer");
        temp = new DBTable("motJouer");
        temp.addColumn("idMot",DBType.INTEGER);
        temp.addColumn("mot",DBType.TEXT);
        temp.addColumn("date",DBType.TEXT);
        bd.addTable(temp);

        bd.buildContent();

        DBHandler bdh = new DBHandler(this);
        bdh.syncMotJeu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deconnexion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
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
                            startActivity(new Intent(MainActivity.this, Connexion.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 416){
                        Toast.makeText(MainActivity.this,"Cet utilisateur n'existe pas.",Toast.LENGTH_LONG);
                    }else {
                        Toast.makeText(MainActivity.this,"Une erreur est survenue.",Toast.LENGTH_LONG);
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