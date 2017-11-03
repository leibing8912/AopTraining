package com.yy.live.aoptraining.permission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import com.yy.live.aoptraining.BaseActivity;
import com.yy.live.aoptraining.R;
import java.io.File;

/**
 * @className: PermissionActivity
 * @classDescription: apply for permission page
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class PermissionActivity extends BaseActivity implements View.OnClickListener{
    // TAG
    private final static String TAG = "AOP PermissionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        // onClick
        findViewById(R.id.btn_check_permission).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickId = view.getId();
        switch (clickId){
            case R.id.btn_check_permission:
                // 检查权限
                checkPermission();
                break;
        }
    }

    /**
     * 检查权限
     */
    @YPermission(value = Manifest.permission.CAMERA)
    private void checkPermission() {
        Log.e(TAG, " checkPermission");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(getExternalCacheDir() + "photo.jpg")));
        startActivity(intent);
    }
}
