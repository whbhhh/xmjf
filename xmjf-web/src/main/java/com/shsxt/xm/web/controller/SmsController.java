package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.exception.ParamsException;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.service.ISmsService;
import com.shsxt.xm.api.util.RandomCodesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("sms")
public class SmsController {
    @Resource
    private ISmsService smsService;

    /**
     * 请求手机短信发送
     * @param phone
     * @param picCode
     * @param type
     * @param session
     * @return
     */
    @RequestMapping("sendPhoneSms")
    @ResponseBody
    public ResultInfo sendPhone(String phone, String picCode, Integer type, HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        String sessionPicCode = (String)session.getAttribute(P2pConstant.PICTURE_VERIFY_CODE);
        if (StringUtils.isBlank(sessionPicCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("验证码失效");
            return resultInfo;
        }
        if (!picCode.equals(sessionPicCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("验证码不匹配");
            return resultInfo;
        }
        try {
            String code = RandomCodesUtils.createRandom(true,4);
            System.out.println("code"+code);
           //smsService.sendPhoneSms(phone,code,type);
            //手机验证码 存入session
            session.setAttribute(P2pConstant.PHONE_VERIFY_CODE+phone,code);
            //发送手机验证码时间存入session
            session.setAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME+phone,new Date());
        }catch (ParamsException e){
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(P2pConstant.OPS_FAILED_MSG);
        }
        return resultInfo;
    }
}
