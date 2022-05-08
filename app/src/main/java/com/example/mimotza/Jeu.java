package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    // bd
    private DBWrapper bdMimotza;
    private String mdj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        loadVirtualKeyboard();

        bdMimotza = new DBWrapper(this, "mimotza");    // connect to database

        // TODO fetch mot du jour
        mdj = "FERME"; // temp

        grid = new GameGrid(this, fetchCells(), mdj, bdMimotza);   // creates grid object
        allowedInput = true;                                   // activate inputs
        win = false;                                           // obviously hasn't won yet
        score = 0;                                             // just initializing it
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!allowedInput) return false;

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

    private void enterEvent() {
        int code = grid.enterRow();
        if (code > 0) {
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
            virtualKeyboard.setVisibility(View.INVISIBLE);

            // TODO send results to server

        }
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
    public void onClick(View v) {
        if (v.getId() == R.id.vKeyBack) {
            grid.eraseLetterAtPos();
        }
        else if (v.getId() == R.id.vKeyEnter) {
            enterEvent();
        }
        else {
            grid.setLettreCellule(getLettreResource(((TextView) findViewById(v.getId())).getText().toString()));
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
}