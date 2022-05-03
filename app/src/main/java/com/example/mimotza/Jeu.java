package com.example.mimotza;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;

public class Jeu extends AppCompatActivity {
    private GameGrid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        // creates grid object
        grid = new GameGrid(fetchCells());
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO make it a bit smoother switching characters

        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
                grid.setLetterAtPos(R.string.lettre_a);
                return true;
            case KeyEvent.KEYCODE_B:
                grid.setLetterAtPos(R.string.lettre_b);
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