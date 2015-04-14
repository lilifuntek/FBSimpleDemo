package com.sromku.simple.fb.example;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by vival_000 on 2015/4/13.
 */
public class myBaseActivity extends Activity{
    private ProgressDialog mProgressDialog;

    protected void showDialog() {
        if (mProgressDialog == null) {
            setProgressDialog();
        }
        mProgressDialog.show();
    }

    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void setProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Thinking...");
        mProgressDialog.setMessage("Doing the action...");
    }
}
