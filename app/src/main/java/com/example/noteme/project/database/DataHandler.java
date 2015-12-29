package com.example.noteme.project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataHandler {

    SQLiteDatabase sqLiteDatabase;

    public static final String CONTACTS_TABLE_NAME = "contactTable";
    public static final String DATA_BASE_NAME = "myDataBase";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE = "CREATE TABLE contactTable(contactID TEXT, filePath TEXT, name TEXT, mail TEXT, number TEXT, description TEXT);";
    public static final String FROM_COLUMN_ID = "contactID = ?";


    public DataBaseHelper dbHelper;

    public DataHandler(Context context) {

        dbHelper = new DataBaseHelper(context.getApplicationContext());

    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            db.execSQL("DROP TABLE IF EXIST " + CONTACTS_TABLE_NAME);
            onCreate(db);

        }
    }

    public DataHandler open() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();

    }

    public void saveContact (Contact contact) {
        ContentValues content = new ContentValues();
        content.put("contactID", Long.toString(System.currentTimeMillis()));
        content.put("filePath", contact.getFilePath());
        content.put("name", contact.getName());
        content.put("mail", contact.getEmail());
        content.put("number", contact.getNumber());
        content.put("description", contact.getDescription());
        sqLiteDatabase.insert(CONTACTS_TABLE_NAME, null, content);

    }

    public void removeContact(String contactId) {
        String whereClause = FROM_COLUMN_ID;
        String[] whereArgs = new String[]{contactId};
        sqLiteDatabase.delete("contactTable", whereClause, whereArgs);
    }

    public Contact getMain(String contactId) {
        String whereClause = FROM_COLUMN_ID;
        String[] whereArgs = new String[]{contactId};
        Cursor c = sqLiteDatabase.query(CONTACTS_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        c.moveToFirst();
        return getContact(c);
    }


    public ArrayList<Contact> getContacts() {
        Cursor cursor = sqLiteDatabase.query(CONTACTS_TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Contact> myContact = new ArrayList<>();
        while (cursor.moveToNext()) {
            myContact.add(getContact(cursor));
        }
        cursor.close();
        return myContact;
    }

    Contact getContact(Cursor cursor) {
        String contactID = cursor.getString((cursor.getColumnIndex("contactID")));
        String filePath = cursor.getString((cursor.getColumnIndex("filePath")));
        String name = cursor.getString((cursor.getColumnIndex("name")));
        String mail = cursor.getString((cursor.getColumnIndex("mail")));
        String number = cursor.getString((cursor.getColumnIndex("number")));
        String description = cursor.getString((cursor.getColumnIndex("description")));
        Contact contact = new Contact(contactID, filePath, name, mail, number, description);
        return contact;
    }

}



