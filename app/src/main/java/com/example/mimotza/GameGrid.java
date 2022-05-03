package com.example.mimotza;

import android.widget.TextView;

import java.util.ArrayList;

public class GameGrid {
    private GameCell[][] grid;

    public GameGrid(ArrayList<TextView> cells) {
        grid = new GameCell[6][5];

        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                grid[y][x] = new GameCell(cells.get((y * 5) + x));
            }
        }
    }
}
