package com.shsxt.xm.api.model;


import com.shsxt.xm.api.constant.P2pConstant;

/**
 * Created by lp on 2017/12/8.
 */
public class ResultInfo {
    private Integer code= P2pConstant.OPS_SUCCESS_CODE;
    private String msg=P2pConstant.OPS_SUCCESS_MSG;
    private Object result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
