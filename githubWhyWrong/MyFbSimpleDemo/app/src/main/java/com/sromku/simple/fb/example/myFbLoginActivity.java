package com.sromku.simple.fb.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
//import com.sromku.simple.fb.example.fragments.MainFragment;
import com.sromku.simple.fb.example.utils.Utils;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;

/**
 * Created by vival_000 on 2015/4/13.
 */
public class myFbLoginActivity extends Activity {
    protected static final String TAG = MainActivity.class.getName();

    private SimpleFacebook mSimpleFacebook;

    private Button mButtonLogin;
    private Button mButtonLogout;

    private TextView mTextStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimpleFacebook = SimpleFacebook.getInstance(this);

        // test local language
        Utils.updateLanguage(getApplicationContext(), "en");
        Utils.printHashKey(getApplicationContext());

        setContentView(R.layout.fragment_main);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mButtonLogout = (Button) findViewById(R.id.button_logout);
        mTextStatus = (TextView) findViewById(R.id.text_status);
        setLogin();
        setLogout();


        setUIState();
    }

    /**
     * Login example.
     */
    private void setLogin() {
        // Login listener
        final OnLoginListener onLoginListener = new OnLoginListener() {

            @Override
            public void onFail(String reason) {
                mTextStatus.setText(reason);
                Log.w(TAG, "Failed to login");
            }

            @Override
            public void onException(Throwable throwable) {
                mTextStatus.setText("Exception: " + throwable.getMessage());
                Log.e(TAG, "Bad thing happened", throwable);
            }

            @Override
            public void onThinking() {
                // show progress bar or something to the user while login is
                // happening
                mTextStatus.setText("Thinking...");
            }

            @Override
            public void onLogin() {
                // change the state of the button or do whatever you want
                mTextStatus.setText("Logged in");
                loggedInUIState();
                Intent intent = new Intent(myFbLoginActivity.this, getProfileActivity.class);
                startActivity(intent);
            }

            @Override
            public void onNotAcceptingPermissions(Permission.Type type) {
                mTextStatus.setText("Logged out");
                Toast.makeText(myFbLoginActivity.this, String.format("You didn't accept %s permissions", type.name()), Toast.LENGTH_SHORT).show();
            }
        };

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mSimpleFacebook.login(onLoginListener);
            }
        });
    }

    /**
     * Logout example
     */
    private void setLogout() {
        final OnLogoutListener onLogoutListener = new OnLogoutListener() {

            @Override
            public void onFail(String reason) {
                mTextStatus.setText(reason);
                Log.w(TAG, "Failed to login");
            }

            @Override
            public void onException(Throwable throwable) {
                mTextStatus.setText("Exception: " + throwable.getMessage());
                Log.e(TAG, "Bad thing happened", throwable);
            }

            @Override
            public void onThinking() {
                // show progress bar or something to the user while login is
                // happening
                mTextStatus.setText("Thinking...");
            }

            @Override
            public void onLogout() {
                // change the state of the button or do whatever you want
                mTextStatus.setText("Logged out");
                loggedOutUIState();
            }

        };

        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mSimpleFacebook.logout(onLogoutListener);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
//        if()
//        {
//            Intent intent = new Intent(myFbLoginActivity.this, getProfileActivity.class);
//            startActivity(intent);
//        }


    }

    private void setUIState() {
        if (mSimpleFacebook.isLogin()) {
            loggedInUIState();


        } else {
            loggedOutUIState();
        }
    }

    private void loggedInUIState() {
        mButtonLogin.setEnabled(false);
        mButtonLogout.setEnabled(true);
        //   mExamplesAdapter.setLogged(true);
        mTextStatus.setText("Logged in");
    }

    private void loggedOutUIState() {
        mButtonLogin.setEnabled(true);
        mButtonLogout.setEnabled(false);
        //  mExamplesAdapter.setLogged(false);
        mTextStatus.setText("Logged out");
    }


}

