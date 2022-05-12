/****************************************
 Fichier : testBd
 Auteur : Alebrto Oviedo
 Fonctionnalité : creer la bd
 Date : 2022-05-11
 Vérification :
 Date Nom Approuvé
 2022-05-11 Alberto
 =========================================================
 Historique de modifications :
 Date Nom Description
 2022-05-05 : creation de l'objet
 2022-05-11 : commentaire et enttetes
 =========================================================
 ****************************************/


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
/**
 * TestBD.
 * @author Alberto Oviedo
 */
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
        temp.addColumn("statut",DBType.INTEGER);
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
        temp.addColumn("idMot",DBType.INTEGER);
        temp.addColumn("mot",DBType.TEXT);
        temp.addColumn("date",DBType.TEXT);
        bd.addTable(temp);
        bd.buildContent();


        bdh.syncMotJeu();
       // bdh.getPartiesJoueur(1000);
    }
}