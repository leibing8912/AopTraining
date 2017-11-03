package com.yy.live.aoptraining.method;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @className: MethodAspect
 * @classDescription: method aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
@Aspect
public class MethodAspect {
    // TAG
    private final static String TAG = "AOP MethodAspect";
    // before time
    private long beforeTime = 0;
    // after time
    private long afterTime = 0;
    // before joinPoint target
    private String beforeTarget;
    // before joinPoint signature name
    private String beforeSignatureName;
    // after joinPoint target
    private String afterTarget;
    // after joinPoint signature name
    private String afterSignatureName;

    /**
     * 函数被调用
     */
    @Pointcut("call(* com.yy.live.aoptraining.model.UserModel.**(..))")
    public void callMethod() {
    }

    /**
     * 执行(函数被调用)JPoint之前
     *
     * @param joinPoint
     */
    @Before("callMethod()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, " before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        beforeTarget =  joinPoint.getTarget().toString();
        beforeSignatureName = joinPoint.getSignature().getName();
        beforeTime  = System.currentTimeMillis();
    }

    /**
     * 执行（函数被调用）JPoint之后
     *
     * @param joinPoint
     */
    @After("callMethod()")
    public void afterMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, " after->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        afterTarget =  joinPoint.getTarget().toString();
        afterSignatureName = joinPoint.getSignature().getName();
        afterTime = System.currentTimeMillis();
        if (afterTarget != null
                && afterSignatureName != null
                && afterTarget.equals(beforeTarget)
                && afterSignatureName.equals(beforeSignatureName)) {
            long castTime = afterTime - beforeTime;
            Log.e(TAG, " monitor->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName() + "#cost " + castTime + " ms");
        }
    }

//    /**
//     * (函数被调用)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
//     *
//     * @param joinPoint
//     * @throws Throwable
//     */
//    @Around("callMethod()")
//    public void aroundMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, " around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
//        // 执行原代码
//        joinPoint.proceed();
//    }

    /**
     * 替换原方法返回值
     * 注：@Pointcut可以不单独定义方法，直接使用，如下：
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     * @Around("execution(* com.yy.live.aoptraining.model.UserModel.getUserName(..))")
     */
    @Around("execution(* com.yy.live.aoptraining.model.UserModel.getUserName(..))")
    public String aroundGetUserNameMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String originUserName = joinPoint.proceed().toString();
        Log.e(TAG, " origin userName = " + originUserName);
        // 此处可对原方法做偷天换日处理
        return "王五";
    }

    /**
     * 异常处理，用于统计所有出现Exception的点
     * 不支持@After、@Around
     */
    @Before("handler(java.lang.Exception)")
    public void handler() {
        Log.e(TAG, " handler");
    }

    /**
     * 异常退出，用于收集抛出异常的方法信息
     * @AfterThrowing
     * @param throwable
     */
    @AfterThrowing(pointcut = "call(* *..*(..))", throwing = "throwable")
    public void anyFuncThrows(Throwable throwable) {
        Log.e(TAG, " Throwable: ", throwable);
    }
}
