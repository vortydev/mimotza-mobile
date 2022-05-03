package com.example.mimotza;

import android.widget.TextView;

import java.util.ArrayList;

public class GameGrid {
    private GameCell[][] grid;
    private int yPos, xPos;

    public GameGrid(ArrayList<TextView> cells) {
        grid = new GameCell[6][5];
        yPos = xPos = 0;

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                grid[y][x] = new GameCell(cells.get((y * 5) + x));
            }
        }
    }

    public void setLetterAtPos(int lettre) {
        if (xPos < 5) {
            grid[yPos][xPos].setKeyPress(lettre);

            xPos++;
            if (xPos < 4) {

            }
        }
    }

    public void eraseLetterAtPos() {
        if (xPos >= 0) {
            if (xPos > 0) {
                xPos--;
            }

            grid[yPos][xPos].clearCell();
        }
    }

    public void enterRow() {
        if (xPos < 5) return;

        xPos = 0;
        yPos++;

        // TODO get user input

        // TODO fetch mot du jour

        // TODO compare input and mdj

        // TODO update row colors
    }
}
