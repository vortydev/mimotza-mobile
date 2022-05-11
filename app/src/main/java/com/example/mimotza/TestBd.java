package com.example.mimotza;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mimotza.databinding.ActivityTestBdBinding;

import java.util.HashMap;
import java.util.Map;

public class TestBd extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    private DBWrapper bd;
    private DBHandler bdh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bd);
        bd = new DBWrapper(this,"mimotza");

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
        temp = new DBTable("partie");
        temp.addColumn("idUser",DBType.INTEGER);
        temp.addColumn("win",DBType.INTEGER);
        temp.addColumn("score",DBType.INTEGER);
        temp.addColumn("idMot",DBType.INTEGER);
        temp.addColumn("temps",DBType.TEXT);

        bd.addTable(temp);
        temp = new DBTable("motJoueur");
        temp.addColumn("mot",DBType.INTEGER);
        temp.addColumn("joue",DBType.INTEGER);
        temp.addColumn("win",DBType.INTEGER);
        bd.addTable(temp);

        temp = new DBTable("motJouer");
        temp.addColumn("idOrigin",DBType.INTEGER);

        temp.addColumn("mot",DBType.INTEGER);
        bd.addTable(temp);

        temp = new DBTable("motJouer");
        temp.addColumn("idOrigin",DBType.INTEGER);

        temp.addColumn("mot",DBType.INTEGER);
        bd.addTable(temp);
        bd.buildContent();
        sendToDataBase();

    }
    private void sendToDataBase( ){
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://127.0.0.1:8000/ajoutSuggestion";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        //String url = "http://10.0.2.2:8000/ajoutSuggestion";     //emulateur

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(TestBd.this, response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //view.setText(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", "1");
                params.put("langue", "Français");
                params.put("mot", "PORTE");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_bd);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}