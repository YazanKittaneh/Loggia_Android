package com.example.l7.project_chatter.Authentication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.l7.project_chatter.R;


public class SignUpActivity extends ActionBarActivity {

    private EditText mUserName;
    private EditText mEmailInput;
    private EditText mPassword;
    private EditText mPasswordValidation;
    private SignUpController mSignUpController;
    private static final String TAG = SignUpActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mUserName = (EditText) findViewById(R.id.User_Name);
        mEmailInput = (EditText) findViewById(R.id.Email_Text);
        mPassword = (EditText) findViewById(R.id.Password_Text);
        mPasswordValidation = (EditText) findViewById(R.id.Password_Validation);
        mSignUpController = new SignUpController();
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


    /**
     * OnClick method to
     * @param view
     */
    public void signUp(View view)
    {
        if(mPasswordValidation.getText().toString().compareTo(mPassword.getText().toString()) == 0) {
            if(mSignUpController.signUp(mUserName.getText().toString(), mEmailInput.getText().toString(), mPassword.getText().toString()))
                Log.d(TAG, "Worked!");
            else
                Log.d(TAG, ":c didn't work");
        }
        else
            Log.d(TAG, "Passwords didn't match");
    }

}
