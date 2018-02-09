package ir.farabi.hotelpardis;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmptyRoom.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmptyRoom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmptyRoom extends Fragment {
    EditText name;
    EditText username;
    EditText password;
    EditText code;
    TextView wrongName;
    TextView wrongUsername;
    TextView wrongPassword;
    TextView wrongCode;
    Button edit;
    SessionManager session;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EmptyRoom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmptyRoom.
     */
    // TODO: Rename and change types and number of parameters
    public static EmptyRoom newInstance(String param1, String param2) {
        EmptyRoom fragment = new EmptyRoom();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_empty_room, container, false);

        name = (EditText) rootView.findViewById(R.id.name);
        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        code = (EditText) rootView.findViewById(R.id.code);
        wrongName = (TextView) rootView.findViewById(R.id.wrongName);
        wrongUsername = (TextView) rootView.findViewById(R.id.wrongUsername);
        wrongPassword = (TextView) rootView.findViewById(R.id.wrongPassword);
        wrongCode = (TextView) rootView.findViewById(R.id.wrongCode);
        edit = (Button) rootView.findViewById(R.id.signUp);

        session = new SessionManager(inflater.getContext());
        HashMap<String, String> userHash = session.getUserDetails();
        databaseHandler db = new databaseHandler(inflater.getContext());
        User userDetails = new User();
        userDetails = db.getUser(userHash.get(SessionManager.USERNAME));

        name.setText(userDetails.getName());
        username.setText(userDetails.getUsername());
        code.setText(userDetails.getCode());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nameCheck=false;
                boolean passwordCheck=false;
                boolean codeCheck=false;
                boolean usernameCheck=false;

                session = new SessionManager(inflater.getContext());
                HashMap<String, String> userHash = session.getUserDetails();
                databaseHandler db = new databaseHandler(inflater.getContext());
                User userDetails = new User();
                userDetails = db.getUser(userHash.get(SessionManager.USERNAME));

                if(name.getText().toString().length()>1 && name.getText().toString().length()<50){
                    nameCheck=true;
                    wrongName.setVisibility(View.GONE);
                }
                else wrongName.setVisibility(View.VISIBLE);

                if(username.getText().toString().matches("^[a-zA-Z]+([.]?[a-zA-Z0-9]+)*$")){
                    if(db.checkUser(username.getText().toString()) && !(username.getText().toString().equals(userDetails.getUsername()))) {
                        wrongUsername.setText("متاسفانه این نام کاربری قبلا گرفته شده است");
                        wrongUsername.setVisibility(View.VISIBLE);
                        usernameCheck =false;
                    }
                    else if(username.getText().length()<50) {
                        usernameCheck=true;
                        wrongUsername.setVisibility(View.GONE);
                    }
                }
                else {
                    wrongUsername.setText("نام کاربری به درستی وارد نشده است");
                    wrongUsername.setVisibility(View.VISIBLE);
                }

                if(password.getText().toString().length()>=8 && password.getText().length()<50){
                    passwordCheck=true;
                    wrongPassword.setVisibility(View.GONE);
                }
                else wrongPassword.setVisibility(View.VISIBLE);

                if(code.getText().toString().matches("^[0-9]{10}")){
                    codeCheck=true;
                    wrongCode.setVisibility(View.GONE);
                }
                else wrongCode.setVisibility(View.VISIBLE);

                if(nameCheck && passwordCheck && codeCheck && usernameCheck){
                    User user = new User();
                    user.setName(name.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setCode(code.getText().toString());
                    user.setId(userDetails.getId());
                    db.updateUser(user);
                    Toast.makeText(inflater.getContext(),"اطلاعات بروزرسانی شد",Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
