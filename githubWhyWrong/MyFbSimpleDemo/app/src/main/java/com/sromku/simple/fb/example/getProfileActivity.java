package com.sromku.simple.fb.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.actions.Cursor;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.example.utils.Utils;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.listeners.OnPublishListener;

import java.util.List;

/**
 * Created by vival_000 on 2015/4/13.
 */
public class getProfileActivity  extends myBaseActivity {

    private TextView mResult;
    private ProfilePictureView profilePictureView;

    private Button mButton,mButton2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_example_action);
        mResult = (TextView) findViewById(R.id.result);
        profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);

        profilePictureView.setCropped(true);
        SimpleFacebook.getInstance().getProfile(new OnProfileListener() {

            @Override
            public void onThinking() {
                showDialog();
            }

            @Override
            public void onException(Throwable throwable) {
                hideDialog();
                mResult.setText(throwable.getMessage());
            }

            @Override
            public void onFail(String reason) {
                hideDialog();
                mResult.setText(reason);
                Log.i("reson",reason);
            }

            @Override
            public void onComplete(Profile response) {
                hideDialog();
                profilePictureView.setProfileId(response.getId());
                String str="我是ID: "+response.getId();
                str=str+"\n"+"我是email: "+response.getEmail();
                str=str+"\n"+"我是namel: "+response.getName();
//                str=str+"\n"+"我是pucture: "+response.getPicture();
//                str = str+"\n"+Utils.toHtml(response);
                mResult.setText(str);

//                Log.i("pucture",response.getPicture());

//                Log.i("reson",reason);
            }
        });
        findViewById(R.id.load_more).setVisibility(View.GONE);
        mButton = (Button) findViewById(R.id.button);
        mButton.setText("分享");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Feed feed = new Feed.Builder()
                        .setMessage("Clone it out...")
                        .setName("Simple Facebook SDK for Android")
                        .setCaption("Code less, do the same.")
                        .setDescription("Login, publish feeds and stories, invite friends and more....")
                        .setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
                        .setLink("https://github.com/sromku/android-simple-facebook")
                        .build();

                SimpleFacebook.getInstance().publish(feed, true, new OnPublishListener() {

                    @Override
                    public void onException(Throwable throwable) {
                        mResult.setText(throwable.getMessage());
                    }

                    @Override
                    public void onFail(String reason) {
                        mResult.setText(reason);
                    }

                    @Override
                    public void onComplete(String response) {
                        mResult.setText(response);
                    }
                });

            }
        });

        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setText("分享圖片");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bitmap bitmap = Utils.takeScreenshot(getProfileActivity.this);

                // create Photo instance and add some properties
                Photo photo = new Photo.Builder()
                        .setImage(bitmap)
                        .setPlace("110619208966868")
                        .build();

                SimpleFacebook.getInstance().publish(photo, true, new OnPublishListener() {

                    @Override
                    public void onException(Throwable throwable) {
                        hideDialog();
                        mResult.setText(throwable.getMessage());
                    }

                    @Override
                    public void onFail(String reason) {
                        hideDialog();
                        mResult.setText(reason);
                    }

                    @Override
                    public void onThinking() {
                        showDialog();
                    }

                    @Override
                    public void onComplete(String response) {
                        hideDialog();
                        mResult.setText(response);
                    }
                });

            }
        });
    }
}
