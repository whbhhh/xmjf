package com.shsxt.xm.server.service;

import com.alibaba.fastjson.JSON;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.service.ISmsService;
import com.shsxt.xm.api.util.AssertUtil;
import com.shsxt.xm.server.constant.TaoBaoConstant;
import com.shsxt.xm.server.enums.SmsType;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class SmsServiceImpl implements ISmsService {
    @Resource
    private IBasUserService basUserService;

    @Override
    public void sendPhoneSms(String phone, String code, Integer type) {
        AssertUtil.isTrue(StringUtils.isBlank(phone), "手机号码不能为空");
        /**
         * 正则判断手机号码是否合法
         */
        AssertUtil.isTrue(!Pattern.matches(TaoBaoConstant.REGEX_MOBILE,phone),"手机号不合法");
        AssertUtil.isTrue(StringUtils.isBlank(code), "手机验证码不能为空");
        AssertUtil.isTrue(null == type, "类型不匹配");
        AssertUtil.isTrue(!type.equals(SmsType.REGISTER.getType()) && !type.equals(SmsType.NOTIFY.getType()),
                "类型不匹配");
        if (type.equals(SmsType.REGISTER.getType())) {
            /**
             * 判断是否已经注册过
             */
            BasUser basUser = basUserService.queryBasUserByPhone(phone);
            AssertUtil.isTrue(basUser != null, "该手机号已经被注册");
            doSend(phone, code,TaoBaoConstant.SMS_TEMATE_CODE_REGISTER);
        }
        if (type.equals(SmsType.NOTIFY.getType())) {
            doSend(phone, code,TaoBaoConstant.SMS_TEMATE_CODE_lOGIN);
        }
    }

    public void doSend(String phone, String code,String template) {
        TaobaoClient client = new DefaultTaobaoClient(TaoBaoConstant.SERVER_URL,
                TaoBaoConstant.APP_KEY,TaoBaoConstant.APP_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType(TaoBaoConstant.SMS_TYPE);
        req.setSmsFreeSignName(TaoBaoConstant.SMS_FREE_SIGN_NAME);
        Map<String,String> map=new HashMap<String,String>();
        map.put("code",code);
        req.setSmsParamString(JSON.toJSONString(map));
        req.setRecNum(phone);
        req.setSmsTemplateCode(template);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            rsp = client.execute(req);
            AssertUtil.isTrue(!rsp.isSuccess(),"短信发送失败,请稍后再试!");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


}
