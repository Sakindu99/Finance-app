package com.example.finalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Finance.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_income";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOTE = "income_note";
    private static final String COLUMN_AMOUNT = "income_amount";
    private static final String COLUMN_CATEGORY = "income_category";


    private static final String TABLE_REM_NAME = "my_Reminder";
    private static final String COLUMN_REM_ID = "_id";
    private static final String COLUMN_TYPE = "Reminder_type";
    private static final String COLUMN_REM_AMOUNT = "Reminder_amount";
    private static final String COLUMN_DATE = "Reminder_due_date";


    private static final String TABLE_GOAL_NAME = "my_goal";
    private static final String COLUMN_GOAL_ID = "_id";
    private static final String COLUMN_GOAL_NAME = "goal_name";
    private static final String COLUMN_GOAL_AMOUNT = "goal_amount";
    private static final String COLUMN_GOAL_DESCRIPTION = "goal_description";

    private static final String TABLE_EXPENSE_NAME = "expense";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE my_income (_id INTEGER PRIMARY KEY AUTOINCREMENT, income_note TEXT, income_amount REAL , income_category TEXT)";
        db.execSQL(query);

        String queryRem = "CREATE TABLE my_Reminder (_id INTEGER PRIMARY KEY AUTOINCREMENT, Reminder_type TEXT, Reminder_amount REAL , Reminder_due_date TEXT)";
        db.execSQL(queryRem);

        String queryGoal = "CREATE TABLE my_goal (_id INTEGER PRIMARY KEY AUTOINCREMENT, goal_name TEXT, goal_amount REAL , goal_description TEXT)";
        db.execSQL(queryGoal);

        String queryExpense = "CREATE TABLE expense(_id INTEGER primary key, expense_note TEXT, expense_amount REAL, payment_method TEXT, expense_category TEXT)";
        db.execSQL(queryExpense);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REM_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE_NAME);
        onCreate(db);
    }

    public Boolean addIncome(String note, String amount, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("income_note", note);
        cv.put("income_amount", amount);
        cv.put("income_category", category);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean addReminder(String Rtype, String amount, String Rdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Reminder_type", Rtype);
        cv.put("Reminder_amount", amount);
        cv.put("Reminder_due_date", Rdate);
        long result = db.insert(TABLE_REM_NAME, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean addGoal(String goalName, String goalAmount, String goalDes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("goal_name", goalName);
        cv.put("goal_amount", goalAmount);
        cv.put("goal_description", goalDes);
        long result = db.insert(TABLE_GOAL_NAME, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertData(String note, String amount, String payMethod, String category) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("expense_note", note);
        cv.put("expense_amount", amount);
        cv.put("payment_method", payMethod);
        cv.put("expense_category", category);

        long ins = myDB.insert("expense", null, cv);
        if (ins == -1) return false;
        else return true;


    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    Cursor readAllDataRem(){
        String query = "SELECT * FROM " + TABLE_REM_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    Cursor readAllDataGoal(){
        String query = "SELECT * FROM " + TABLE_GOAL_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    public Cursor readAllDataExpense() {
        String query = "SELECT * FROM " + TABLE_EXPENSE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        }
        return cursor;

    }

   void updateData(String row_id, String note, String amount, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("income_note", note);
        cv.put("income_amount", amount);
        cv.put("income_category", category);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDataRem(String row_id, String Rtype, String amount, String Rdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Reminder_type", Rtype);
        cv.put("Reminder_amount", amount);
        cv.put("Reminder_due_date", Rdate);

        long result = db.update(TABLE_REM_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDataGoal(String row_id, String name, String amount, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("goal_name", name);
        cv.put("goal_amount", amount);
        cv.put("goal_description", description);

        long result = db.update(TABLE_GOAL_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDataExpense(String row_id, String note, String amount, String payMethod, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("expense_note", note);
        cv.put("expense_amount", amount);
        cv.put("payment_method", payMethod);
        cv.put("expense_category", category);

        long result = db.update(TABLE_EXPENSE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    void deleteOneRowRem(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_REM_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllDataRem(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_REM_NAME);
    }

    void deleteOneRowGoal(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_GOAL_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllDataGoal(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_GOAL_NAME);
    }

    public void deleteOneRowExpense(String recordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_EXPENSE_NAME, "_id=?", new String[]{recordId});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllDataExpense(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSE_NAME);
    }



/*
    public List<Income> getIncome(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder gb = new SQLiteQueryBuilder();

        //make sure all is column name in your table
        String[] sqlSelect ={"_id", "income_note", "income_amount", "income_category"};
        String tableName ="my_income";

        gb.setTables(tableName);
        //This will like query : select * from my_income where income_note LIKE %pattern%"
        Cursor cursor = gb.query(db,sqlSelect, "income_category ?",new String[]{"%+category%"}, null, null, null, null);
        List<Income> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do{
                Income income = new Income();
                income.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                income.setNote(cursor.getString(cursor.getColumnIndex("income_note")));
                income.setAmount(cursor.getString(cursor.getColumnIndex("income_amount")));
                income.setCategory(cursor.getString(cursor.getColumnIndex("income_category")));

                result.add(income);
            }while (cursor.moveToNext());
        }
        return result;
    }*/
}




