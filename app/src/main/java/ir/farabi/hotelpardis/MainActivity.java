package ir.farabi.hotelpardis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    databaseHandler DB;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB= new databaseHandler(getApplicationContext());
        listView = (ListView)findViewById(R.id.list);
        CustomListViewAdapter customListView = new CustomListViewAdapter(getApplicationContext());
        listView.setAdapter(customListView);

    }
}
