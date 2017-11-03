package com.yy.live.aoptraining.constructor;

import android.os.Bundle;

import com.yy.live.aoptraining.BaseActivity;
import com.yy.live.aoptraining.R;
import com.yy.live.aoptraining.model.UserModel;

/**
 * @className: ConstructorActivity
 * @classDescription: try to verify constructor for aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class ConstructorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructor);
        // create a UserModel
        UserModel userModel = new UserModel("张三");
//        StuModel stuModel = new StuModel("李四");
    }
}
