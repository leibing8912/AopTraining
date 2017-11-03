package com.yy.live.aoptraining.constructor;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @className: ConstructorAspect
 * @classDescription: constructor aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
@Aspect
public class ConstructorAspect {
    // TAG
    private final static String TAG = "AOP ConstructorAspect";

    /**
     * 构造函数被调用
     */
    @Pointcut("call(com.yy.live.aoptraining.model..*.new(..))")
    public void callConstructor() {
    }

    /**
     * 执行(构造函数被调用)JPoint之前
     *
     * @param joinPoint
     */
    @Before("callConstructor()")
    public void beforeConstructorCall(JoinPoint joinPoint) {
        Log.e(TAG, " before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }

    /**
     * 执行（构造函数被调用）JPoint之后
     *
     * @param joinPoint
     */
    @After("callConstructor()")
    public void afterConstructorCall(JoinPoint joinPoint) {
        Log.e(TAG, " after->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }

//    /**
//     * 构造函数执行内部
//     */
//    @Pointcut("execution(com.yy.live.aoptraining.model..*.new(..))")
//    public void executionConstructor() {}
//
//    /**
//     * 执行(构造函数执行内部)JPoint之前
//     * @param joinPoint
//     */
//    @Before("executionConstructor()")
//    public void beforeConstructorExecution(JoinPoint joinPoint) {
//        Log.e(TAG, " before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }
//
//    /**
//     * 执行（构造函数执行内部）JPoint之后
//     * @param joinPoint
//     */
//    @After("executionConstructor()")
//    public void afterConstructorExecution(JoinPoint joinPoint) {
//        Log.e(TAG, " after->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//    }

//    /**
//     * (构造函数执行内部)替换原来的代码，如果要执行原来的代码，需使用joinPoint.proceed()，不能和Before、After一起使用
//     * @param joinPoint
//     * @throws Throwable
//     */
//    @Around("executionConstructor()")
//    public void aroundConstructorExecution(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.e(TAG, " around->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
//        // 执行原代码
//        joinPoint.proceed();
//    }
}
