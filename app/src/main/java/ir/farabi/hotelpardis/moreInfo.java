package ir.farabi.hotelpardis;


import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.Date;

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
    int type;

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

        type= bundle.getInt("type");
        vurud = (Button) findViewById(R.id.vurud);
        khuruj = (Button) findViewById(R.id.khuruj);
        day=(TextView)findViewById(R.id.vurud_day);
        date=(TextView)findViewById(R.id.vurud_date);
        khuruj_day=(TextView)findViewById(R.id.khuruj_day);
        khuruj_date=(TextView)findViewById(R.id.khuruj_date);
        backdrop = (ImageView)findViewById(R.id.backdrop);
        info_text=(TextView)findViewById(R.id.info_text);
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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClickVurud(View v) {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                             @Override
                                                                             public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                 Toast.makeText(getApplicationContext(), "" + year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                                                                 day.setText(String.valueOf(dayOfMonth));
                                                                                 date.setText(monthOfYear+","+year);

                                                                                 day.setVisibility(View.VISIBLE);
                                                                                 date.setVisibility(View.VISIBLE);
                                                                                 vurud.setVisibility(View.INVISIBLE);
                                                                             }
                                                                         }, now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay());



        datePickerDialog.show(getFragmentManager(), "tpd");
    }
    public void onClickKhuruj(View v) {
        PersianCalendar now = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                                                                             @Override
                                                                             public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                                                                 Toast.makeText(getApplicationContext(), "" + year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                                                                                 khuruj_day.setText(String.valueOf(dayOfMonth));
                                                                                 khuruj_date.setText(monthOfYear + "," + year);
                                                                                 khuruj_day.setVisibility(View.VISIBLE);
                                                                                 khuruj_date.setVisibility(View.VISIBLE);
                                                                                 khuruj.setVisibility(View.INVISIBLE);

                                                                             }
                                                                         }, now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay());



        datePickerDialog.show(getFragmentManager(), "tpd");
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
}
