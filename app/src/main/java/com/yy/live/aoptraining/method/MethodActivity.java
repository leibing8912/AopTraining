package com.yy.live.aoptraining.method;

import android.os.Bundle;
import android.util.Log;
import com.yy.live.aoptraining.BaseActivity;
import com.yy.live.aoptraining.R;
import com.yy.live.aoptraining.model.StuModel;
import com.yy.live.aoptraining.model.UserModel;

/**
 * @className: MethodActivity
 * @classDescription: method aop page
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class MethodActivity extends BaseActivity {
    // TAG
    private final static String TAG = "AOP MethodActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method);
        UserModel model = new UserModel("张三");
        model.work();
        String userName = model.getUserName();
        Log.e(TAG, " userName = " + userName);
        model.createThrows();
        StuModel stuModel = new StuModel("六六");
        stuModel.createThrows();
    }
}
