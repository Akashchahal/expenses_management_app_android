package com.example.expensesmanagementproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_app_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EXPENSES = "tbl_expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATED = "dated";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS tbl_expenses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "amount INTEGER, " +
                "dated DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public long addExpense(String name, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_AMOUNT, amount);
        long newRowId = db.insert(TABLE_EXPENSES, null, values);
        db.close();
        return newRowId;
    }

    public ArrayList<ExpenseModel> getAllExpenses() {
        ArrayList<ExpenseModel> expenseList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EXPENSES;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
                String dated = cursor.getString(cursor.getColumnIndex(COLUMN_DATED));
                expenseList.add(new ExpenseModel(id, name, amount, dated));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenseList;
    }

    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSES+" where id="+id);
        onCreate(db);
    }

    public void updateExpense(int id, String name, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE " + TABLE_EXPENSES + " SET " +
                COLUMN_NAME + "='" + name + "', " +
                COLUMN_AMOUNT + "=" + amount +
                " WHERE " + COLUMN_ID + "=" + id;
        db.execSQL(updateQuery);
        db.close();
    }

    public ArrayList<ExpenseModel> getExpensesByName(String name){
        ArrayList<ExpenseModel> expenseList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery="SELECT * FROM " + TABLE_EXPENSES + " WHERE " +COLUMN_NAME +"= ? ";
        Cursor cursor=db.rawQuery(selectQuery,new String[]{name});

        if(cursor.moveToFirst()) {
            do{
                int id= cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int amount= cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String dated=cursor.getString(cursor.getColumnIndex(COLUMN_DATED));
                expenseList.add(new ExpenseModel(id,name,amount,dated));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenseList;
    }
    public List<String> getAllExpenseNames(){
        List<String> expenseNames=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
        String selectQuery="SELECT DISTINCT " + COLUMN_NAME + " FROM " + TABLE_EXPENSES;
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                expenseNames.add(name);
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return expenseNames;

    }

}
