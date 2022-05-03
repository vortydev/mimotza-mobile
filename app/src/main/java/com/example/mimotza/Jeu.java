package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activité du jeu Mi-Mot-Za.
 * @author Étienne Ménard
 */
public class Jeu extends AppCompatActivity {
    private GameGrid grid;
    private DBWrapper bdMimotza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        // connect to database
        bdMimotza = new DBWrapper(this, "mimotza");

        // creates grid object
        grid = new GameGrid(fetchCells());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
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
            case KeyEvent.KEYCODE_ENTER:
                grid.enterRow();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
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
}