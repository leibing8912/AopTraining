package com.yy.live.aoptraining.model;

import android.util.Log;

/**
 * @className: StuModel
 * @classDescription: stu model for aspectj
 * @author: leibing
 * @email: leibing@yy.com
 * @createTime:2017/11/3
 */
public class StuModel {
    // TAG
    private final static String TAG = "AOP StuModel";
    // stu name
    private String stuName;

    static {
        Log.e(TAG, " StuModel static block");
    }

    /**
     * construction
     *
     * @param stuName
     */
    public StuModel(String stuName) {
        this.stuName = stuName;
        Log.e(TAG, " StuModel Construction");
    }

    /**
     * get stu name
     *
     * @return
     */
    public String getStuName() {
        Log.e(TAG, " get stu name");
        return stuName;
    }

    /**
     * set stu name
     *
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
        Log.e(TAG, " set stu name");
    }

    /**
     * create throws
     */
    public void createThrows(){
        Log.e(TAG, " createThrows");
        try {
            String a = null;
            a.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
