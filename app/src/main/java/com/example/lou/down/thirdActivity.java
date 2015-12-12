package com.example.lou.down;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Marcos on 10/20/2015.
 */
public class thirdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.third_layout);
    }

    public void sendButtonSlideThree(View v) {
        EditText EventAccept = (EditText)findViewById(R.id.eventAccept);
        EditText EventDeny = (EditText)findViewById(R.id.eventDeny);
        TimePicker EventTime = (TimePicker)findViewById(R.id.timePicker);

        Intent intent = getIntent();
        EventClass event = (EventClass) intent.getExtras().getSerializable("eventFromSlideTwo");


        if (EventAccept.getText().toString().isEmpty()){
            event.setAccept("Down");
        } else {
            event.setAccept(EventAccept.getText().toString());
        }
        if (EventDeny.getText().toString().isEmpty()){
            event.setDeny("Nahh");
        } else {
            event.setDeny(EventDeny.getText().toString());
        }

        event.setHour(EventTime.getCurrentHour());
        event.setMinute(EventTime.getCurrentMinute());

        ParseObject eventObj = new ParseObject("event");
        eventObj.put("eventName", event.getName());
        eventObj.put("eventDiscription", event.getDiscription());
        eventObj.put("location", event.getLocation());
        eventObj.put("hour", event.getHour());
        eventObj.put("minute", event.getMinute());
        eventObj.put("month", event.getMonth());
        eventObj.put("day", event.getDay());
        eventObj.put("year", event.getYear());
        eventObj.put("accept", event.getAccept());
        eventObj.put("deny", event.getDeny());
        eventObj.put("owner", ParseUser.getCurrentUser().getUsername());
        for (String retVal: event.getInviteeList().split("\n")) {
            eventObj.addUnique("inviteeList", retVal);
        }


        eventObj.saveInBackground();

        CharSequence confirmation = "Event Created";
        Toast toast = Toast.makeText(getApplicationContext(),confirmation, Toast.LENGTH_LONG);
        toast.show();

        Intent c = new Intent(this, MainActivity.class );
        c.putExtra("eventFromSlideThree", event);
        finish();

    }

    public void backButtonSlideThree(View v) {
        //Intent d = new Intent(this, secondActivity.class);
        //startActivity(d);
        finish();
    }

}
