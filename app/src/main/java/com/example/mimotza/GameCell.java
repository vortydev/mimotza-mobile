package com.example.mimotza;

import android.widget.TextView;

/**
 * Cellule de la grille du jeu Mi-Mot-Za.
 * @author Étienne Ménard
 */
public class GameCell {
    private TextView cell;
    private int lettre;
    private CellState state;

    /**
     * Constructeur de l'objet Cellule.
     * @author Étienne Ménard
     * @param v TextView de la cellule.
     */
    public GameCell(TextView v) {
        cell = v;
        lettre = 0;
        state = CellState.DEFAULT;

        updateCell();
    }

    /**
     * Affiche la lettre envoyée par une touche de clavier sur la cellule.
     * @author Étienne Ménard
     * @param l ID du string statique de la lettre dans l'application.
     */
    public void setKeyPress(int l) {
        lettre = l;
        setState(CellState.OCCUPIED);
        updateCell();
    }

    /**
     * Clear la lettre de la cellule.
     * @author Étienne Ménard
     */
    public void clearCell() {
        lettre = 0;
        setState(CellState.DEFAULT);
        updateCell();
    }

    /**
     * Set l'état de la cellule, définit par l'enum CellState.
     * @author Étienne Ménard
     * @param s State de la cellule (DEFAULT, OCCUPIED, BAD, GOOD, VALID)
     */
    public void setState(CellState s) {
        state = s;
    }

    /**
     * Met à jour le state de la cellule.
     * @author Étienne Ménard
     */
    private void updateState() {
        switch (state) {
            case OCCUPIED:
                cell.setBackgroundResource(R.drawable.cell_occupied);
                break;
            case BAD:
                cell.setBackgroundResource(R.drawable.cell_bad);
                break;
            case GOOD:
                cell.setBackgroundResource(R.drawable.cell_good);
                break;
            case VALID:
                cell.setBackgroundResource(R.drawable.cell_valid);
                break;
            default:
                cell.setBackgroundResource(R.drawable.cell_default);
                break;
        }
    }

    /**
     * Met à jour la lettre de la cellule.
     * @author Étienne Ménard
     */
    private void updateLettre() {
        if (lettre == 0) {
            cell.setText("");
        }
        else {
            cell.setText(lettre);
        }
    }

    /**
     * Met à jour la cellule.
     * @author Étienne Ménard
     */
    private void updateCell() {
        updateState();
        updateLettre();
    }
}
