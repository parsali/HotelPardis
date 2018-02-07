package ir.farabi.hotelpardis;

/**
 * Created by Parsa on 1/6/2017.
 */
public class constants {


    public static String DATABASE_NAME = "HotelDB";

    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME_room = "room";
    public static final String TABLE_NAME_roomSpecs="room_spec";
    public static final String TABLE_NAME_CUSTOMER="customer";
    public static final String TABLE_NAME_RESERVE = "reserve";
    public static final String PERSON_NAME = "name";
    public static final String PRICE = "price_room";
    public static final String CONTENT_NAME = "content";
    public static final String DATE_NAME = "recorddate";
    public static final String CART_NUMBER = "cartNumber";
    public static final String NUMBER_ROOM = "number_room";
    public static final String NUMBER_BED="number_bed";
    public static final String ROOM_TYPE = "room_type";
    public static final String VIEW = "room_view";
    public static final String CUSTOMER_ID="customer_id";
    public static final String CUSTOMER_NAME="customer_name";
    public static final String CUSTOMER_USERNAME="customer_username";
    public static final String CUSTOMER_PASSWORD="customer_password";
    public static final String CUSTOMER_CODE="customer_code";
    public static final String DATE_START="start_date";
    public static final String DATE_END="end_date";


    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static void setDatabaseName(String databaseName) {
        DATABASE_NAME = databaseName;
    }
}
