package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText email,userName,password;
    Button signUp,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        email = findViewById(R.id.edtEamilSignUp);
        userName = findViewById(R.id.edtUserNameSignUp);
        password = findViewById(R.id.edtPasswordSignUp);
        signUp=findViewById(R.id.btnSignUp2);
        login = findViewById(R.id.btnLogin2);
        signUp.setOnClickListener(SignUp.this);
        login.setOnClickListener(SignUp.this);
        if (ParseUser.getCurrentUser() != null){
            finish();
            goWelcomePage();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin2:
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
                break;
            case  R.id.btnSignUp2:
               ParseUser parseUser = new ParseUser();
               if (email.getText().toString().equals("") || userName.getText().toString().equals("") || password.getText().toString().equals("") ){
                   Toast.makeText(SignUp.this,"Email,UserName And Password is required",Toast.LENGTH_LONG).show();
               }else {
                   ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                   progressDialog.setMessage("Loading...");
                   progressDialog.show();
                   parseUser.setEmail(email.getText().toString());
                   parseUser.setUsername(userName.getText().toString());
                   parseUser.setPassword(password.getText().toString());
                   parseUser.signUpInBackground(new SignUpCallback() {
                       @Override
                       public void done(ParseException e) {
                           if (e == null){
                               Toast.makeText(SignUp.this,"Welcome "+userName.getText().toString(),Toast.LENGTH_LONG).show();
                               goWelcomePage();
                           }else {
                               Toast.makeText(SignUp.this,e.getMessage(),Toast.LENGTH_LONG).show();

                           }
                       }
                   });
                   progressDialog.dismiss();
               }

                break;
        }

    }
    public void goWelcomePage(){
        Intent intent = new Intent(SignUp.this,WelcomePage.class);
        finish();
        startActivity(intent);

    }
}