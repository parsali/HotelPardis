package ir.farabi.hotelpardis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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
        String CREATE_ROOMSPEC_TABLE = "CREATE TABLE"+
                constants.TABLE_NAME_roomSpecs+
                "("+constants.NUMBER_ROOM + " varchar(3), "
                 + constants.NUMBER_BED +
                " char(1)," +constants.ROOM_TYPE+
                "char(1)"+constants.VIEW +" char(1)"
                + " FOREIGN KEY ("+constants.NUMBER_ROOM+") REFERENCES "+constants.TABLE_NAME_room+"("+constants.NUMBER_ROOM+"));";;
        String CREATE_CUSTOMER_TABLE="CREATE TABLE "+constants.TABLE_NAME_CUSTOMER +"("
                + constants.CUSTOMER_ID + " varchar(10) PRIMARY KEY, "
                + constants.CUSTOMER_NAME + " nvarchar(50),"
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
    public void update(ContentValues contentValues,int id){
     //   SQLiteDatabase db = this.getWritableDatabase();
    //    db.update(constants.TABLE_NAME,contentValues,"_id="+id,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + constants.TABLE_NAME);
    //    db.execSQL("ALTER TABLE " + constants.TABLE_NAME + " ADD COLUMN cartNumber TEXT");
              //  Log.v("ONUPGRADE", "DROPING THE TABLE AND CREATING A NEW ONE!");

        //create a new one
        //onCreate(db);

    }





}
