package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText edtUserName,edtPassword;

    Button btnSignUp,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        edtUserName = findViewById(R.id.edtUserNamelogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnLogin.setOnClickListener(Login.this);
        btnSignUp.setOnClickListener(Login.this);
        if (ParseUser.getCurrentUser() != null){
            finish();
            goWelcomePage();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btnLogin:
                if (edtUserName.getText().toString().equals("")||edtPassword.getText().toString().equals("")){
                    Toast.makeText(Login.this,"You must enter Username and Password",Toast.LENGTH_LONG).show();

                }else {
                    ParseUser.logInInBackground(edtUserName.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {

                        @Override
                        public void done(ParseUser user, ParseException e) {
                            ProgressDialog progressDialog = new ProgressDialog(Login.this);
                            progressDialog.setMessage("Loading...");
                            progressDialog.show();


                            if (user != null && e == null){
                                Toast.makeText(Login.this,"Welcome Back "+edtUserName.getText().toString(),Toast.LENGTH_LONG).show();
                                goWelcomePage();


                            }else {
                                Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }

                    });

                }

            break;
            case  R.id.btnSignUp:
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
                break;
        }

    }
    public void goWelcomePage(){
        Intent intent = new Intent(Login.this,WelcomePage.class);
        finish();
        startActivity(intent);

    }
}