package com.example.lou.down;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by Robin on 11/22/2015.
 */

public class CustomAdapter extends ParseQueryAdapter<ParseObject>{

    public CustomAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("event");
                query.whereEqualTo("owner", ParseUser.getCurrentUser().getUsername());
                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.event_layout, null);
        }
        super.getItemView(object, v, parent);

        TextView eventNameView = (TextView) v.findViewById(R.id.text1);
        eventNameView.setText(object.getString("eventName"));

        TextView eventDescriptionView = (TextView) v.findViewById(R.id.text2);
        eventDescriptionView.setText(object.getString("eventDiscription"));

        TextView eventLocationView = (TextView) v.findViewById(R.id.text3);
        String loc = object.getString("location");
        if(loc.isEmpty())  loc = "unknown location";
        eventLocationView.setText(loc);

        TextView eventTimeView = (TextView) v.findViewById(R.id.text4);
        String time = "";
        time += "@" + object.getInt("hour") + ":" + object.getInt("minute") +
                " on " + object.getInt("month") + "/" + object.getInt("day") +
                "/" + object.getInt("year");
        eventTimeView.setText(time);
        return v;
    }
}
