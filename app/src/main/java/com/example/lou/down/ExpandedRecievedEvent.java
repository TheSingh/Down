package com.example.lou.down;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class ExpandedRecievedEvent extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_expanded_recieved_event);

        Intent intent = getIntent();
        EventClass event = (EventClass) intent.getExtras().getSerializable("eventReceivedPassed");

        final Button downButton = (Button) findViewById(R.id.DownButton);
        final Button nahButton = (Button) findViewById(R.id.NahButton);
        final TextView acceptedTxt = (TextView) findViewById(R.id.acceptText);

        downButton.setText(event.getAccept());
        nahButton.setText(event.getDeny());



        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");
        query.getInBackground(event.getId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    ArrayList<String> a = (ArrayList<String>)object.get("accepteeList");
                    if (a == null || !a.contains(ParseUser.getCurrentUser().getUsername())) {
                        downButton.setVisibility(View.VISIBLE);
                        nahButton.setVisibility(View.VISIBLE);
                        acceptedTxt.setVisibility(View.GONE);
                    }
                } else {
                    downButton.setVisibility(View.VISIBLE);
                    nahButton.setVisibility(View.VISIBLE);
                    acceptedTxt.setVisibility(View.GONE);
                }
            }
        });

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

    }
    public void downButton(View v){
        Intent intent = getIntent();
        EventClass event = (EventClass) intent.getExtras().getSerializable("eventReceivedPassed");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");

        query.getInBackground(event.getId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    Toast.makeText(ExpandedRecievedEvent.this, "Invite Accepted.", Toast.LENGTH_LONG).show();

                    object.addAllUnique("accepteeList", Arrays.asList(ParseUser.getCurrentUser().getUsername()));
                    object.saveInBackground();
                }
            }
        });
        setResult(RESULT_OK, null);
        finish();

    };


    public void nahButton(View v){
        Intent intent = getIntent();
        EventClass event = (EventClass) intent.getExtras().getSerializable("eventReceivedPassed");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("event");

        query.getInBackground(event.getId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object != null) {
                    object.removeAll("inviteeList", Arrays.asList(ParseUser.getCurrentUser().getUsername()));
                    // Set up a progress dialog
                    final ProgressDialog dlg = new ProgressDialog(ExpandedRecievedEvent.this);
                    dlg.setTitle("Working.");
                    dlg.setMessage("Completing your request.  Please wait.");
                    dlg.show();
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(ExpandedRecievedEvent.this, "Invite Declined.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ExpandedRecievedEvent.this, "There seems to be an error.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dlg.dismiss();
                }
            }
        });

        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public void onBackPressed(){
        setResult(RESULT_OK, null);
        finish();
    }

}
