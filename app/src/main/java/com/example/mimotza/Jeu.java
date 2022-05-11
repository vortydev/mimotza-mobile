/****************************************
 Fichier : Jeu.java
 Auteur : Étienne Ménard
 Fonctionnalité : MMZ-M-001 Jouer
 Date : 01/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 01/05/2022 Nom: Étienne Ménard Description: Création de l'activité pour le jeu.
 Date: 03/05/2022 Nom: Étienne Ménard Description: Implémentation des cellules et de la grille de jeu.
 Date: 03/05/2022 Nom: Étienne Ménard Description: Implémentation des touches de clavier.
 Date: 03/05/2022 Nom: Étienne Ménard Description: Implémentation de l'algorithme de validation des mots.
 Date: 05/05/2022 Nom: Étienne Ménard Description: Implémentation du clavier virtuel.
 Date: 08/05/2022 Nom: Étienne Ménard Description: Modification de l'algorithme pour valider des lettres récurrentes.
 Date: 09/05/2022 Nom: Étienne Ménard Description: Implémentation de la fonction pour reset la grille.
 =========================================================
 ****************************************/
package com.example.mimotza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Activité du jeu Mi-Mot-Za.
 * @author Étienne Ménard
 */
public class Jeu extends AppCompatActivity implements View.OnClickListener {
    //  jeu
    private GameGrid grid;
    private boolean allowedInput;
    private boolean win;
    private int score;
    private ArrayList<TextView> vKeyb;
    private View virtualKeyboard;
    private long rowTimeStart;

    // bd
    private DBWrapper bdMimotza;
    private String mdj;

    // boutons
    private Button btnSuggestion;
    private Button btnRetour;
    private Button btnNextMot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        btnSuggestion = (Button) findViewById(R.id.btnSuggestion);
        btnRetour = (Button) findViewById(R.id.btnRetourJeu);
        btnNextMot = (Button) findViewById(R.id.btnNextMot);
        btnSuggestion.setOnClickListener(this);
        btnRetour.setOnClickListener(this);
        btnNextMot.setOnClickListener(this);
        loadVirtualKeyboard();

        bdMimotza = new DBWrapper(this, "mimotza");    // connect to database
        // TODO fetch mot du jour
        mdj = "AVION"; // temp

        showVirtualKeyboard(true);
        showPostGameNav(false);
        // TODO disable the game if the user already won

