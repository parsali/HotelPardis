package ir.farabi.hotelpardis;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button login;
    Button signUp;
    Button reset;
    TextView forgetPassword;
    int databaseHidden;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signUp);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        reset = (Button) findViewById(R.id.reset);
        session = new SessionManager(getApplicationContext());

        Intent i = getIntent();
        if(i!=null) {
            String passedUsername = i.getStringExtra("Username");
            String passedPassword = i.getStringExtra("Password");
            username.setText(passedUsername);
            password.setText(passedPassword);
        }

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                    username.setTextColor(colorStateList);
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                    password.setTextColor(colorStateList);
                }
            }
        });


    }

    public void onClickLogin(View v){
        databaseHandler db = new databaseHandler(this);
        boolean user = db.checkUser(username.getText().toString().trim(),password.getText().toString().trim());
        if(user) {
            User userDetails = new User();
            userDetails = db.getUser(username.getText().toString());
            session.createLoginSession(userDetails.getUsername(),String.valueOf(userDetails.getId()));
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this,"نام کاربری یا گذرواژه به درستی وارد نشده است.",Toast.LENGTH_LONG).show();
            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
            username.setTextColor(colorStateList);
            password.setTextColor(colorStateList);
        }
    }
    public void onClickSignUp (View v){
        Intent i = new Intent(this,SignUpActivity.class);
        startActivity(i);
    }
    public void onClickForgetPassword(View v){
        Toast.makeText(this,"متاسفانه کاری از دست ما ساخته نیست",Toast.LENGTH_SHORT).show();
    }
    public void onClickDatabase(View v){
        databaseHidden++;
        if(databaseHidden==2){
            reset.setVisibility(View.VISIBLE);
        }
        if(databaseHidden==3) {
            databaseHidden = 0;
            reset.setVisibility(View.GONE);
            Intent i = new Intent(this, AndroidDatabaseManager.class);
            startActivity(i);
        }
    }
    public void onClickResetDatabase(View v){
        databaseHandler db = new databaseHandler(this);
        db.recreate();
        Toast.makeText(this,"database recreated!",Toast.LENGTH_LONG).show();
    }
}
