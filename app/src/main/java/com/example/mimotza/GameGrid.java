package com.example.mimotza;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Grille et controleur des cellules du jeu Mi-Mot-Za.
 * @author Étienne Ménard
 */
public class GameGrid {
    private GameCell[][] grid;
    private int yPos, xPos;     // pointeurs sur la grille

    /**
     * Constructeur de l'objet GameGrid.
     * @author Étienne Ménard
     * @param cells Liste des cellules de la grille.
     */
    public GameGrid(ArrayList<TextView> cells) {
        grid = new GameCell[6][5];
        yPos = xPos = 0;

        // okay, je dois expliquer un peu ce qui se passe ici.
        // on vient de passer une liste des cellules (TextView) de la grille,
        // mais on a généré un tableau bidimensionel d'objets GameCell.
        // on a ici une double boucle qui créé les Cellules dans l'ordre.
        // note: il faut lire la grille comme "GameCell[Y][X]".
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x] = new GameCell(cells.get((y * 5) + x));
                // ex. Ligne 1 Lettre 3 : (0 * 5) + 2 = 2 (3ème dans la liste)
                // ex. Ligne 4 Lettre 5 : (3 * 5) + 4 = 19 (20ème dans la liste)
            }
        }
    }

    /**
     * Affiche la lettre appuyée sur la cellule.
     * @author Étienne Ménard
     * @param l ID du string statique de la lettre dans l'application.
     */
    public void setLettreCellule(int l) {
        // prévient d'entrer plus de 5 lettres
        if (xPos >= 5) return;

        grid[yPos][xPos].setKeyPress(l);
        xPos++; // incrémente le pointeur X
    }

    /**
     * Efface la lettre de la cellule.
     * @author Étienne Ménard
     */
    public void eraseLetterAtPos() {
        // prévient d'effacer hors de la grille
        if (xPos <= 0) return;

        xPos--;
        grid[yPos][xPos].clearCell();
    }

    /**
     * Soumets la ligne à la vérification.
     * @author Étienne Ménard
     */
    public void enterRow() {
        // prévient d'entrer une ligne incomplète
        if (xPos < 5) return;

        // prévient d'envoyer plus de 6 lignes
        if (yPos > 6) return;

        xPos = 0;   // reset X
        yPos++;     // incrémente Y

        // TODO get user input

        // TODO fetch mot du jour

        // TODO compare input and mdj

        // TODO update row colors
    }
}
