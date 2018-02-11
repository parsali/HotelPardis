package ir.farabi.hotelpardis;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class moreInfo extends AppCompatActivity {
    ActionBar ab;
    Button vurud;
    Button khuruj;
    TextView day;
    TextView date;
    TextView khuruj_day;
    TextView khuruj_date;
    TextView info_text;
    ImageView backdrop;
    TextView TextVurud;
    TextView TextKHuruj;
    TextView hazine;
    databaseHandler databaseHandler;
    String startDate;
    String endDate;
    int type;
    SessionManager session;
    Date START_DATE;
    Date END_DATE;
    Date NOW_DATE;
    boolean START_DATE_SET=false;
    boolean END_DATE_SET=false;
    PersianCalendar now;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myBestoolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        databaseHandler=new databaseHandler(getApplicationContext());
        hazine = (TextView)findViewById(R.id.hazine);
        type= bundle.getInt("type");
        vurud = (Button) findViewById(R.id.vurud);
        khuruj = (Button) findViewById(R.id.khuruj);
        day=(TextView)findViewById(R.id.vurud_day);
        date=(TextView)findViewById(R.id.vurud_date);
        khuruj_day=(TextView)findViewById(R.id.khuruj_day);
        khuruj_date=(TextView)findViewById(R.id.khuruj_date);
        backdrop = (ImageView)findViewById(R.id.backdrop);
        TextVurud = (TextView)findViewById(R.id.TextVurud);
        TextKHuruj = (TextView)findViewById(R.id.TextKhuruj);
        info_text=(TextView)findViewById(R.id.info_text);
        session = new SessionManager(getApplicationContext());
        now = new PersianCalendar();

        if(type==1){
            backdrop.setImageResource(R.drawable.luxury);
            info_text.setText("لوکس");

        }
        if(type==2){
            backdrop.setImageResource(R.drawable.special);
            info_text.setText("ویژه");
        }
        if(type==3){
            backdrop.setImageResource(R.drawable.economy);
            info_text.setText("اقتصادی");
        }

        hazine.setText(databaseHandler.getRoomPrice(String.valueOf(type))+" تومان ");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClickVurud(View v) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                             @Override
                                                                             public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                 Roozh jCal= new Roozh();
                                                                                 jCal.PersianToGregorian(year, monthOfYear+1, dayOfMonth);
                                                                                 startDate=jCal.toString();
                                                                                 jCal.PersianToGregorian(now.getPersianYear(),now.getPersianMonth()+1,now.getPersianDay());
                                                                                 String nowDate = jCal.toString();
                                                                                 try {
                                                                                     SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
                                                                                     START_DATE = SDF.parse(startDate);
                                                                                     NOW_DATE = SDF.parse(nowDate);

                                                                                 } catch (ParseException e) {
                                                                                     e.printStackTrace();
                                                                                 }
                                                                                 Log.d("app123",startDate+"--"+NOW_DATE.toString());
                                                                                 if (START_DATE.after(NOW_DATE)) {
                                                                                     Toast.makeText(getApplicationContext(), "" + year + "/" + (monthOfYear+1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                                                                     PersianCalendar selected = new PersianCalendar();
                                                                                     selected.setPersianDate(year, monthOfYear, dayOfMonth);
                                                                                     day.setText(selected.getPersianWeekDayName());
                                                                                     date.setText(dayOfMonth + " " + getMounth(monthOfYear + 1));
                                                                                     day.setVisibility(View.VISIBLE);
                                                                                     date.setVisibility(View.VISIBLE);
                                                                                     vurud.setVisibility(View.INVISIBLE);
                                                                                     TextVurud.setVisibility(View.VISIBLE);
                                                                                     START_DATE_SET = true;
                                                                                 } else
                                                                                     Toast.makeText(getApplicationContext(), "امکان رزرو اتاق تنها برای تاریخی پس از فردا امکان پذیر است", Toast.LENGTH_LONG).show();
                                                                                 }
                                                                         }, now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay());



        datePickerDialog.show(getFragmentManager(), "tpd");
    }
    public void onClickKhuruj(View v) {
        if(START_DATE_SET) {
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                                 @Override
                                                                                 public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                     Roozh jCal = new Roozh();
                                                                                     jCal.PersianToGregorian(year, monthOfYear + 1, dayOfMonth);
                                                                                     endDate = jCal.toString();
                                                                                     try {
                                                                                         SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
                                                                                         END_DATE = SDF.parse(endDate);
                                                                                     } catch (ParseException e) {
                                                                                         e.printStackTrace();
                                                                                     }
                                                                                     if (END_DATE.after(START_DATE) || startDate.equals(endDate)) {
                                                                                         Toast.makeText(getApplicationContext(), "" + year + "/" + (monthOfYear + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                                                                         PersianCalendar selected = new PersianCalendar();
                                                                                         selected.setPersianDate(year, monthOfYear, dayOfMonth);
                                                                                         khuruj_day.setText(selected.getPersianWeekDayName());
                                                                                         khuruj_date.setText(dayOfMonth + " " + getMounth(monthOfYear + 1));
                                                                                         khuruj_day.setVisibility(View.VISIBLE);
                                                                                         khuruj_date.setVisibility(View.VISIBLE);
                                                                                         khuruj.setVisibility(View.INVISIBLE);
                                                                                         TextKHuruj.setVisibility(View.VISIBLE);
                                                                                         END_DATE_SET = true;

                                                                                     } else
                                                                                         Toast.makeText(getApplicationContext(), "تاریخ خروج باید بعد از ورود باشد", Toast.LENGTH_SHORT).show();
                                                                                 }
                                                                             }, now.getPersianYear(),
                    now.getPersianMonth(),
                    now.getPersianDay());


            datePickerDialog.show(getFragmentManager(), "tpd");
        }
        else{
            Toast.makeText(getApplicationContext(),"ابتدا تاریخ ورود را وارد نمایید", Toast.LENGTH_SHORT).show();
        }
    }
    public String getMounth(int mounth) {
        switch (mounth) {
            case 1:
                return "فرودین";
            case 2:
                return"اردیبهشت";
            case 3:
                return"خرداد";
            case 4:
                return"تیر";
            case 5:
                return"مرداد";
            case 6:
                return "شهریور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آذر";
            case 10:
                return "دی";
            case 11:
                return "بهمن";
            case 12:
                return "اسفند";
            default:
                return "";
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "moreInfo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ir.farabi.hotelpardis/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "moreInfo Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ir.farabi.hotelpardis/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
    public void reserve(View v){
        if(START_DATE_SET&&END_DATE_SET){
            try {
                String room = databaseHandler.getAvailableRooms(String.valueOf(type),"1", startDate, endDate).get(0);
                HashMap<String, String> userHash = session.getUserDetails();
                databaseHandler.reserve(room, userHash.get(SessionManager.USER_ID), startDate, endDate);
                Toast.makeText(this, "اتاق شما روز گردید", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){
                Toast.makeText(this,"متاسفانه با مشخصات وارد شده شما در تاریخ مذکور اتاق خالی نداریم",Toast.LENGTH_LONG).show();
            }
        }
        else Toast.makeText(this,"لطفا تاریخ ورود و خروج را وارد نمایید", Toast.LENGTH_SHORT).show();
    }
}
