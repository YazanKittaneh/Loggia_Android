package com.example.l7.project_chatter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends ActionBarActivity {

    private EditText mUserName;
    private EditText mEmailInput;
    private EditText mPassword;
    private EditText mPasswordValidation;
    private Button mSignUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserName = (EditText) findViewById(R.id.User_Name);
        mEmailInput = (EditText) findViewById(R.id.Email_Text);
        mPassword = (EditText) findViewById(R.id.Password_Text);
        mPasswordValidation = (EditText) findViewById(R.id.Password_Validation_);
        mSignUpButton = (Button) findViewById(R.id.Sign_up_button);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SignUpController signUpController = new SignUpController();
                //Toast.makeText(SignUpActivity.this, "jaljglkajg", Toast.LENGTH_SHORT).show();


                if(mPasswordValidation.getText().toString() == mPassword.getText().toString()) {
                    Toast.makeText(SignUpActivity.this, "jaljglkajg", Toast.LENGTH_SHORT).show();
                    signUpController.signUp(mUserName.getText().toString(), mEmailInput.getText().toString(), mPassword.getText().toString());
                }
                else
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
