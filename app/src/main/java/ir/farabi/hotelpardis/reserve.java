package ir.farabi.hotelpardis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link reserve.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link reserve#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reserve extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listView;
    databaseHandler dba;
    Button cancel;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-02-08 23:48:42 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */


    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-02-08 23:48:42 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public reserve() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reserve.
     */
    // TODO: Rename and change types and number of parameters
    public static reserve newInstance(String param1, String param2) {
        reserve fragment = new reserve();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_reserve, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        cancel=(Button)view.findViewById(R.id.cancel);

        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<User> users = new ArrayList<User>();

        dba = new databaseHandler(inflater.getContext());
        User user = dba.getUser(String.valueOf(1));
        ArrayList<resereveModule> resereveModule = dba.getReserves(String.valueOf(1));
        if (resereveModule != null) {
            for (int i = 0; i < resereveModule.size(); i++) {
                Room room= dba.getRoom(resereveModule.get(i).getRoomNumber());


                rooms.add(room);
            }
        }
        CustomListViewAdapter customListView = new CustomListViewAdapter(getContext(),user,rooms,resereveModule );
        listView.setAdapter(customListView);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
