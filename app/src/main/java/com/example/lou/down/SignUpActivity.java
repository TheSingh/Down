package com.example.lou.down;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

    private EditText usernameView;
    private EditText passwordView;
    private EditText passwordAgainView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Login);
        setContentView(R.layout.signup);

        // Set up the signup form.
        usernameView = (EditText) findViewById(R.id.eventUsername);
        passwordView = (EditText) findViewById(R.id.eventPassword1);
        passwordAgainView = (EditText) findViewById(R.id.eventPassword2);

        // Set up the submit button click handler
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                StringBuilder errorMsg = new StringBuilder("Please ");
                Boolean validationError = false;

                if (isEmpty(usernameView)) {
                    validationError = true;
                    errorMsg.append("enter a username");
                }

                if (isEmpty(passwordView)) {
                    if(validationError) errorMsg.append(", and ");
                    validationError = true;
                    errorMsg.append("enter a password");
                }

                if(!isMatching(passwordView, passwordAgainView)) {
                    if (validationError) errorMsg.append(", and ");
                    validationError = true;
                    errorMsg.append("enter matching passwords");
                }

                errorMsg.append(".");
                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(SignUpActivity.this, errorMsg.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(SignUpActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();

                // Set up a new Parse user
                ParseUser user = new ParseUser();
                user.setUsername(usernameView.getText().toString());
                user.setPassword(passwordView.getText().toString());
                // Call the Parse signup method
                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            finish();
                        }
                    }
                });
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}