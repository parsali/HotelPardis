package ir.farabi.hotelpardis;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by paulodichone on 2/4/15.
 */
public class CustomListViewAdapter extends BaseAdapter {

    private Context mContext;
    private  ArrayList<HashMap<String, String>> hesabs;
    private static LayoutInflater inflater = null;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CardView cardView;
    private TextView type;
    private TextView name;
    private TextView textVurud;
    private TextView startDate;
    private TextView textkhuruj;
    private Button cancel;
    private TextView price;
    databaseHandler db;

    ImageView image;
    int size;
    int typeInt;
    String DateStart;
    String DateEnd;
    int priceInt;
    String namePerson;
    ArrayList<Room> rooms;
    ArrayList<resereveModule> resereveModules;
    User user;


    public CustomListViewAdapter(Context context,User user,ArrayList<Room> rooms,ArrayList<resereveModule> resereveModule){

        mContext = context;
        this.user=user;
        this.rooms=rooms;
        this.resereveModules=resereveModule;



        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return resereveModules.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(resereveModules.size()==0)
            Toast.makeText(mContext,"nothing to show",Toast.LENGTH_LONG).show();

        View view = convertView;

        if (convertView == null){

            view = inflater.inflate(R.layout.row,null);

            type = (TextView)view.findViewById( R.id.type );
            name = (TextView)view.findViewById( R.id.name );
            textVurud = (TextView)view.findViewById( R.id.textVurud );
            startDate = (TextView)view.findViewById( R.id.startDate );
            textkhuruj = (TextView)view.findViewById( R.id.khurujDate );
            cancel = (Button)view.findViewById( R.id.cancel );
            price = (TextView)view.findViewById( R.id.price );
            image=(ImageView)view.findViewById(R.id.imageHotel);
            name.setText(user.getName());
            db= new databaseHandler(mContext);
            startDate.setText(resereveModules.get(position).getStartDate());
            textkhuruj.setText(resereveModules.get(position).getEndDate());
            price.setText(String.valueOf(rooms.get(position).getPrice()));
            typeInt=Integer.valueOf(rooms.get(position).getType());
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteFromReserve(rooms.get(position).getRoomNumber(),String.valueOf(user.getId()),resereveModules.get(position).getStartDate(),resereveModules.get(position).getEndDate());
                    notifyDataSetChanged();
                    Toast.makeText(mContext,"here",Toast.LENGTH_LONG).show();
                }
            });






            if(typeInt==1){
               type.setText("لوکس");
               image.setImageResource(R.drawable.luxury);
           }
            else if (typeInt==2){
               type.setText("ویژه");
               image.setImageResource(R.drawable.special);
           }
            else if(typeInt==3){
               type.setText("اقتصادی");
               image.setImageResource(R.drawable.economy);
           }




        }


        return view;
    }
}