        grid = new GameGrid(this, fetchCells(), mdj);   // creates grid object
        allowedInput = true;                                   // activate inputs
        win = false;                                           // obviously hasn't won yet
        score = 0;                                             // just initializing it
    }

    /**
     * Retourne une liste de toutes les cellules de la grille.
     * @author Étienne Ménard
     * @return ArrayList de TextViews
     */
    private ArrayList<TextView> fetchCells() {
        ArrayList<TextView> cells = new ArrayList<>();
        cells.add(findViewById(R.id.r1l1));
        cells.add(findViewById(R.id.r1l2));
        cells.add(findViewById(R.id.r1l3));
        cells.add(findViewById(R.id.r1l4));
        cells.add(findViewById(R.id.r1l5));
        cells.add(findViewById(R.id.r2l1));
        cells.add(findViewById(R.id.r2l2));
        cells.add(findViewById(R.id.r2l3));
        cells.add(findViewById(R.id.r2l4));
        cells.add(findViewById(R.id.r2l5));
        cells.add(findViewById(R.id.r3l1));
        cells.add(findViewById(R.id.r3l2));
        cells.add(findViewById(R.id.r3l3));
        cells.add(findViewById(R.id.r3l4));
        cells.add(findViewById(R.id.r3l5));
        cells.add(findViewById(R.id.r4l1));
        cells.add(findViewById(R.id.r4l2));
        cells.add(findViewById(R.id.r4l3));
        cells.add(findViewById(R.id.r4l4));
        cells.add(findViewById(R.id.r4l5));
        cells.add(findViewById(R.id.r5l1));
        cells.add(findViewById(R.id.r5l2));
        cells.add(findViewById(R.id.r5l3));
        cells.add(findViewById(R.id.r5l4));
        cells.add(findViewById(R.id.r5l5));
        cells.add(findViewById(R.id.r6l1));
        cells.add(findViewById(R.id.r6l2));
        cells.add(findViewById(R.id.r6l3));
        cells.add(findViewById(R.id.r6l4));
        cells.add(findViewById(R.id.r6l5));
        return cells;
    }

    /**
     * Initialise le clavier virtuel.
     * @author Étienne Ménard
     */
    private void loadVirtualKeyboard() {
        virtualKeyboard = findViewById(R.id.vKeyboard);
        virtualKeyboard.setVisibility(View.VISIBLE);

        vKeyb = new ArrayList<>();
        vKeyb.add(findViewById(R.id.vKeyA));
        vKeyb.add(findViewById(R.id.vKeyB));
        vKeyb.add(findViewById(R.id.vKeyC));
        vKeyb.add(findViewById(R.id.vKeyD));
        vKeyb.add(findViewById(R.id.vKeyE));
        vKeyb.add(findViewById(R.id.vKeyF));
        vKeyb.add(findViewById(R.id.vKeyG));
        vKeyb.add(findViewById(R.id.vKeyH));
        vKeyb.add(findViewById(R.id.vKeyI));
        vKeyb.add(findViewById(R.id.vKeyJ));
        vKeyb.add(findViewById(R.id.vKeyK));
        vKeyb.add(findViewById(R.id.vKeyL));
        vKeyb.add(findViewById(R.id.vKeyM));
        vKeyb.add(findViewById(R.id.vKeyN));
        vKeyb.add(findViewById(R.id.vKeyO));
        vKeyb.add(findViewById(R.id.vKeyP));
        vKeyb.add(findViewById(R.id.vKeyQ));
        vKeyb.add(findViewById(R.id.vKeyR));
        vKeyb.add(findViewById(R.id.vKeyS));
        vKeyb.add(findViewById(R.id.vKeyT));
        vKeyb.add(findViewById(R.id.vKeyU));
        vKeyb.add(findViewById(R.id.vKeyV));
        vKeyb.add(findViewById(R.id.vKeyW));
        vKeyb.add(findViewById(R.id.vKeyX));
        vKeyb.add(findViewById(R.id.vKeyY));
        vKeyb.add(findViewById(R.id.vKeyZ));
        vKeyb.add(findViewById(R.id.vKeyEnter));
        vKeyb.add(findViewById(R.id.vKeyBack));

        for (TextView v: vKeyb) {
            v.setOnClickListener(this::onClick);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!allowedInput) return false;

        initRowTime();

        // my god that's tedious
        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
                grid.setLettreCellule(R.string.lettre_a);
                return true;
            case KeyEvent.KEYCODE_B:
                grid.setLettreCellule(R.string.lettre_b);
                return true;
            case KeyEvent.KEYCODE_C:
                grid.setLettreCellule(R.string.lettre_c);
                return true;
            case KeyEvent.KEYCODE_D:
                grid.setLettreCellule(R.string.lettre_d);
                return true;
            case KeyEvent.KEYCODE_E:
                grid.setLettreCellule(R.string.lettre_e);
                return true;
            case KeyEvent.KEYCODE_F:
                grid.setLettreCellule(R.string.lettre_f);
                return true;
            case KeyEvent.KEYCODE_G:
                grid.setLettreCellule(R.string.lettre_g);
                return true;
            case KeyEvent.KEYCODE_H:
                grid.setLettreCellule(R.string.lettre_h);
                return true;
            case KeyEvent.KEYCODE_I:
                grid.setLettreCellule(R.string.lettre_i);
                return true;
            case KeyEvent.KEYCODE_J:
                grid.setLettreCellule(R.string.lettre_j);
                return true;
            case KeyEvent.KEYCODE_K:
                grid.setLettreCellule(R.string.lettre_k);
                return true;
            case KeyEvent.KEYCODE_L:
                grid.setLettreCellule(R.string.lettre_l);
                return true;
            case KeyEvent.KEYCODE_M:
                grid.setLettreCellule(R.string.lettre_m);
                return true;
            case KeyEvent.KEYCODE_N:
                grid.setLettreCellule(R.string.lettre_n);
                return true;
            case KeyEvent.KEYCODE_O:
                grid.setLettreCellule(R.string.lettre_o);
                return true;
            case KeyEvent.KEYCODE_P:
                grid.setLettreCellule(R.string.lettre_p);
                return true;
            case KeyEvent.KEYCODE_Q:
                grid.setLettreCellule(R.string.lettre_q);
                return true;
            case KeyEvent.KEYCODE_R:
                grid.setLettreCellule(R.string.lettre_r);
                return true;
            case KeyEvent.KEYCODE_S:
                grid.setLettreCellule(R.string.lettre_s);
                return true;
            case KeyEvent.KEYCODE_T:
                grid.setLettreCellule(R.string.lettre_t);
                return true;
            case KeyEvent.KEYCODE_U:
                grid.setLettreCellule(R.string.lettre_u);
                return true;
            case KeyEvent.KEYCODE_V:
                grid.setLettreCellule(R.string.lettre_v);
                return true;
            case KeyEvent.KEYCODE_W:
                grid.setLettreCellule(R.string.lettre_w);
                return true;
            case KeyEvent.KEYCODE_X:
                grid.setLettreCellule(R.string.lettre_x);
                return true;
            case KeyEvent.KEYCODE_Y:
                grid.setLettreCellule(R.string.lettre_y);
                return true;
            case KeyEvent.KEYCODE_Z:
                grid.setLettreCellule(R.string.lettre_z);
                return true;
            case KeyEvent.KEYCODE_DEL:
                grid.eraseLetterAtPos();
                return true;
            case KeyEvent.KEYCODE_ENTER:        // OVER HERE
                enterEvent();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        // boutons réguliers
        switch (v.getId()) {
            case R.id.btnRetourJeu:
                Intent intentRetourJeu = new Intent(Jeu.this, MainActivity.class);
                intentRetourJeu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentRetourJeu);
                return;
            case R.id.btnSuggestion:
                // TODO start suggestion activity
                return;
            case R.id.btnNextMot:
                grid.resetGrid();
                showPostGameNav(false);
                showVirtualKeyboard(true);
                allowedInput = true;
                // TODO fetch next word
                return;
            default: break;
        }

        // clavier virtuel
        if (v.getId() == R.id.vKeyBack) {
            grid.eraseLetterAtPos();
        }
        else if (v.getId() == R.id.vKeyEnter) {
            enterEvent();
        }
        else {
            initRowTime();
            grid.setLettreCellule(getLettreResource(((TextView) findViewById(v.getId())).getText().toString()));
        }
    }

    /**
     * Appelé lorsque l'utilisateur clique Enter.
     * @author Étienne Ménard
     */
    private void enterEvent() {
        int code = grid.enterRow();
        if (code > 0) {
            // end of game
            if (code > 6) {
                // out of tries
                score = 6;
                Toast.makeText(this, "Vous avez lamentablement échoué...", Toast.LENGTH_LONG).show();
            }
            else {
                // si on gagne
                win = true;
                score = code;
                Toast.makeText(this, "Vous avez gagné!", Toast.LENGTH_LONG).show();
            }
            allowedInput = false;

//            sendToDataBase();

            virtualKeyboard.setVisibility(View.INVISIBLE);
            showPostGameNav(true);
        }
    }

    /**
     * Retourne la resource string de la lettre.
     * @author Étienne Ménard
     * @param l Lettre affichée sur la cellule.
     * @return String resource.
     */
    private int getLettreResource(String l) {
        switch (l) {
            case "A": return R.string.lettre_a;
            case "B": return R.string.lettre_b;
            case "C": return R.string.lettre_c;
            case "D": return R.string.lettre_d;
            case "E": return R.string.lettre_e;
            case "F": return R.string.lettre_f;
            case "G": return R.string.lettre_g;
            case "H": return R.string.lettre_h;
            case "I": return R.string.lettre_i;
            case "J": return R.string.lettre_j;
            case "K": return R.string.lettre_k;
            case "L": return R.string.lettre_l;
            case "M": return R.string.lettre_m;
            case "N": return R.string.lettre_n;
            case "O": return R.string.lettre_o;
            case "P": return R.string.lettre_p;
            case "Q": return R.string.lettre_q;
            case "R": return R.string.lettre_r;
            case "S": return R.string.lettre_s;
            case "T": return R.string.lettre_t;
            case "U": return R.string.lettre_u;
            case "V": return R.string.lettre_v;
            case "W": return R.string.lettre_w;
            case "X": return R.string.lettre_x;
            case "Y": return R.string.lettre_y;
            case "Z": return R.string.lettre_z;
            default: return 0;
        }
    }

    /**
     * Initialise la timestamp du début de la partie.
     * @author Étienne Ménard
     */
    private void initRowTime() {
        if (rowTimeStart == 0) {
            rowTimeStart = Calendar.getInstance().getTimeInMillis();
        }
    }

    /**
     * Retourne le temps pris pour la partie.
     * @author Étienne Ménard
     * @return Temps de la partie en millisecondes.
     */
    private long getRowTime() {
        return Calendar.getInstance().getTimeInMillis() - rowTimeStart;
    }

    /**
     * Set la visibilité des éléments de navigation post-game.
     * @author Étienne Ménard
     * @param state Visibilité des éléments.
     */
    public void showPostGameNav(boolean state) {
        if (state) {
            btnSuggestion.setVisibility(View.VISIBLE);
            btnRetour.setVisibility(View.VISIBLE);
            btnNextMot.setVisibility(View.VISIBLE);
        }
        else {
            btnSuggestion.setVisibility(View.INVISIBLE);
            btnRetour.setVisibility(View.INVISIBLE);
            btnNextMot.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Set la visibilité du clavier virtuel.
     * @author Étienne Ménard
     * @param state Visibilité du clavier virtuel.
     */
    public void showVirtualKeyboard(boolean state) {
        if (state) {
            virtualKeyboard.setVisibility(View.VISIBLE);
        }
        else {
            virtualKeyboard.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Sauvegarde les données de la partie dans la BD locale.
     * @author Étienne Ménard
     */
    private void sendToDataBase() {
        // TODO get user id
        // TODO get MDJ id
        bdMimotza.insertPartie(1, (win ? 1 : 0), score, new SimpleDateFormat("HH:mm:ss").format(getRowTime()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()), 16);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.menuJouer:
                Intent intentJouer = new Intent(Jeu.this, Jeu.class);
                intentJouer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentJouer);
                return true;
            case R.id.menuForum:
                Intent intentForum = new Intent(Jeu.this, ForumActivity.class);
                intentForum.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentForum);
                return true;
            case R.id.menuProfil:
                Intent IntentP = new Intent(Jeu.this, ProfilJoueur.class);
                IntentP.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(IntentP);
                return true;
            case R.id.menuDeco:
                //deconnecter joueur avant de start intent
                //Intent intentInsc = new Intent(Jeu.this, Connexion.class);
                //intentInsc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intentInsc);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}