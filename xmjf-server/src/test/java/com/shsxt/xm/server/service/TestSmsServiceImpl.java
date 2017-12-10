package com.shsxt.xm.server.service;

import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.service.ISmsService;
import org.junit.Test;

import javax.annotation.Resource;

public class TestSmsServiceImpl extends TestBase {
    @Resource
    private ISmsService smsService;
    @Test
    public void test(){
        smsService.sendPhoneSms("111","222",1);
    }

}
