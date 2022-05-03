package com.example.mimotza;

import android.widget.TextView;

public class GameCell {
    private TextView cell;
    private int lettre;
    private CellState state;

    public GameCell(TextView c) {
        cell = c;
        lettre = 0;
        state = CellState.DEFAULT;

        updateCell();
    }

    public TextView getCell() {
        return cell;
    }

    public void setCell(TextView c) {
        cell = c;
    }

    public int getLettre() {
        return lettre;
    }

    public void setLettre(int l) {
        lettre = l;
    }

    public void setKeyPress(int l) {
        lettre = l;
        setState(CellState.OCCUPIED);
        updateCell();
    }

    public void clearCell() {
        lettre = 0;
        setState(CellState.DEFAULT);
        updateCell();
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState s) {
        state = s;
    }

    public void updateCell() {
        if (lettre == 0) {
            cell.setText("");
        }
        else {
            cell.setText(lettre);
        }

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
}
