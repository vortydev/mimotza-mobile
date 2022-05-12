/****************************************
 Fichier : ForumActivity.java
 Auteur : François-Nicolas Gitzhofer
 Fonctionnalité : MMZ-M-005
 Date : 07/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 07/05/2022 Nom: François-Nicolas Gitzhofer Description: Création de l'activité
 Date: 08/05/2022 Nom: François-Nicolas Gitzhofer Description: Ajout du système de requête afin de récupérer les threads
 Date: 08/05/2022 Nom: François-Nicolas Gitzhofer Description: Finalisation de l'affichage des threads dans le forum
 =========================================================
 ****************************************/

package com.example.mimotza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForumActivity extends AppCompatActivity implements View.OnClickListener {

    private JSONObject jsonObject;
    private ArrayList<JSONObject> threadList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        threadList = new ArrayList<JSONObject>();

        recyclerView = findViewById(R.id.threadRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleThreadAdapter recycleThreadAdapter = new RecycleThreadAdapter(this, threadList);
        recyclerView.setAdapter(recycleThreadAdapter);
        Context currentContext = ForumActivity.this;

        String homeIp = "192.168.2.83";
        String cegepIp = "10.170.13.52";
        String globalIp = "10.0.2.2";

        Button backButton = findViewById(R.id.btnForumReturn);
        backButton.setOnClickListener(this);

        RequestQueue queue = Volley.newRequestQueue(ForumActivity.this);
        //String url = "http://" + cegepIp + ":8000/getAllMedia";
        //String url = "http://" + homeIp + ":8000/getAllMedia";
        String url = "http://" + globalIp + ":8000/getAllMedia";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(ForumActivity.this, "Got a response.\nResponse: " + response, Toast.LENGTH_LONG).show();

                        try {
                            jsonObject = new JSONObject(response);

                            for (int i = 0; i < jsonObject.names().length(); i++) {
                                if (jsonObject.get(jsonObject.names().getString(i)).getClass().getSimpleName().contentEquals("JSONObject")) {
                                    threadList.add(jsonObject.getJSONObject(jsonObject.names().getString(i)));
                                    recycleThreadAdapter.notifyItemInserted(0);
                                }
                            }
                            //recycleAdapter.notifyItemRangeInserted(0, threadList.size());
                            //Toast.makeText(ForumActivity.this, "JSON String to JSON object worked!", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(ForumActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForumActivity.this, "Une erreur est survenue.\n" + error, Toast.LENGTH_LONG).show();
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
            case R.id.btnForumReturn:
                startActivity(new Intent(ForumActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                finish();
                break;
        }
    }
}
