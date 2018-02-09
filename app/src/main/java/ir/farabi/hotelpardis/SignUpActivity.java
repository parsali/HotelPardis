package ir.farabi.hotelpardis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    EditText name;
    EditText username;
    EditText password;
    EditText code;
    TextView wrongName;
    TextView wrongUsername;
    TextView wrongPassword;
    TextView wrongCode;

    boolean userCheck=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        code = (EditText) findViewById(R.id.code);
        wrongName = (TextView) findViewById(R.id.wrongName);
        wrongUsername = (TextView) findViewById(R.id.wrongUsername);
        wrongPassword = (TextView) findViewById(R.id.wrongPassword);
        wrongCode = (TextView) findViewById(R.id.wrongCode);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if(username.getText().toString().length()!=0){
                        databaseHandler db = new databaseHandler(getApplicationContext());
                        if(db.checkUser(username.getText().toString())){
                            wrongUsername.setText("متاسفانه این نام کاربری قبلا گرفته شده است");
                            wrongUsername.setVisibility(View.VISIBLE);
                            userCheck=false;
                        }
                    }
                }
                else {
                    wrongUsername.setVisibility(View.GONE);
                    wrongUsername.setText("نام کاربری به درستی وارد نشده است");
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    wrongName.setVisibility(View.GONE);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    wrongPassword.setVisibility(View.GONE);
                }
            }
        });
        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    wrongCode.setVisibility(View.GONE);
                }
            }
        });
    }
    public void onClickSignUp(View v){
        boolean nameCheck=false;
        boolean passwordCheck=false;
        boolean codeCheck=false;
        boolean usernameCheck=false;

        if(name.getText().toString().length()>1 && name.getText().toString().length()<50){
            nameCheck=true;
            wrongName.setVisibility(View.GONE);
        }
        else wrongName.setVisibility(View.VISIBLE);

        if(username.getText().toString().matches("^[a-zA-Z]+([.]?[a-zA-Z0-9]+)*$")){
            databaseHandler db = new databaseHandler(this);
            if(db.checkUser(username.getText().toString())) {
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

        Log.d("app123",String.valueOf(nameCheck)+String.valueOf(usernameCheck)+String.valueOf(passwordCheck)+String.valueOf(codeCheck));
        if(nameCheck && passwordCheck && codeCheck && usernameCheck){
            databaseHandler db = new databaseHandler(this);
            User user = new User();
            user.setName(name.getText().toString());
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setCode(code.getText().toString());
            db.addUser(user);
            Intent i = new Intent(this,LoginActivity.class);
            i.putExtra("Username",username.getText().toString());
            i.putExtra("Password",password.getText().toString());
            startActivity(i);
        }
    }
}
