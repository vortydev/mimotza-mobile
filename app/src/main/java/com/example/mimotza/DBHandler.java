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
     * @param dbn Database's name.
     */
    public DBHandler(Context c, String dbn) {
        context = c;
        dbName = dbn;

        db = c.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);

    }

    public void insertPartie(Integer idUser,Integer win,Integer score,String temps,String dateEmission,Integer idMot){
        db.execSQL("INSERT INTO partie(idUser,win,score,temps,dateEmission,idMot) " +
        " values("+idUser.toString()+","+win.toString()+","+score.toString()+","+temps+","+dateEmission+","+idMot.toString()+")");
    }
    public void insertUtilisateur(Integer idOrigin,Integer idStatut,String username,String email,String nom,String prenom,String avatar){
        db.execSQL("INSERT INTO utilisateur(idUser,win,score,temps,dateEmission,idMot) " +
                " values("+idOrigin.toString()+","+idStatut.toString()+","+username+","+email+","+nom+","+prenom+")");
    }


}