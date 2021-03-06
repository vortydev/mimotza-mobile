/****************************************
 Fichier : GameGrid.java
 Auteur : Étienne Ménard
 Fonctionnalité : MMZ-M-001 Jouer
 Date : 03/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 03/05/2022 Nom: Étienne Ménard Description: Implémentation des cellules et de la grille de jeu.
 Date: 03/05/2022 Nom: Étienne Ménard Description: Implémentation de l'algorithme de validation des mots.
 Date: 08/05/2022 Nom: Étienne Ménard Description: Modification de l'algorithme pour valider des lettres récurentes.
 Date: 09/05/2022 Nom: Étienne Ménard Description: Implémentation de la fonction pour reset la grille.
 =========================================================
 ****************************************/
package com.example.mimotza;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Grille et controleur des cellules du jeu Mi-Mot-Za.
 * @author Étienne Ménard
 */
public class GameGrid {
    private Context context;
    private GameCell[][] grid;
    private int yPos, xPos;     // pointeurs sur la grille
    private String mdj;         // mot du jour

    /**
     * Constructeur de l'objet GameGrid.
     * @author Étienne Ménard
     * @param cells Liste des cellules de la grille.
     */
    public GameGrid(Context context, ArrayList<TextView> cells, String motDuJour) {
        this.context = context;
        grid = new GameCell[6][5];
        yPos = xPos = 0;
        mdj = motDuJour;

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
     * @return 0: mot pas valide, 1-6: score, 7: out of tries
     */
    public int enterRow() {
        if (xPos < 5) return -1;     // prévient d'entrer une ligne incomplète
        if (yPos > 6) return -1;     // prévient d'envoyer plus de 6 lignes

        StringBuilder str = new StringBuilder();
        for (int x = 0; x < grid[yPos].length; x++) {
            str.append(context.getString(grid[yPos][x].getLettre()));
        }

        // retourne un tableau des states des cellules de la ligne
        CellState[] res = solvingAlgorithm(str.toString());

        updateCells(res);   // update les-dites cellules

        xPos = 0;   // reset X
        yPos++;     // incrémente Y

        for (int i = 0; i < res.length; i++) {
            if (res[i] != CellState.VALID && yPos >= 6) return 7;   // hors essays
            else if (res[i] != CellState.VALID) return 0;           // le mot n'est pas valide
        }

        return yPos;    // mot valide!
    }

    /**
     * Compares et retourne le résultat.
     * @author Étienne Ménard
     * @param mot Mot de l'utilisateur.
     * @return Liste des états des cellules de la ligne.
     */
    private CellState[] solvingAlgorithm(String mot) {
        CellState[] states = new CellState[5];
        boolean[] found = new boolean[5];

        // VALID pass
        for (int i = 0; i < mot.length(); i++) {
            for (int j = 0; j < mdj.length(); j++) {

                if (mot.charAt(i) == mdj.charAt(j) && i == j) {
                    states[i] = CellState.VALID;
                    found[j] = true;
                    j = mot.length();
                }
            }
        }

        // GOOD pass
        for (int i = 0; i < mot.length(); i++) {
            for (int j = 0; j < mdj.length(); j++) {
                if (mot.charAt(i) == mdj.charAt(j) && !found[j] && states[i] != CellState.VALID) {
                    states[i] = CellState.GOOD;
                    found[j] = true;
                    j = mot.length();
                }
            }
        }

        // BAD pass
        for (int i = 0; i < mot.length(); i++) {
            if (states[i] != CellState.VALID && states[i] != CellState.GOOD) {
                states[i] = CellState.BAD;
            }
        }

        return states;
    }

    /**
     * Met à jour les cellules de la ligne entrée.
     * @author Étienne Ménard
     * @param states
     */
    private void updateCells(CellState[] states) {
        for (int i = 0; i < grid[yPos].length; i++) {
            grid[yPos][i].updateState(states[i]);
        }
    }

    /**
     * Reset la grille.
     * @author Étienne Ménard
     */
    public void resetGrid() {
        // clear cells
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                grid[y][x].clearCell();
            }
        }
        xPos = yPos = 0;
    }
}
