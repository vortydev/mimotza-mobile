package com.example.mimotza;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Custom SQLite database wrapper.
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

    /**
     * Executes raw SQL commands. USE AT YOUR OWN RISK
     * @param sql SQL expression.
     */
    /*public void rawSQL(String sql) {
        db.execSQL(sql);
    }*/

    public void insertPartie(Integer idUser,Integer win,Integer score,String temps,String dateEmission,Integer idMot){
        db.execSQL("INSERT INTO partie(idUser,win,score,temps,dateEmission,idMot) " +
                " values("+idUser.toString()+","+win.toString()+","+score.toString()+","+temps+","+dateEmission+","+idMot.toString()+")");
    }

    /**
     * ajoute un utilisateur à la base de donnée locale
     * @author Alberto et Isabelle Rioux
     * @param pidOrigin id dans la base de donnée serveur
     * @param pusername username à mettre dans la base de donnée locale
     * @param pnom nom à mettre dans la base de donnée locale
     * @param pprenom prenom à mettre dans la base de donnée locale
     */
    public void insertUser(Integer pidOrigin,String pusername,String pnom,String pprenom){
        try{
            Cursor c = db.rawQuery("SELECT * FROM utilisateur WHERE idOrigin = "+pidOrigin.toString(),null);
            if(c.getCount() <= 0){
                c.close();
                db.execSQL("INSERT INTO utilisateur (idOrigin,prenom,nom,username,statut) " +
                        "VALUES ("+pidOrigin.toString()+",'"+pprenom+"','"+pnom+"','"+pusername+"',2)");
            }else{
                c.close();
                //modifier statut pour actif
                db.execSQL("UPDATE utilisateur SET statut = 2 WHERE idOrigin ="+pidOrigin.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * met un utilisateur qui se déconnecte inactif
     * @author Isabelle Rioux
     * @return id de l'user pour la base de donnée serveur
     */
    public Integer disconnectUser(){
        Integer pidOrigin = -1;
        try{
            Cursor c = db.rawQuery("SELECT * FROM utilisateur WHERE statut = 2",null);
            if(c.getCount() <= 0){
                c.close();
            }else {
                if (c.moveToFirst()){
                    int index = c.getColumnIndex("idOrigin");
                    pidOrigin = c.getInt(index);
                }
                c.close();
                db.execSQL("UPDATE utilisateur SET statut = 1 WHERE statut = 2");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return pidOrigin;
    }

    /**
     * vérifie si un utilisateur actif est dans la bd
     * @author Isabelle Rioux
     * @return si l'utilisateur est connecté
     */
    public boolean checkUserConnected(){
        try{
            Cursor c = db.rawQuery("SELECT * FROM utilisateur WHERE statut = 2",null);
            if(c.getCount() <= 0){
                c.close();
                return false;
            }}
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * envoie l'id d'un utilisateur, soir pour l'envoi de données à la base de donnée serveur
     * @author Isabelle Rioux
     * @return l'id d'un utilisateur connecté
     */
    public Integer fetchUserId(){
        Integer pidOrigin = -1;
        try{
            Cursor c = db.rawQuery("SELECT * FROM utilisateur WHERE statut = 2",null);
            if (c.getCount() <= 0){
                c.close();
            }else {
                if (c.moveToFirst()) {
                    int index = c.getColumnIndex("idOrigin");
                    pidOrigin = c.getInt(index);
                }
                c.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return pidOrigin;
    }
}
