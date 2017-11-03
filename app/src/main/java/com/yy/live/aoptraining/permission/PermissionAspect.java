package com.yy.live.aoptraining.permission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import com.yy.live.aoptraining.AppManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @className: PermissionAspect
 * @classDescription: permission aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
@Aspect
public class PermissionAspect {
    // TAG
    private final static String TAG = "AOP PermissionAspect";

    /**
     * 函数执行内部（采用注解动态权限处理）
     *
     * @param permission
     */
    @Pointcut("execution(@com.yy.live.aoptraining.permission.YPermission * *(..)) && @annotation(permission)")
    public void methodAnnotatedWithMPermission(YPermission permission) {
    }

    /**
     * (函数执行内部（采用注解动态权限处理）)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
     *
     * @param joinPoint
     * @param permission
     * @throws Throwable
     */
    @Around("methodAnnotatedWithMPermission(permission)")
    public void checkPermission(final ProceedingJoinPoint joinPoint, YPermission permission) throws Throwable {
        Log.e(TAG, " checkPermission");
        // 权限
        String permissionStr = permission.value();
        // 模拟权限申请
        if (AppManager.getInstance().currentActivity() != null) {
            new AlertDialog.Builder(AppManager.getInstance().currentActivity()).setTitle("提示")
                    .setMessage(permissionStr)
                    .setNegativeButton("取消", null)
                    .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e(TAG, " checkPermission allow");
                            try {
                                // 继续执行原方法
                                joinPoint.proceed();
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }

                        }
                    }).create().show();
        }
    }
}
