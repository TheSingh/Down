package com.example.lou.down;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TabHost;
import android.widget.ListView;
import android.content.Intent;

import com.parse.ParseObject;

import java.util.List;

public class MainActivityScreenTwo extends Activity {

    private CustomAdapter ca;
    private InviteAdapter ia;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main_activity_screen_two);

        ca = new CustomAdapter(this);
        ia = new InviteAdapter(this);

        listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(ca);
        ca.loadObjects();

        showInvites(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            ia = new InviteAdapter(this);
            listView.setAdapter(ia);
            ia.loadObjects();
        }
    }

    public EventClass makeEventToPass(ParseObject Event){
        EventClass ret = new EventClass();

        ret.setName(Event.getString("eventName"));
        ret.setEventDiscription(Event.getString("eventDiscription"));
        ret.setLocation(Event.getString("location"));
        ret.setHour(Event.getInt("hour"));
        ret.setMinute(Event.getInt("minute"));
        ret.setMonth(Event.getInt("month") - 1);
        ret.setDay(Event.getInt("day"));
        ret.setYear(Event.getInt("year"));
        ret.setAccept(Event.getString("accept"));
        ret.setDeny(Event.getString("deny"));
        ret.setId(Event.getObjectId());


        String invitedList = "";

        for (int i = 0; i < Event.getList("inviteeList").size(); i++) {
            invitedList += Event.getList("inviteeList").get(i);
            invitedList += "\n";
        }



        ret.setInviteeList(invitedList);
        return ret;
    }


    public void showInvites(View v) {
        if(listView.getAdapter() != ia) {
            listView.setAdapter(ia);
            ia.loadObjects();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject toExpand = ia.getItem(position);
                EventClass eventToPass = makeEventToPass(toExpand);

                Intent expandEvent = new Intent(MainActivityScreenTwo.this, ExpandedRecievedEvent.class);
                expandEvent.putExtra("eventReceivedPassed", eventToPass);
                startActivityForResult(expandEvent,1);
            }
        });
    }

    public void showSent(View v) {
        if(listView.getAdapter() != ca) {
            listView.setAdapter(ca);
            ca.loadObjects();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject toExpand = ca.getItem(position);
                EventClass eventToPass = makeEventToPass(toExpand);

                Intent expandEvent = new Intent(MainActivityScreenTwo.this, ExpandedEvent.class);
                expandEvent.putExtra("eventPassed", eventToPass);
                startActivityForResult(expandEvent,1);
            }
        });
    }

}
