package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Amped up SQLite database controller.
 * @author Alberto Oviedo
 */
public class DBHandler {
    private Context context;

    private static SQLiteDatabase db;

    /**
     * Class constructor.
     *
     * @param c Activity's context.
     */
    public DBHandler(Context c) {
        context = c;
        db = c.openOrCreateDatabase("mimotza", c.MODE_PRIVATE, null);
    }


    //permet lajout d'une partie
    public void insertPartie(Integer idUser, Integer win, Integer score, String temps, Integer idMot) {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        db.execSQL("INSERT INTO partie(idUser,win,score,temps,idMot,date) " +
                " values(" + idUser.toString() + "," + win.toString() + "," + score.toString() + ",'" + temps + "'," + idMot.toString() + "," + today + ")");
    }

    //permet l'ajout d'un joueur
    public void insertUser(Integer pidOrigin, String pusername, String pnom, String pprenom) {
        db.execSQL("INSERT INTO utilisateur (idOrigin,prenom,nom,username) VALUES (" + pidOrigin.toString() + ",'" + pprenom + "','" + pnom + "','" + pusername + "')");

    }

    //
    public void insertmotJoue(Integer pidOrigin, Integer idMot) {
        db.execSQL("INSERT INTO utilisateur (idUser,mot) VALUES (" + pidOrigin.toString() + "," + idMot.toString() + ")");

    }
    /*
    public String[] getPartiesJoueur(int idUser){
       // Cursor PartiesJoueur = db.execSQL("SELECT () VALUES ("+pidOrigin.toString()+","+idMot.toString()+")");
    }

 */

    public String getLastDateMotJeu(){
        Cursor Date = db.rawQuery("SELECT date FROM motJouer where rowid =(SELECT  last_insert_rowid() FROM  motJouer)",null);
        Date.moveToFirst();
        return Date.getString(0);

    }

    private void syncMotJeu(){

        RequestQueue queue = Volley.newRequestQueue(context);


        //String url = "http://127.0.0.1:8000/userProfile";  //cell isa instructions : https://dev.to/tusharsadhwani/connecting-android-apps-to-localhost-simplified-57lm
        //String url = "http://10.0.2.2:8000/ajoutSuggestion";     //emulateur
        String url = "http://10.0.0.246:8000/syncMotJeu";     //home
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray infoJson = null;
                        try {
                            Toast.makeText(context, response, Toast.LENGTH_LONG);
                            infoJson = new JSONArray(response);

                            for (int i = 0; i < infoJson.length(); i++) {
                                String date = infoJson.getJSONObject(i).getString("date");

                            }


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

                return params;
            }
        };
        queue.add(stringRequest);



    }
}



