package com.example.lenovo.reminder.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lenovo.reminder.Common;
import com.example.lenovo.reminder.Fragments.ExpenseFragment;
import com.example.lenovo.reminder.Fragments.IncomeFragment;
import com.example.lenovo.reminder.Fragments.LoanFragment;

import com.example.lenovo.reminder.MainActivity;
import com.example.lenovo.reminder.model.IncomeModel;
import com.example.lenovo.reminder.model.LoanModel;
import com.example.lenovo.reminder.model.RecordModel;
import com.example.lenovo.reminder.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public SharedPreferences preferences;
    public String phoneNumber, selectedDate;


    public static final String USER_TABLE_NAME = "User";
    public static final String USER_COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_WALLET = "wallet";
    public static final String COLUMN_LIMIT = "user_limit";

    public static final String USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME
            + "("
            + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMAIL + " TEXT, " + COLUMN_NAME + " TEXT, "
            + COLUMN_PHONE + " TEXT UNIQUE , " + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_WALLET + " TEXT, " + COLUMN_LIMIT + " TEXT " + ");";


    public static final String TABLE_RECORD = "Record";
    public static final String RECORD_ID = "record_id";
    public static final String NAME = "itemname";
    public static final String DATE = "date";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String NUMBER = "number";
    public static final String ONLYDATE = "onlydate";
    public static final String ONLYMONTH = "onlymonth";
    public static final String ADDRESS = "address";
    public static final String PRICE = "rupee";


    public static final String TABLE = "CREATE TABLE " + TABLE_RECORD
            + "("
            + RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " TEXT, "
            + DATE + " TEXT, " + MONTH + " TEXT, " + YEAR + " TEXT, "
            + NUMBER + " TEXT, " + ONLYDATE + " TEXT, " + ONLYMONTH + " TEXT, "
            + PRICE + " INTEGER, " + ADDRESS + " TEXT " + ");";

    public static final String TABLE_LOAN = "LoanRecord";
    public static final String LOAN_ID = "loan_id";
    public static final String PERSON = "personname";
    public static final String LOAN_DATE = "LOAN_date";
    public static final String LOAN_MONTH = "LOAN_month";
    public static final String LOAN_YEAR = "LOAN_year";
    public static final String LOAN_NUMBER = "LOAN_number";
    public static final String RETURN_DATE = "returndate";
    public static final String AMOUNT = "amount";
    public static final String LOAN_TYPE = "loan_type";
    public static final String LOAN_ONLYDATE = "LOAN_onlydate";


    public static final String LOAN_TABLE = "CREATE TABLE " + TABLE_LOAN
            + "("
            + LOAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PERSON + " TEXT, "
            + LOAN_DATE + " TEXT, " + LOAN_MONTH + " TEXT, " + LOAN_YEAR + " TEXT, "
            + LOAN_NUMBER + " TEXT, "
            + AMOUNT + " INTEGER, " + RETURN_DATE + " TEXT, " + LOAN_TYPE + " TEXT, "
            + LOAN_ONLYDATE + " TEXT " + ");";

    public static final String TABLE_INCOME = "IncomeRecord";
    public static final String INCOME_ID = "income_id";
    public static final String INCOME_SOURCE = "income_source";
    public static final String INCOME_AMOUNT = "income_amount";
    public static final String INCOME_DATE = "income_date";
    public static final String INCOME_MONTH = "income_month";
    public static final String INCOME_YEAR = "income_year";
    public static final String INCOME_NUMBER = "income_number";
    public static final String INCOME_ONLYDATE = "income_onlydate";
    public static final String INCOME_ONLYMONTH = "income_onlymonth";

    public static final String INCOME_TABLE = "CREATE TABLE " + TABLE_INCOME
            + "("
            + INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + INCOME_SOURCE + " TEXT, "
            + INCOME_AMOUNT + " INTEGER, "
            + INCOME_DATE + " TEXT, " + INCOME_MONTH + " TEXT, " + INCOME_YEAR + " TEXT, "
            + INCOME_NUMBER + " TEXT, " + INCOME_ONLYDATE + " TEXT, " + INCOME_ONLYMONTH + " TEXT " + ");";


    Context context;

    private static final int DatabaseVersion = 1;
    private static final String DatabaseName = "MyRemainder.db";

    public DbHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        this.context = context;
        preferences = this.context.getSharedPreferences(Common.sharedPreferences, Context.MODE_PRIVATE);
        phoneNumber = preferences.getString("Number", null);
        selectedDate = preferences.getString("Date", null);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("Create table User(id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "email TEXT, name TEXT, phone TEXT, password TEXT)");

        db.execSQL(USER_TABLE);
        db.execSQL(TABLE);
        db.execSQL(LOAN_TABLE);
        db.execSQL(INCOME_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(" DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_RECORD);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LOAN);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_INCOME);
    }

    public long insertUserData(String email, String name, String password, String number) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", email);

        values.put("name", name);

        values.put("phone", number);

        values.put("password", password);

        values.put("wallet", "0");

        values.put("user_limit", "0");

        //insert row
        long id = db.insert(USER_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<UserModel> getalldata() {
        String query;

        query = String.format("SELECT * FROM User WHERE phone='%s'", phoneNumber);


        List<UserModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {
                UserModel model = new UserModel();
//                model.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                model.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                model.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                model.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public boolean getData(String number, String password) {
        // get readable database as we are not inserting anything

        SQLiteDatabase db = this.getReadableDatabase();
        String Query = String.format("SELECT * FROM User WHERE phone='%s' AND password='%s'", number, password);

        Cursor cursor = db.rawQuery(Query, null);


        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }

    }


    public long insertRecordData(String item, String rupee, String date, String month, String year, String onlydate, String onlymonth, String address) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, item);
        values.put(NUMBER, ExpenseFragment.userNumber);
        values.put(PRICE, Integer.valueOf(rupee));
        values.put(DATE, date);
        values.put(MONTH, month);
        values.put(YEAR, year);
        values.put(ONLYDATE, onlydate);
        values.put(ONLYMONTH, onlymonth);
        values.put(ADDRESS, address);


        //insert row
        long id = db.insert(TABLE_RECORD, null, values);
        db.close();
        return id;
    }

    public List<RecordModel> getallrecords() {

        String query = String.format("SELECT * FROM Record WHERE number='%s' ", ExpenseFragment.userNumber);


        List<RecordModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                RecordModel model = new RecordModel();
                model.setExp_id(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setPrice(String.valueOf(cursor.getInt(cursor.getColumnIndex(PRICE))));
                model.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(ONLYMONTH)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public RecordModel getalldatarec(long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM Record WHERE number='%s' ", phoneNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        RecordModel model = new RecordModel();
        cursor.getString(cursor.getColumnIndex(NAME));
        cursor.getString(cursor.getColumnIndex(PRICE));
        cursor.getString(cursor.getColumnIndex(ONLYMONTH));
        cursor.getString(cursor.getColumnIndex(ONLYDATE));
        cursor.getString(cursor.getColumnIndex(YEAR));
        cursor.getString(cursor.getColumnIndex(DATE));
        cursor.getString(cursor.getColumnIndex(MONTH));
        cursor.getString(cursor.getColumnIndex(NUMBER));
        cursor.getString(cursor.getColumnIndex(ADDRESS));


        cursor.close();
        return model;

    }

    public double totalsum() {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT SUM(rupee) FROM Record where number='%s'", ExpenseFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);
        }
        cursor.close();
        return grandprice;
    }

    public ArrayList<RecordModel> byDate(String date) {

        String query = String.format("Select * From Record Where date='%s' AND number='%s'", date, ExpenseFragment.userNumber);


        ArrayList<RecordModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            do {

                RecordModel model = new RecordModel();
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(ONLYMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                cursor.getString(cursor.getColumnIndex(NUMBER));
                model.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                model.setExp_id(cursor.getString(cursor.getColumnIndex(RECORD_ID)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public ArrayList<RecordModel> byMonth(String month) {

        String query = String.format("Select * From Record Where month='%s' AND number='%s'", month, ExpenseFragment.userNumber);


        ArrayList<RecordModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            do {

                RecordModel model = new RecordModel();
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(MONTH)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(ONLYMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                model.setExp_id(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public ArrayList<RecordModel> byYear(String year) {

        String query = String.format("Select * From Record Where year='%s' AND number='%s'", year, ExpenseFragment.userNumber);


        ArrayList<RecordModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            do {

                RecordModel model = new RecordModel();
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(ONLYMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                model.setExp_id(cursor.getString(cursor.getColumnIndex(RECORD_ID)));
                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public double totalsumofDate(String date) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where date='%s' AND number='%s'", date, ExpenseFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public double totalsumofMonth(String month) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where month='%s' AND number='%s'", month, ExpenseFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }

    public double totalsumofYear(String year) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (rupee) From Record Where year='%s' AND number='%s'", year, ExpenseFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        Log.e("cursor", cursor.toString());
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);
            Log.e("grandtotal", grandprice + "");

        }
        cursor.close();
        return grandprice;
    }

    public void deteteRcord(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete()

        String Query = String.format("DELETE FROM Record WHERE record_id='%s'", id );
        db.execSQL(Query);

    }


    public double dailysum(String date) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT SUM(rupee) FROM Record where number='%s' AND date='%s'", ExpenseFragment.userNumber, date);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);
        }
        cursor.close();
        return grandprice;
    }

    public long insertLoanData(String person, String amount, String number, String onlydate, String month, String year, String rDate, String type, String date) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e("RDATE", rDate);

        values.put(PERSON, person);
        values.put(AMOUNT, Integer.valueOf(amount));
        values.put(LOAN_NUMBER, number);
        values.put(LOAN_ONLYDATE, onlydate);
        values.put(LOAN_MONTH, month);
        values.put(LOAN_YEAR, year);
        values.put(RETURN_DATE, rDate);
        values.put(LOAN_TYPE, type);
        values.put(LOAN_DATE, date);

        //insert row
        long id = db.insert(TABLE_LOAN, null, values);
        Log.e("type",type);
        Log.e("onlydate",date);
        if (id > 0) {
            Log.e("Inserted", id + "");

        } else
            Log.e("NotInserted", id + "");


        db.close();
        return id;
    }

    public LoanModel getalldataLoan(long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM LoanRecord WHERE LOAN_number='%s'", LoanFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        LoanModel model = new LoanModel();
        cursor.getString(cursor.getColumnIndex(PERSON));
        cursor.getString(cursor.getColumnIndex(LOAN_DATE));
        cursor.getString(cursor.getColumnIndex(LOAN_MONTH));
        cursor.getString(cursor.getColumnIndex(LOAN_YEAR));
        cursor.getString(cursor.getColumnIndex(LOAN_NUMBER));
        cursor.getString(cursor.getColumnIndex(AMOUNT));
        cursor.getString(cursor.getColumnIndex(RETURN_DATE));
        cursor.getString(cursor.getColumnIndex(LOAN_TYPE));
        cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE));


        cursor.close();
        return model;

    }

    public List<LoanModel> getloanrecords() {

        String query = String.format("SELECT * FROM LoanRecord WHERE LOAN_number='%s'", LoanFragment.userNumber);


        List<LoanModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                LoanModel model = new LoanModel();
                model.setLoan_id(cursor.getString(cursor.getColumnIndex(LOAN_ID)));
                model.setPerson(cursor.getString(cursor.getColumnIndex(PERSON)));
                model.setDate(cursor.getString(cursor.getColumnIndex(LOAN_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(LOAN_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(LOAN_YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(LOAN_NUMBER)));
                model.setAmount(String.valueOf(cursor.getInt(cursor.getColumnIndex(AMOUNT))));
                model.setReturnDate(cursor.getString(cursor.getColumnIndex(RETURN_DATE)));
                model.setType(cursor.getString(cursor.getColumnIndex(LOAN_TYPE)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE)));

                models.add(model);

            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public void deteteLoanRcord(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete()

        String Query = String.format("DELETE FROM LoanRecord WHERE loan_id='%s'", id);
        db.execSQL(Query);

    }


    public List<LoanModel> getreturnDate(String today_date) {

        String query = String.format("SELECT * FROM LoanRecord WHERE LOAN_number='%s' and returndate='%s'", MainActivity.userNum, today_date);

        List<LoanModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.e("Cursor_count", cursor.getCount() + "");

        if (cursor.moveToFirst())
            do {

                Log.e("Loanid", String.valueOf(cursor.getInt(cursor.getColumnIndex(LOAN_ID))));
                LoanModel model = new LoanModel();
                model.setLoan_id(cursor.getString(cursor.getColumnIndex(LOAN_ID)));
                model.setPerson(cursor.getString(cursor.getColumnIndex(PERSON)));
                model.setDate(cursor.getString(cursor.getColumnIndex(LOAN_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(LOAN_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(LOAN_YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(LOAN_NUMBER)));
                model.setAmount(String.valueOf(cursor.getInt(cursor.getColumnIndex(AMOUNT))));
                model.setReturnDate(cursor.getString(cursor.getColumnIndex(RETURN_DATE)));
                model.setType(cursor.getString(cursor.getColumnIndex(LOAN_TYPE)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE)));

                models.add(model);

            } while (cursor.moveToNext());


        Log.e("size", models.size() + "");
        db.close();
        cursor.close();
        return models;
    }

//    public static final String TABLE_INCOME = "IncomeRecord";
//    public static final String INCOME_ID = "income_id";
//    public static final String INCOME_SOURCE = "income_source";
//    public static final String INCOME_AMOUNT = "income_amount";
//    public static final String INCOME_DATE = "income_date";
//    public static final String INCOME_MONTH = "income_month";
//    public static final String INCOME_YEAR = "income_year";
//    public static final String INCOME_NUMBER = "income_number";


    public long insertIncomeData(String source, String amount, String date, String month, String year, String number, String onlydate, String onlymonth) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INCOME_SOURCE, source);
        values.put(INCOME_AMOUNT, Integer.valueOf(amount));
        values.put(INCOME_DATE, date);
        values.put(INCOME_MONTH, month);
        values.put(INCOME_YEAR, year);
        values.put(INCOME_NUMBER, number);
        values.put(INCOME_ONLYDATE, onlydate);
        values.put(INCOME_ONLYMONTH, onlymonth);


        //insert row
        long id = db.insert(TABLE_INCOME, null, values);
        Log.e("Insert", id + "");
        db.close();
        return id;
    }

    public List<IncomeModel> getIncomerecords() {

        String query = String.format("SELECT * FROM IncomeRecord WHERE income_number='%s'", IncomeFragment.userNumber);


        List<IncomeModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.e("IncomeCursor", cursor.getCount() + "");
        if (cursor.moveToFirst())
            do {

                IncomeModel model = new IncomeModel();
                model.setIncome_id(cursor.getString(cursor.getColumnIndex(INCOME_ID)));
                model.setSource(cursor.getString(cursor.getColumnIndex(INCOME_SOURCE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(INCOME_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(INCOME_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(INCOME_YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(INCOME_NUMBER)));
                model.setAmount(String.valueOf(cursor.getInt(cursor.getColumnIndex(INCOME_AMOUNT))));

                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(INCOME_ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(INCOME_ONLYMONTH)));
                models.add(model);

            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;

    }

    public IncomeModel getallincomrec(long id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM IncomeRecord WHERE income_number='%s' ", phoneNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();

        IncomeModel model = new IncomeModel();
        cursor.getString(cursor.getColumnIndex(INCOME_SOURCE));
        cursor.getString(cursor.getColumnIndex(INCOME_DATE));
//        cursor.getString(cursor.getColumnIndex(INCOME_MONTH));
//        cursor.getString(cursor.getColumnIndex(INCOME_YEAR));
        cursor.getString(cursor.getColumnIndex(INCOME_NUMBER));
        cursor.getString(cursor.getColumnIndex(INCOME_AMOUNT));

        cursor.close();
        return model;

    }

    public double totalincomesum() {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("SELECT SUM(income_amount) FROM IncomeRecord where income_number='%s'", IncomeFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);
        }
        cursor.close();
        return grandprice;
    }

    public void deteteIncomeRcord(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String Query = String.format("DELETE FROM IncomeRecord WHERE income_id='%s'", id);
        db.execSQL(Query);

    }

    public ArrayList<IncomeModel> incomebyMonth(String month) {

        String query = String.format("Select * From IncomeRecord Where income_month='%s' AND income_number='%s'", month, IncomeFragment.userNumber);


        ArrayList<IncomeModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            do {

                IncomeModel model = new IncomeModel();
                model.setSource(cursor.getString(cursor.getColumnIndex(INCOME_SOURCE)));
                model.setAmount(cursor.getString(cursor.getColumnIndex(INCOME_AMOUNT)));
                model.setDate(cursor.getString(cursor.getColumnIndex(INCOME_DATE)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(INCOME_ONLYMONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(INCOME_YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(INCOME_NUMBER)));
                model.setIncome_id(cursor.getString(cursor.getColumnIndex(INCOME_ID)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public double totalincomeofMonth(String month) {
        double grandprice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("Select SUM (income_amount) From IncomeRecord Where income_month='%s' AND income_number='%s'", month, IncomeFragment.userNumber);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            cursor.moveToFirst();
            grandprice = cursor.getDouble(0);

        }
        cursor.close();
        return grandprice;
    }
    public List<RecordModel> getdailyrecords(String date) {

        String query = String.format("SELECT * FROM Record WHERE number='%s' AND date='%s'", ExpenseFragment.userNumber, date);


        List<RecordModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            do {

                RecordModel model = new RecordModel();
                model.setItem(cursor.getString(cursor.getColumnIndex(NAME)));
                model.setPrice(String.valueOf(cursor.getInt(cursor.getColumnIndex(PRICE))));
                model.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(ONLYDATE)));
                model.setOnlyMonth(cursor.getString(cursor.getColumnIndex(ONLYMONTH)));
                model.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                model.setExp_id(cursor.getString(cursor.getColumnIndex(RECORD_ID)));

                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }


    public List<LoanModel> getReturnLoanrecords(String date) {

        String query = String.format("SELECT * FROM LoanRecord WHERE LOAN_number='%s' AND LOAN_date='%s'", MainActivity.userNum, date);


        List<LoanModel> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Log.e("insertedDate",date);
        if (cursor.moveToFirst())
            do {

                LoanModel model = new LoanModel();
                model.setLoan_id(cursor.getString(cursor.getColumnIndex(LOAN_ID)));
                model.setPerson(cursor.getString(cursor.getColumnIndex(PERSON)));
                model.setDate(cursor.getString(cursor.getColumnIndex(LOAN_DATE)));
                model.setMonth(cursor.getString(cursor.getColumnIndex(LOAN_MONTH)));
                model.setYear(cursor.getString(cursor.getColumnIndex(LOAN_YEAR)));
                model.setNumber(cursor.getString(cursor.getColumnIndex(LOAN_NUMBER)));
                model.setAmount(String.valueOf(cursor.getInt(cursor.getColumnIndex(AMOUNT))));
                model.setReturnDate(cursor.getString(cursor.getColumnIndex(RETURN_DATE)));
                model.setType(cursor.getString(cursor.getColumnIndex(LOAN_TYPE)));
                model.setOnlyDate(cursor.getString(cursor.getColumnIndex(LOAN_ONLYDATE)));
                models.add(model);


            } while (cursor.moveToNext());


        db.close();
        cursor.close();
        return models;
    }

    public void updateWallet(String newAmount){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE User SET wallet='%s' WHERE phone='%s'",newAmount, phoneNumber);
        db.execSQL(query);


    }

    public UserModel getUserWallet(String userNum){

        SQLiteDatabase db = this.getReadableDatabase();

        String query = String.format("Select wallet, user_limit from User where phone='%s'",userNum);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();
        UserModel user = new UserModel(
                cursor.getString(cursor.getColumnIndex(COLUMN_WALLET)),
                cursor.getString(cursor.getColumnIndex(COLUMN_LIMIT)));
        // close the db connection
        cursor.close();
        return user;
    }

    public void updateLimit(String newAmount){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE User SET user_limit='%s' WHERE phone='%s'",newAmount, phoneNumber);
        db.execSQL(query);


    }
}
