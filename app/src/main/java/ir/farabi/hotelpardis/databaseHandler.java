package ir.farabi.hotelpardis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.MatrixCursor;
import java.util.ArrayList;
import ir.farabi.hotelpardis.User;


/**
 * Created by Parsa on 1/6/2017.
 */

public class databaseHandler extends SQLiteOpenHelper {

    public databaseHandler(Context context) {
        super(context, constants.DATABASE_NAME, null, constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROOM_TABLE = "CREATE TABLE " + constants.TABLE_NAME_room + "("
                + constants.NUMBER_ROOM + " varchar(3) PRIMARY KEY, " + constants.PERSON_NAME +
                " TEXT, " + constants.PRICE + " TEXT );";
        String CREATE_ROOMSPEC_TABLE = "CREATE TABLE "+
                constants.TABLE_NAME_roomSpecs+
                "("+constants.NUMBER_ROOM + " varchar(3), "
                 + constants.NUMBER_BED +
                " char(1)," +constants.ROOM_TYPE+
                " char(1),"+constants.VIEW +" char(1),"
                + " FOREIGN KEY ("+constants.NUMBER_ROOM+") REFERENCES "+constants.TABLE_NAME_room+"("+constants.NUMBER_ROOM+"));";;
        String CREATE_CUSTOMER_TABLE="CREATE TABLE "+constants.TABLE_NAME_CUSTOMER +"("
                + constants.CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + constants.CUSTOMER_NAME + " nvarchar(50),"
                + constants.CUSTOMER_USERNAME + " varchar(50),"
                + constants.CUSTOMER_PASSWORD + " varchar(50),"
                + constants.CUSTOMER_CODE + " varchar(10));";
        String CREATE_RESERVE_TABLE="CREATE TABLE "
                + constants.TABLE_NAME_RESERVE+"("
                + constants.NUMBER_ROOM + " varchar(3),"
                + constants.CUSTOMER_ID + " varchar(10),"
                + constants.DATE_START + " DATE"
                + constants.DATE_END + " DATE,"
                + " FOREIGN KEY ("+constants.NUMBER_ROOM+") REFERENCES "+constants.TABLE_NAME_room+"("+constants.NUMBER_ROOM+")"
                + " FOREIGN KEY ("+constants.CUSTOMER_ID+") REFERENCES "+constants.TABLE_NAME_CUSTOMER+"("+constants.CUSTOMER_ID+"));";;
        db.execSQL(CREATE_ROOM_TABLE);
        db.execSQL(CREATE_ROOMSPEC_TABLE);
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_RESERVE_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_RESERVE);
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_roomSpecs);
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_room);
        onCreate(db);
    }

    public void initial() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO customer (customer_name,customer_username,customer_password,customer_code) " +
                "VALUES ('admin','admin','admin','1234')";
        db.execSQL(sql);
        db.close();
    }

    public void recreate() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_RESERVE);
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_roomSpecs);
        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME_room);
        onCreate(db);
        initial();
        db.close();
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(constants.CUSTOMER_NAME, user.getName());
        values.put(constants.CUSTOMER_USERNAME, user.getUsername());
        values.put(constants.CUSTOMER_PASSWORD, user.getPassword());
        values.put(constants.CUSTOMER_CODE, user.getCode());
        db.insert(constants.TABLE_NAME_CUSTOMER, null, values);
        db.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(constants.CUSTOMER_NAME, user.getName());
        values.put(constants.CUSTOMER_USERNAME, user.getUsername());
        values.put(constants.CUSTOMER_PASSWORD, user.getPassword());
        values.put(constants.CUSTOMER_CODE, user.getCode());
        db.update(constants.TABLE_NAME_CUSTOMER, values, constants.CUSTOMER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(constants.TABLE_NAME_CUSTOMER, constants.CUSTOMER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String username) {
        String[] columns = {
                constants.CUSTOMER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = constants.CUSTOMER_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(constants.TABLE_NAME_CUSTOMER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) return true;
        return false;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {
                constants.CUSTOMER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = constants.CUSTOMER_USERNAME + " = ?" + " AND " + constants.CUSTOMER_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(constants.TABLE_NAME_CUSTOMER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) return true;
        return false;
    }

    public User getUser(String username){
        User user = new User();
        String[] columns = {
                constants.CUSTOMER_ID,
                constants.CUSTOMER_NAME,
                constants.CUSTOMER_USERNAME,
                constants.CUSTOMER_PASSWORD,
                constants.CUSTOMER_CODE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = constants.CUSTOMER_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(constants.TABLE_NAME_CUSTOMER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);
        if (cursor.moveToFirst()) {
            do {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(constants.CUSTOMER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(constants.CUSTOMER_NAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(constants.CUSTOMER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(constants.CUSTOMER_PASSWORD)));
                user.setCode(cursor.getString(cursor.getColumnIndex(constants.CUSTOMER_CODE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return user;
    }



}
