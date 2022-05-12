package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class addSuggestion extends AppCompatActivity implements View.OnClickListener {
    private EditText txt;
    private Button btnFindProfile;
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
                Intent intent = new Intent(addSuggestion.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(addSuggestion.this,  "La suggestion a été fait", Toast.LENGTH_LONG).show();
                sendSuggestion();
                startActivity(intent);
                break;
            default:
                // rien :)
                break;
        }
    }

    private void sendSuggestion(){
        Intent info = getIntent();
   
        RequestQueue queue = Volley.newRequestQueue(this);


        TextView titleProfile = findViewById(R.id.usernameProfil);
        //String url = "http://127.0.0.1:8000/userProfile";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        //String url = "http://10.0.2.2:8000/ajoutSuggestion";     //emulateur
        String url = "http://10.0.0.246:8000/ajoutSuggestion";     //home
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Toast.makeText(addSuggestion.this,"Réponse du serveur!",Toast.LENGTH_LONG).show();
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
                params.put("langue", "Français");
                params.put("mot", txt.getText().toString());
                params.put("idUser", String.valueOf(1));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}