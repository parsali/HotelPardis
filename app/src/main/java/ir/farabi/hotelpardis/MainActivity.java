package ir.farabi.hotelpardis;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    databaseHandler DB;
    ActionBar ab;
    private ListView listView;
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

//        DB= new databaseHandler(getApplicationContext());
//        listView = (ListView)findViewById(R.id.list);
//        CustomListViewAdapter customListView = new CustomListViewAdapter(getApplicationContext());
//        listView.setAdapter(customListView);

    }
}
