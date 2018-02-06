package ir.farabi.hotelpardis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    databaseHandler DB;
    ActionBar ab;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myBestoolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);


        DB= new databaseHandler(getApplicationContext());

        CustomListViewAdapter customListView = new CustomListViewAdapter(getApplicationContext());

    }
    public void onClickLux(View v){
        Intent intent = new Intent(getApplicationContext(),moreInfo.class);
        intent.putExtra("type",1);
        startActivity(intent);
    }
    public void onClickPrimium(View v){
        Intent intent = new Intent(getApplicationContext(),moreInfo.class);
        intent.putExtra("type",2);
        startActivity(intent);
    }
    public void onClickEconomy(View v){
        Intent intent = new Intent(getApplicationContext(),moreInfo.class);
        intent.putExtra("type",3);
        startActivity(intent);
    }
}
