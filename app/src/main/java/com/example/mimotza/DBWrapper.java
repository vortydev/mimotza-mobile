package com.example.mimotza;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Amped up SQLite database controller.
 * @author Étienne Ménard
 */
public class DBWrapper {
    private Context context;
    private String dbName;

    private static SQLiteDatabase db;
    private ArrayList<DBTable> tables;

    /**
     * Class constructor.
     * @param c Activity's context.
     * @param dbn Database's name.
     */
    public DBWrapper(Context c, String dbn) {
        context = c;
        dbName = dbn;

        db = c.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        tables = new ArrayList<>();
    }

    /**
     * Access database table.
     * @param tblName Table's name.
     * @return DBTable object corresponding.
     */
    public DBTable getTable(String tblName) {
        return tables.get(tables.indexOf(tblName));
    }

    /**
     * Add table to database.
     * @param table DBTable object.
     */
    public void addTable(DBTable table) {
        tables.add(table);
    }

    /**
     * Add new table to database.
     * @param tblName New table's name.
     */
    public void addTable(String tblName) {
        tables.add(new DBTable(tblName));
    }

    /*public int tableCount(String tblName) {
        return (int) DatabaseUtils.queryNumEntries(db, tblName);
    }*/

    /**
     * Create database table.
     * @param
     */
    public void createTable(DBTable table) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ").append(table.getTableName());
        sql.append(" (").append(table.tableToString()).append(");");
        db.execSQL(sql.toString());
    }

    /**
     * Delete table's content.
     * @param tblName Table's name.
     */
    public void deleteTable(String tblName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tblName).append(";");

        db.execSQL(sql.toString());
    }

    /**
     * Drop table. USE WITH CAUTION.
     * @param tblName Table's name.
     */
    public void dropTable(String tblName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DROP TABLE IF EXISTS ").append(tblName).append(";");

        db.execSQL(sql.toString());
    }

    /**
     * BuildContent createTables and rows
     */
    public void buildContent(){
        for (int i = 0 ; i < tables.size(); i++) {
            createTable(tables.get(i));
        }
    }
}
