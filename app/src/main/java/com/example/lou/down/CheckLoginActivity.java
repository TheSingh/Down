package com.example.lou.down;

import android.os.Bundle;
import android.app.Activity;

import com.parse.Parse;
import com.parse.ParseUser;
import android.content.Intent;

public class CheckLoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "2dm2ZO6deyFBrptTQSguyhVudhU8pekQ1UuWRE7G", "oFp2oCbrhg1cucYGA3rTfeI7dY5y54Elwi7quEIl");

        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, LoginClass.class));
        }
    }

}
