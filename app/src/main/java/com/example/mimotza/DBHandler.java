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


    //permet lajout d'une partie
    public void insertPartie(Integer idUser,Integer win,Integer score,String temps,Integer idMot){
        db.execSQL("INSERT INTO partie(idUser,win,score,temps,idMot) " +
        " values("+idUser.toString()+","+win.toString()+","+score.toString()+",'"+temps+"',"+idMot.toString()+")");
    }

    //permet l'ajout d'un joueur
    public void insertUser(Integer pidOrigin,String pusername,String pnom,String pprenom){
        db.execSQL("INSERT INTO utilisateur (idOrigin,prenom,nom,username) VALUES ("+pidOrigin.toString()+",'"+pprenom+"','"+pnom+"','"+pusername+"')");

    }

    public void insertmotJoue(Integer pidOrigin,Integer idMot){
        db.execSQL("INSERT INTO utilisateur (idOrigin,mot) VALUES ("+pidOrigin.toString()+","+idMot.toString()+")");

    }





}