package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.model.ResultInfo;
import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.api.po.BasUserSecurity;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.util.AssertUtil;
import com.shsxt.xm.api.util.MD5;
import com.shsxt.xm.server.db.dao.BasUserDao;
import com.shsxt.xm.server.db.dao.BasUserSecurityDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BasUserSecurityServiceImpl implements IBasUserSecurityService {
    @Resource
    private BasUserSecurityDao basUserSecurityDao;
    @Resource
    private IBasUserService basUserService;
    @Override
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId) {
        return basUserSecurityDao.queryBasUserSecurityByUserId(userId);
    }

    /**
     * 用户认证校验
     * @param
     * @return
     */
    @Override
    public ResultInfo userAuthCheck(Integer userId) {
        ResultInfo resultInfo = new ResultInfo();
        BasUserSecurity basUserSecurity = basUserSecurityDao.queryBasUserSecurityByUserId(userId);
        if (basUserSecurity.getRealnameStatus().equals(0)){
            resultInfo.setCode(301);
            resultInfo.setMsg("用户未进行实名认证");
        }
        if (basUserSecurity.getRealnameStatus().equals(1)){
            resultInfo.setCode(P2pConstant.OPS_SUCCESS_CODE);
            resultInfo.setMsg("该用户已认证");
        }
        if (basUserSecurity.getRealnameStatus().equals(2)){
            resultInfo.setCode(302);
            resultInfo.setMsg("认证申请已提交,正在认证中");
        }
        return resultInfo;
    }

    /**
     * 执行认证
     * @param realName
     * @param idCard
     * @param businessPassword
     * @param confirmPassword
     * @param userId
     */
    @Override
    public void doUserAuth(String realName, String idCard, String businessPassword, String confirmPassword, Integer userId) {
        AssertUtil.isTrue(null == userId || userId == 0 ||basUserService.queryBasUserById(userId)==null,
                "用户不存在");
        AssertUtil.isTrue(StringUtils.isBlank(idCard),"身份证号不能为空");
        AssertUtil.isTrue(idCard.length()!=18,"身份证号长度不合法");
        AssertUtil.isTrue(StringUtils.isBlank(realName),"真实姓名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword)||StringUtils.isBlank(businessPassword)
        ||!businessPassword.equals(confirmPassword),"密码输入错误");
        BasUserSecurity basUserSecurity = basUserSecurityDao.queryBasUserSecurityByUserId(userId);
        basUserSecurity.setPaymentPassword(MD5.toMD5(businessPassword));
        basUserSecurity.setRealname(realName);
        basUserSecurity.setIdentifyCard(idCard);
        basUserSecurity.setRealnameStatus(1);
        AssertUtil.isTrue(basUserSecurityDao.update(basUserSecurity)<1,P2pConstant.OPS_FAILED_MSG);
    }
}
