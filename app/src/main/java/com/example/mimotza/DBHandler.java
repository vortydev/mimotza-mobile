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
        db.execSQL("INSERT INTO utilisateur (idOrigin,prenom,nom,username,statut) VALUES (" + pidOrigin.toString() + ",'" + pprenom + "','" + pnom + "','" + pusername + "',2)");

    }

    //
    public void insertmotJoue(Integer idMot, String mot, String date) {
        db.execSQL("INSERT INTO motJouer (idMot,mot,date) VALUES (" + idMot.toString() + ",'" + mot + "','"+date+"')");

    }

    //retourne un mot a jouer si le jouer n'a pas joue a un mot du passe
    public String[] getPartiesJoueur(Integer idUser){
        Cursor partiesJoueur = db.rawQuery("SELECT date FROM partie WHERE idUser=" +idUser.toString()+ " ORDER BY date DESC LIMIT 1" ,null);
        Cursor motJouer = db.rawQuery("select * FROM motJouer order by date DESC LIMIT 5 ",null);

        partiesJoueur.moveToFirst();
        motJouer.moveToFirst();
        String[] temp = new String[3];


        while(!motJouer.isAfterLast()){
            if(partiesJoueur.getCount() == 0 || !(partiesJoueur.getString(0).equals(motJouer.getString(2)))){
                Integer id = motJouer.getInt(0);
                temp[0] = String.valueOf(id);
                temp[1] = motJouer.getString(1);
                temp[2] = motJouer.getString(2);
                motJouer.moveToLast();
            }
            motJouer.moveToNext();
        }


        return temp;

    }



    public String getLastDateMotJeu(){

        Cursor Date = db.rawQuery("SELECT date FROM motJouer ORDER BY date DESC",null);
        Date.moveToFirst();
        return Date.getString(0);

    }

    public void syncMotJeu(){
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

                            for (int i = infoJson.length()-1; i >=0; i--) {
                                String date = infoJson.getJSONObject(i).getString("date");

                                if (date.compareTo(getLastDateMotJeu()) > 0){
                                    System.out.println("date" + date);
                                    System.out.println("date last" + getLastDateMotJeu());
                                    insertmotJoue(infoJson.getJSONObject(i).getInt("idmot"),infoJson.getJSONObject(i).getString("mot"),date);
                                }
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



