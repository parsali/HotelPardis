package ir.farabi.hotelpardis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by paulodichone on 2/4/15.
 */
public class CustomListViewAdapter extends BaseAdapter {

    private Context mContext;
    private  ArrayList<HashMap<String, String>> hesabs;
    private static LayoutInflater inflater = null;


    public CustomListViewAdapter(Context context){

        mContext = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (convertView == null){

            view = inflater.inflate(R.layout.row,null);

            TextView type = (TextView) view.findViewById(R.id.type);
            ImageView image = (ImageView)view.findViewById(R.id.image);





           if(position==0){
               type.setText("اقتصادی");
               image.setImageResource(R.drawable.economy);
           }
            else if (position==1){
               type.setText("لوکس");
               image.setImageResource(R.drawable.luxury);
           }
            else if(position==2){
               type.setText("ویژه");
               image.setImageResource(R.drawable.special);
           }




        }


        return view;
    }
}
