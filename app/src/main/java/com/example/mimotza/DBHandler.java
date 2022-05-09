package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Amped up SQLite database controller.
 * @author Alberto Oviedo
 */
public class DBHandler {
    private Context context;
    private String dbName;

    private static SQLiteDatabase db;


    /**
     * Class constructor.
     * @param c Activity's context.
     *
     */
    public DBHandler(Context c) {
        context = c;
        dbName = "mimotza";

        db = c.openOrCreateDatabase(dbName, c.MODE_PRIVATE, null);

    }

    public void insertPartie(Integer idUser,Integer win,Integer score,String temps,String dateEmission,Integer idMot){
        db.execSQL("INSERT INTO partie(idUser,win,score,temps,dateEmission,idMot) " +
        " values("+idUser.toString()+","+win.toString()+","+score.toString()+","+temps+","+dateEmission+","+idMot.toString()+")");
    }
    public void insertUtilisateur(Integer pidOrigin,String pusername,String pnom,String pprenom){
        db.execSQL("INSERT INTO utilisateur(idOrigin,username,nom,prenom) " +
                "values("+pidOrigin.toString()+","+pusername+","+pnom+","+pprenom+")");
    }


}