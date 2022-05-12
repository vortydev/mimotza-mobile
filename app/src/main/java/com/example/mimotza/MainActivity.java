package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJouer;
    private Button btnForum;
    private Button btnCon;
    private Button btnBD;
    private Button profilJoueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if no user actif go to connexion
        DBWrapper bd = new DBWrapper(this, "mimotza");

        if (!bd.checkUserConnected()){
            Intent intentInsc = new Intent(MainActivity.this, Connexion.class);
            intentInsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentInsc);
        }

        // jeu
        btnJouer = (Button) findViewById(R.id.btnJouer);
        btnJouer.setOnClickListener(this);

        // inscription
        btnCon = (Button) findViewById(R.id.btnMainConn);
        btnCon.setOnClickListener(this);
        btnCon.setVisibility(View.INVISIBLE);

        // forum
        btnForum = (Button) findViewById(R.id.btnForum);
        btnForum.setOnClickListener(this);

        // BD
        btnBD = (Button) findViewById(R.id.btnBD);
        btnBD.setOnClickListener(this);
        btnBD.setVisibility(View.INVISIBLE);
        initBD();

        profilJoueur = (Button) findViewById(R.id.btnProfil);
        profilJoueur.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJouer:
                Intent intentJouer = new Intent(MainActivity.this, Jeu.class);
                intentJouer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentJouer);
                break;
            case R.id.btnMainConn:
                Intent intentInsc = new Intent(MainActivity.this, Connexion.class);
                intentInsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentInsc);
                break;
            case R.id.btnForum:
                Intent intentForum = new Intent(MainActivity.this, ForumActivity.class);
                intentForum.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentForum);
                break;
            case R.id.btnBD:
                Intent intentBD = new Intent(MainActivity.this, TestBd.class);
                intentBD.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentBD);
                break;
            case R.id.btnProfil:
                Intent IntentP = new Intent(MainActivity.this, ProfilJoueur.class);
                IntentP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(IntentP);
                break;

            default:
                // rien :)
                break;
        }
    }

    private void initBD() {
        DBWrapper bd = new DBWrapper(this,"mimotza");

        DBTable temp = new DBTable("utilisateur");
        temp.addColumn("idOrigin",DBType.INTEGER);// id provenant de la bd dans le serveur

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
        temp.addColumn("idOrigin",DBType.INTEGER);

        temp.addColumn("mot",DBType.INTEGER);
        bd.addTable(temp);

//        temp = new DBTable("motJouer");
//        temp.addColumn("idOrigin",DBType.INTEGER);

        temp.addColumn("mot",DBType.INTEGER);
        bd.addTable(temp);
        bd.buildContent();
    }
}