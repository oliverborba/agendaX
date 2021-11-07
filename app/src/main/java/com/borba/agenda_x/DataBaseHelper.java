package com.borba.agenda_x;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String banco_dados = "Contatos";
    private static int versao = 1;

    public DataBaseHelper(Context context) {

        super(context, banco_dados, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "CREATE TABLE AGENDA (ID INTEGER PRIMARY KEY," +
                        "NOME VARCHAR(30), " +
                        "EMPRESA VARCHAR(30)," +
                        "UF VARCHAR(2))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addAgenda(Agenda a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("NOME", a.getNome());
        values.put("EMPRESA", a.getEmpresa());
        values.put("UF", a.getUf());

        long id = db.insert("AGENDA", null, values);

        return id;
    }

    public long updateAgenda(Agenda a, int id_contato) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("NOME", a.getNome());
        values.put("EMPRESA", a.getEmpresa());
        values.put("UF", a.getUf());

        long id = db.update("AGENDA", values, "id = ?",
                new String[]{String.valueOf(id_contato)});

        return id;
    }

    public Agenda getAgenda(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Agenda agenda = new Agenda();

        Cursor cursor =
                db.rawQuery("SELECT * FROM AGENDA WHERE id = ?",
                        new String[]{String.valueOf(id)});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            agenda.setId(cursor.getInt(0));
            agenda.setNome(cursor.getString(1));
            agenda.setEmpresa(cursor.getString(2));
            agenda.setUf(cursor.getString(3));

        } else {
            agenda.setId(0);
            agenda.setNome("");
            agenda.setEmpresa("");
            agenda.setUf("");
        }

        return agenda;
    }
    public int deleteAgenda(){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete("AGENDA", "1", null);
        return i;
    }
}
