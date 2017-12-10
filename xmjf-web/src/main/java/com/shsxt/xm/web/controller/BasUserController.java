package com.shsxt.xm.web.controller;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.exception.ParamsException;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.util.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.server.SessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("user")
public class BasUserController {

    @Autowired
    private IBasUserService basUserService;

    @RequestMapping("queryBasUserById")
    @ResponseBody
    public BasUser queryBasUserById(Integer id){
        return basUserService.queryBasUserById(id);
    }

    /**
     * 注册
     * @param phone
     * @param picCode
     * @param code
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public ResultInfo userRegister(String phone, String picCode, String code, String password, HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        String sessionPicCode = (String)session.getAttribute(P2pConstant.PICTURE_VERIFY_CODE);
        if (StringUtils.isBlank(sessionPicCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("验证码已经失效");
            return resultInfo;
        }
        if (!picCode.equals(sessionPicCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("验证码不匹配");
            return resultInfo;
        }
        //发送验证码时间
        Date sessionTime = (Date)session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME+phone);
        if (null == sessionTime){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("手机验证码已经失效");
            return resultInfo;
        }
        Date currTime = new Date();
        long time = (currTime.getTime()-sessionTime.getTime())/1000;
        if (time>180){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("手机验证码已经失效");
            return resultInfo;
        }
        String sessionCode = (String)session.getAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        if (!sessionCode.equals(code)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("手机验证码不正确");
            return resultInfo;
        }
        try {
            basUserService.saveBasUser(phone,password);
            session.removeAttribute(P2pConstant.PICTURE_VERIFY_CODE);
            session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
            session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME+phone);
        }catch (ParamsException e){
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(P2pConstant.OPS_FAILED_MSG);
        }
        return resultInfo;

    }

    /**
     * 登录
     * @param phone
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("userLogin")
    @ResponseBody
    public ResultInfo userLogin(String phone,String password,HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        try {
            BasUser basUser = basUserService.userLogin(phone,password);
            session.setAttribute("userInfo",basUser);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(P2pConstant.OPS_FAILED_MSG);
        }
        return resultInfo;
    }

    /**
     * 快速登录
     * @param phone
     * @param picCode
     * @param code
     * @param session
     * @return
     */
    @RequestMapping("quickLogin")
    @ResponseBody
    public ResultInfo quickLogin(String phone,String picCode,String code,HttpSession session){
        ResultInfo resultInfo = new ResultInfo();
        String sessionPicCode = (String)session.getAttribute(P2pConstant.PICTURE_VERIFY_CODE);
        if (StringUtils.isBlank(sessionPicCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("验证码已经失效");
            return resultInfo;
        }
        if (!picCode.equals(sessionPicCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("验证码不匹配");
            return resultInfo;
        }
        Date sessionTime = (Date)session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIRE_TIME+phone);
        if (sessionTime == null){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("手机验证码失效");
            return resultInfo;
        }
        Date currenTime = new Date();
        long time = (currenTime.getTime()-sessionTime.getTime())/1000;
        if (time >180){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("手机验证码失效");
            return resultInfo;
        }
        String sessionCode = (String)session.getAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        if (!code.equals(sessionCode)){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg("手机验证码不正确");
            return resultInfo;
        }
        try {
            BasUser basUser = basUserService.quickLogin(phone);
            session.setAttribute("userInfo",basUser);
        } catch (ParamsException e) {
            e.printStackTrace();
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(e.getErrorMsg());
        }catch (Exception e){
            resultInfo.setCode(P2pConstant.OPS_FAILED_CODE);
            resultInfo.setMsg(P2pConstant.OPS_FAILED_MSG);
        }
        return resultInfo;
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("userInfo");
        request.setAttribute("ctx",request.getContextPath());
        return "login";
    }
}
