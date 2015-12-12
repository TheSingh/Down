package com.example.lou.down;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import com.parse.Parse;

/**
 * Created by kchinnap on 11/7/2015.
 */

public class LoginClass extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Login);
        setContentView(R.layout.login_layout);
    }

    public void login_event(View v) {
        EditText usernameView = (EditText) findViewById(R.id.eventUsername);
        EditText passwordView = (EditText) findViewById(R.id.eventPassword1);

        boolean validationError = false;
        StringBuilder validationErrorMessage =
                new StringBuilder("Please enter a ");

        if (isEmpty(usernameView)) {
            validationError = true;
            validationErrorMessage.append("username");
        }
        if (isEmpty(passwordView)) {
            if (validationError) {
                validationErrorMessage.append(", and enter a ");
            }
            validationError = true;
            validationErrorMessage.append("password ");
        }
        validationErrorMessage.append(".");

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(LoginClass.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dlg = new ProgressDialog(LoginClass.this);
        dlg.setTitle("Working.");
        dlg.setMessage("Logging in.  Please wait.");
        dlg.show();

        ParseUser.logInInBackground(usernameView.getText().toString(), passwordView.getText()
                .toString(), new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                dlg.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(LoginClass.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    // Start an intent for the dispatch activity
                    Intent intent = new Intent(LoginClass.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void do_signup(View v) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}

