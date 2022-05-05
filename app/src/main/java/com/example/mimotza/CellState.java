package com.example.mimotza;

/**
 * Enum des états d'une cellule.
 * @author Étienne Ménard
 */
public enum CellState {
    DEFAULT,    // rien
    OCCUPIED,   // lettre
    BAD,        // lettre pas dans le mot
    GOOD,       // lettre dans le mot
    VALID       // lettre à la bonne place
}
