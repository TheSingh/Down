package com.example.lou.down;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Marcos on 10/20/2015.
 */
public class secondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.second_layout);

    }

    public void nextButtonSlideTwo(View v){
        EditText EventWhere = (EditText)findViewById(R.id.eventWhere);
        DatePicker EventWhen = (DatePicker)findViewById(R.id.datePicker);

        Intent intent = getIntent();
        EventClass event = (EventClass) intent.getExtras().getSerializable("eventFromSlideOne");

        event.setLocation(EventWhere.getText().toString());
        event.setMonth(EventWhen.getMonth());
        event.setDay(EventWhen.getDayOfMonth());
        event.setYear(EventWhen.getYear());

        Intent b = new Intent(this, thirdActivity.class );
        b.putExtra("eventFromSlideTwo", event);
        startActivity(b);
        finish();
    }

    public void backButtonSlideTwo(View v){
        //Intent a = new Intent(this, MainActivity.class);
        //startActivity(a);
        finish();
    }



}
