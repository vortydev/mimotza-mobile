package com.example.mimotza;

import android.widget.TextView;

public class GameCell {
    private TextView cell;
    private String lettre;
    private CellState state;

    public GameCell(TextView c) {
        cell = c;
        lettre = null;
        state = CellState.DEFAULT;

        updateCell();
    }

    public TextView getCell() {
        return cell;
    }

    public void setCell(TextView c) {
        cell = c;
    }

    public String getLettre() {
        return lettre;
    }

    public void setLettre(String l) {
        assert(l.length() == 1);
        lettre = l;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState s) {
        state = s;
    }

    public void updateCell() {
        if (lettre == null) {
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
