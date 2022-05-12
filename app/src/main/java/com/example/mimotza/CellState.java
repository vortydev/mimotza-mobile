/****************************************
 Fichier : CellState.java
 Auteur : Étienne Ménard
 Fonctionnalité : MMZ-M-001 Jouer
 Date : 03/05/2022
 Vérification :
 Date Nom Approuvé
 =========================================================
 Historique de modifications :
 Date: 03/05/2022 Nom: Étienne Ménard Description: Implémentation des cellules et de la grille de jeu.
 =========================================================
 ****************************************/
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
