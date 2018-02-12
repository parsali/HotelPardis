package ir.farabi.hotelpardis;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    refresh refresh;

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


    public CustomListViewAdapter(Context context,User user,ArrayList<Room> rooms,ArrayList<resereveModule> resereveModule,refresh refresh){

        mContext = context;
        this.user=user;
        this.rooms=rooms;
        this.resereveModules=resereveModule;
        this.refresh=refresh;



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
       // if(resereveModules.size()==0)
           // Toast.makeText(mContext,String.valueOf(position),Toast.LENGTH_LONG).show();

        View view = convertView;

        if (convertView == null){
            Log.d("postirion",String.valueOf(position));

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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = resereveModules.get(position).getStartDate();
            Roozh roozh=new Roozh();
            long Date1=0;
            long Date2=0;

            try {
                int add=1;
                Date date = formatter.parse(dateInString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Date1=date.getTime();
                if (cal.get(Calendar.MONTH)==12)
                    add=0;
                roozh = new Roozh();
                roozh.GregorianToPersian(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+add,cal.get(Calendar.DAY_OF_MONTH));
                System.out.println(date);
                System.out.println(formatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            startDate.setText(roozh.toString());
            try {
                dateInString=resereveModules.get(position).getEndDate();
                Date date = formatter.parse(dateInString);
                Date2=date.getTime();
                roozh = new Roozh();
                int add=1;
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                if (cal.get(Calendar.MONTH)==12)
                    add=0;

                roozh.GregorianToPersian(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+add,cal.get(Calendar.DAY_OF_MONTH));
                System.out.println(date);
                System.out.println(formatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            int diff = (int)Math.abs(Math.round((Date1 - Date2) / (1000 * 60 * 60 * 24)));
            textkhuruj.setText(roozh.toString());
            price.setText(String.valueOf(rooms.get(position).getPrice()*diff)+" تومان ");
            typeInt=Integer.valueOf(rooms.get(position).getType());
            Log.d("typeInt",String.valueOf(typeInt));
            Log.d("userId",String.valueOf(user.getId()));
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteFromReserve(rooms.get(position).getRoomNumber(), String.valueOf(user.getId()), resereveModules.get(position).getStartDate(), resereveModules.get(position).getEndDate());
                   refresh.refresh();
//                    reserve reserve = new reserve();
//                    ((reserve)reserve).refresh();

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
