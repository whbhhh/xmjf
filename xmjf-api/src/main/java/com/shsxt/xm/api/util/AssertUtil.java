package com.shsxt.xm.api.util;

import com.shsxt.xm.api.exception.ParamsException;

public class AssertUtil {
    public static void isTrue(boolean flag,String errorMsg){
        if (flag){
            throw new ParamsException(errorMsg);
        }
    }

    public static void isTrue(boolean flag,String errorMsg,Integer errorcode){
        if (flag){
            throw new ParamsException(errorcode,errorMsg);
        }
    }

}
