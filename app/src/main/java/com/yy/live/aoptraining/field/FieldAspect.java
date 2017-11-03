package com.yy.live.aoptraining.field;

import android.util.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @className: FieldAspect
 * @classDescription: field aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
@Aspect
public class FieldAspect {
    // TAG
    private static final String TAG = "AOP FieldAspect";

    /**
     * 读变量
     */
    @Pointcut("get(String com.yy.live.aoptraining.model.UserModel.userName)")
    public void getField() {
    }

//    /**
//     * 执行(读变量)JPoint之前
//     *
//     * @param joinPoint
//     */
//    @Before("getField()")
//    public void beforeFieldGet(JoinPoint joinPoint) {
//        Log.e(TAG, " before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }
//
//    /**
//     * 执行（读变量）JPoint之后
//     *
//     * @param joinPoint
//     */
//    @After("getField()")
//    public void afterFieldGet(JoinPoint joinPoint) {
//        Log.e(TAG, " after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//    }

    /**
     * (读变量)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("getField()")
    public String aroundFieldGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行原代码
        Object obj = joinPoint.proceed();
        String userName = obj.toString();
        Log.e(TAG, " around->userName = " + userName);
        // 可在此处偷天换日更改类原有属性的值
        return "李四";
    }
}
