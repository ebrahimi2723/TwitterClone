package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Posts extends AppCompatActivity {
    private ListView listView;
    ArrayList<HashMap<String,String>>tweetList;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);
        listView= findViewById(R.id.listView);
         tweetList=new ArrayList<>();
          adapter = new SimpleAdapter(this,tweetList, android.R.layout.simple_list_item_2,new String[]{"tweetUserName","tweetValue"},new int[]{android.R.id.text1,android.R.id.text2});
       try {
           ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("twitte");
           parseQuery.whereContainedIn("userName", ParseUser.getCurrentUser().getList("follower"));
           parseQuery.findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(List<ParseObject> objects, ParseException e) {
                   if (objects.size()>0 || e == null){
                       for (ParseObject user: objects){
                           HashMap<String,String>userTweet = new HashMap<>();
                           userTweet.put("tweetUserName",user.getString("userName"));
                           userTweet.put("tweetValue",user.getString("twitte"));
                           tweetList.add(userTweet);

                       }
                       listView.setAdapter(adapter);
                   }
               }
           });

       }catch (Exception e){
           e.printStackTrace();
       }

    }
}