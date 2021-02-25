package com.example.twitterclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class WelcomePage extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList <String> arrayList;
    private ArrayAdapter arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        listView = findViewById(R.id.UserList);
        arrayList = new ArrayList();
        arrayAdapter =new ArrayAdapter(WelcomePage.this, android.R.layout.simple_list_item_checked,arrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(WelcomePage.this);


        ParseQuery <ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size()>0 && e == null){
                    for (ParseUser user:objects){
                        arrayList.add(user.getUsername());

                    }
                    listView.setAdapter(arrayAdapter);
                     for (String user : arrayList){
                         if (ParseUser.getCurrentUser().getList("follower") != null) {
                             if (ParseUser.getCurrentUser().getList("follower").contains(user)) {
                                 listView.setItemChecked(arrayList.indexOf(user), true);
                             }
                         }

                 }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOutUser){
            ParseUser.logOut();
            Intent intent = new Intent(WelcomePage.this,Login.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.sendTwitte){
            Intent intent = new Intent(WelcomePage.this,SendTwitte.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.Posts){
            Intent intent = new Intent(WelcomePage.this,Posts.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()){
            ParseUser.getCurrentUser().add("follower",arrayList.get(position));
            Toast.makeText(WelcomePage.this,arrayList.get(position)+" is followed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(WelcomePage.this,arrayList.get(position)+" is unfollowed",Toast.LENGTH_SHORT).show();
              ParseUser.getCurrentUser().getList("follower").remove(arrayList.get(position));
              List newFollower = ParseUser.getCurrentUser().getList("follower");
              ParseUser.getCurrentUser().remove("follower");
              ParseUser.getCurrentUser().put("follower",newFollower);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Toast.makeText(WelcomePage.this,"saved",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}