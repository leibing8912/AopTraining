package com.yy.live.aoptraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @className: BaseActivity
 * @classDescription: activity基类
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 入栈
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        // 出栈
        AppManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 跳转到目标页面
     *
     * @param targetCls
     */
    protected void turnToTargetActivity(Class targetCls) {
        Intent intent = new Intent();
        intent.setClass(this, targetCls);
        startActivity(intent);
    }
}
