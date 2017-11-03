package com.yy.live.aoptraining.model;

import android.util.Log;

/**
 * @className: UserModel
 * @classDescription: user model for aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class UserModel {
    // TAG
    private final static String TAG = "AOP UserModel";
    // user name
    private String userName;

    static {
        Log.e(TAG, " UserModel static block");
    }

    /**
     * construction
     *
     * @param userName
     */
    public UserModel(String userName) {
        this.userName = userName;
        Log.e(TAG, " UserModel Construction");
    }

    /**
     * get user name
     *
     * @return
     */
    public String getUserName() {
        Log.e(TAG, " get user name");
        return this.userName;
    }

    /**
     * set user name
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
        Log.e(TAG, " set user name");
    }

    /**
     * work
     */
    public void work() {
        Log.e(TAG, " work");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * create throws
     */
    public void createThrows(){
        Log.e(TAG, " createThrows");
        try {
            Integer.parseInt("abc");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
