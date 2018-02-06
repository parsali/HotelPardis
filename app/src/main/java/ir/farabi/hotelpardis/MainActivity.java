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


        DB= new databaseHandler(getApplicationContext());
        listView = (ListView)findViewById(R.id.list);
        CustomListViewAdapter customListView = new CustomListViewAdapter(getApplicationContext());
        listView.setAdapter(customListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),moreInfo.class);
                startActivity(intent);
            }
        });
    }
}
