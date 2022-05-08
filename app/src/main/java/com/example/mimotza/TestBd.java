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
        bdh = new DBHandler(this);

        DBTable temp = new DBTable("utilisateur");
        temp.addColumn("idOrigin",DBType.INTEGER);// id provenant de la bd dans le serveur

        temp.addColumn("username",DBType.TEXT);
        temp.addColumn("email",DBType.TEXT);
        temp.addColumn("mdp",DBType.TEXT);
        temp.addColumn("nom",DBType.TEXT);
        temp.addColumn("prenom",DBType.TEXT);
        temp.addColumn("avatar",DBType.TEXT);
        temp.addColumn("dateCreation",DBType.TEXT);
        bd.addTable(temp);
        temp = new DBTable("partie");
        temp.addColumn("idUser",DBType.INTEGER);
        temp.addColumn("win",DBType.INTEGER);
        temp.addColumn("score",DBType.INTEGER);
        temp.addColumn("idMot",DBType.INTEGER);
        temp.addColumn("temps",DBType.TEXT);
        temp.addColumn("date",DBType.TEXT);
        bd.addTable(temp);
        temp = new DBTable("motJouer");
        temp.addColumn("idMot",DBType.INTEGER);
        temp.addColumn("mot",DBType.TEXT);
        temp.addColumn("date",DBType.TEXT);
        bd.addTable(temp);
        bd.buildContent();

        //bdh.insertUser(1000,"test","st1","test2");
        //bdh.insertPartie(1,0,1,"5h",100);


    }



}