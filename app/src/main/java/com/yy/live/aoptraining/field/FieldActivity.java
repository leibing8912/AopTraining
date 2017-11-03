package com.yy.live.aoptraining.field;

import android.os.Bundle;
import android.util.Log;
import com.yy.live.aoptraining.BaseActivity;
import com.yy.live.aoptraining.R;
import com.yy.live.aoptraining.model.UserModel;

/**
 * @className: FieldActivity
 * @classDescription: try to verify field(write or read) for aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class FieldActivity extends BaseActivity {
    // TAG
    private final static String TAG = "AOP FieldActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        UserModel model = new UserModel("张三");
        String userName = model.getUserName();
        Log.e(TAG, " userName = " + userName);
    }
}
