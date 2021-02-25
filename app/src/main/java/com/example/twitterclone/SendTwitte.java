package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SendTwitte extends AppCompatActivity implements View.OnClickListener {
    private Button btnSend;
    private EditText twitte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_twitte);
        btnSend = findViewById(R.id.btnSend);
        twitte = findViewById(R.id.edtTwitte);
        btnSend.setOnClickListener(SendTwitte.this);
    }


    @Override
    public void onClick(View v) {
        ParseObject parseObject = new ParseObject("twitte");
        parseObject.put("twitte",twitte.getText().toString());
        parseObject.put("userName",ParseUser.getCurrentUser().getUsername());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(SendTwitte.this,"It's Send",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}