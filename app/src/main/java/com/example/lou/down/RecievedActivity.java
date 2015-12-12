package com.example.lou.down;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class RecievedActivity extends Activity {
    //private CustomAdapter ca;
    private InviteAdapter ia;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved);

        //ca = new CustomAdapter(this);
        ia = new InviteAdapter(this);

        listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(ia);
        ia.loadObjects();
    }

}
