package com.example.mimotza;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mimotza.databinding.ActivityTestBdBinding;

public class TestBd extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestBdBinding binding;

    private DBWrapper bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bd);
        bd = new DBWrapper(this,"mimotza");
        DBTable temp = new DBTable("statut");
        temp.addColumn("statut",DBType.TEXT);
        bd.addTable(temp);

        temp = new DBTable("utilisateur");
        temp.addColumn("idOrigin",DBType.INTEGER);// id provenant de la bd dans le serveur

        temp.addColumn("idStatut",DBType.INTEGER);
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
        temp.addColumn("temps",DBType.TEXT);
        temp.addColumn("dateEmission",DBType.TEXT);
        temp.addColumn("idMot",DBType.INTEGER);
        bd.addTable(temp);
        bd.buildContent();


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_bd);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}