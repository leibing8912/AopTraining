package com.yy.live.aoptraining;

import android.os.Bundle;
import android.view.View;
import com.yy.live.aoptraining.constructor.ConstructorActivity;
import com.yy.live.aoptraining.field.FieldActivity;
import com.yy.live.aoptraining.method.MethodActivity;
import com.yy.live.aoptraining.permission.PermissionActivity;

/**
 * @className: MainActivity
 * @classDescription: aop training main page
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // onclick
        findViewById(R.id.btn_constructor).setOnClickListener(this);
        findViewById(R.id.btn_filed).setOnClickListener(this);
        findViewById(R.id.btn_method).setOnClickListener(this);
        findViewById(R.id.btn_permission).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickId = view.getId();
        switch (clickId){
            case R.id.btn_constructor:
                // 构造函数AOP
                turnToTargetActivity(ConstructorActivity.class);
                break;
            case R.id.btn_filed:
                // 属性AOP
                turnToTargetActivity(FieldActivity.class);
                break;
            case R.id.btn_method:
                // 方法AOP
                turnToTargetActivity(MethodActivity.class);
                break;
            case R.id.btn_permission:
                // 权限AOP
                turnToTargetActivity(PermissionActivity.class);
                break;
            default:
                break;
        }
    }
}
