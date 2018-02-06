package ir.farabi.hotelpardis;

import android.content.Intent;
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
    TextView forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);

    }

    public void onClickLogin(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void onClickForgetPassword(View v){
        Toast.makeText(this,"متاسفانه کاری از دست ما ساخته نیست",Toast.LENGTH_SHORT).show();
    }
}
