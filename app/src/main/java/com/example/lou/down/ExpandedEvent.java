package com.example.lou.down;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.List;

public class ExpandedEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_expanded_event);

        Intent intent = getIntent();
        EventClass event = (EventClass) intent.getExtras().getSerializable("eventPassed");

        TextView eventText = (TextView)findViewById(R.id.eventTextView);
        String ampm = "";

        if (event.getHour() < 12){
            ampm = "A.M.";
        } else {
            ampm = "P.M.";
            event.setHour(event.getHour() - 12);
        }

        String zeroSpacer = "";
        if (event.getMinute() < 10){
            zeroSpacer += "0";
        }

        eventText.setText(" " + event.getName() + " \n " +
                event.getLocation() + " \n " +
                event.getMonth() + "\\" + event.getDay() + "\\" + event.getYear() + " \n " +
                event.getHour() + ":" + zeroSpacer + event.getMinute() + " " + ampm + " \n " +
                event.getDiscription() + " ");

        String peopleInvited[] = event.getInviteeList().split("\\r?\\n");

        ListView listView = (ListView) findViewById(R.id.InvitedList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, peopleInvited);

        listView.setAdapter(adapter);

        setResult(RESULT_OK, null);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}
