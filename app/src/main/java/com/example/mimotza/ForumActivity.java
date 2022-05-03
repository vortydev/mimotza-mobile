package com.example.mimotza;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ForumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context currentContext = ForumActivity.this;

        TextView textAuteur = findViewById(R.id.auteur);
        TextView textTitre = findViewById(R.id.titre);
        TextView nbCommentaires = findViewById(R.id.nbComments);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://127.0.0.1:8000/getAllMedia";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ForumActivity.this, "Got a response", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForumActivity.this, "That didn't work!\n" + error, Toast.LENGTH_SHORT).show();
            }
        });

        textAuteur.setText("HEY");
    }
}
