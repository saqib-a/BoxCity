package com.example.saqib.myapplication.Model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.saqib.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DiscussionChat extends AppCompatActivity {

    Button sendDiscussionButton;
    EditText editDiscussionMsg;
    ListView lvDiscussionMsg;
    ArrayList<String> listDiscussionMsg = new ArrayList<String>();
    ArrayAdapter arrayAdpt;
    private DatabaseReference dbref;
    String UserName;
    String SelectedSubject;
    String user_discussionMsg_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_chat);

        sendDiscussionButton = (Button) findViewById(R.id.sendDiscussionButton);
        editDiscussionMsg = (EditText) findViewById(R.id.editDiscussionMsg);
        lvDiscussionMsg = (ListView) findViewById(R.id.discussionMsg);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listDiscussionMsg);
        lvDiscussionMsg.setAdapter(arrayAdpt);

        UserName = getIntent().getExtras().get("user_name").toString();
        SelectedSubject = getIntent().getExtras().get("selected_subject").toString();
        setTitle("Subject : " + SelectedSubject);

        dbref = FirebaseDatabase.getInstance().getReference().child(SelectedSubject);

        sendDiscussionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                user_discussionMsg_key = dbref.push().getKey();
                dbref.updateChildren(map);

                DatabaseReference dbref2 = dbref.child(user_discussionMsg_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("msg", editDiscussionMsg.getText().toString());
                map2.put("user", UserName);
                dbref2.updateChildren(map2);
            }
        });

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateDiscussion(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateDiscussion(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateDiscussion(DataSnapshot dataSnapshot)
    {
        String msg;
        String user;
        String discussion;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            msg = (String) ((DataSnapshot)i.next()).getValue();
            user = (String) ((DataSnapshot)i.next()).getValue();

            discussion = user + ": " + msg;
            arrayAdpt.insert(discussion, 0);
            arrayAdpt.notifyDataSetChanged();
        }
    }

}
