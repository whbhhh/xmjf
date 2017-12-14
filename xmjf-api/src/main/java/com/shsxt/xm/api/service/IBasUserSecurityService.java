package com.shsxt.xm.api.service;

import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.BasUserSecurity;

public interface IBasUserSecurityService {
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);

    /**
     * 用户认证校验
     * @param
     * @return
     */
    public ResultInfo userAuthCheck(Integer userId);

    /**
     * 用户认证
     * @param realName
     * @param idCard
     * @param businessPassword
     * @param confirmPassword
     * @param userId
     */
    public void doUserAuth(String realName,String idCard,String businessPassword,String
                           confirmPassword,Integer userId);
}
