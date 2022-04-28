package com.example.mimotza;

import java.util.ArrayList;

public class DBTable {
    private String tableName;
    private ArrayList<DBColumn> columns;

    public DBTable(String tblName, ArrayList<DBColumn> cols) {
        tableName = tblName;
        columns = cols;
    }

    public DBTable(String tblName) {
        tableName = tblName;
        columns = new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void addColumn(String name, DBType type) {
        columns.add(new DBColumn(name, type));
    }

    public String tableToString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0 ; i < columns.size(); i++) {
            str.append(columns.get(i).colName).append(" ");
            str.append(columns.get(i).colType);

            if (i + 1 < columns.size()) {
                str.append(", ");
            }
        }

        return str.toString();
    }


    private class DBColumn {
        private String colName;
        private DBType colType;

        public DBColumn(String name, DBType type) {
            this.colName = name;
            this.colType = type;
        }

        public String getColumnName() {
            return colName;
        }

        public DBType getColumnType() {
            return colType;
        }
    }
}
