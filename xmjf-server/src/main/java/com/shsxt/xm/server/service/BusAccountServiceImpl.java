package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.constant.YunTongFuConstant;
import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BasUserSecurity;
import com.shsxt.xm.api.po.BusAccount;
import com.shsxt.xm.api.po.BusAccountLog;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.service.IBasUserSecurityService;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.service.IBusAccountService;
import com.shsxt.xm.api.util.AssertUtil;
import com.shsxt.xm.api.util.MD5;
import com.shsxt.xm.api.util.Md5Util;
import com.shsxt.xm.server.db.dao.BasUserSecurityDao;
import com.shsxt.xm.server.db.dao.BusAccountDao;
import com.shsxt.xm.server.db.dao.BusAccountLogDao;
import com.shsxt.xm.server.db.dao.BusAccountRechargeDao;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class BusAccountServiceImpl implements IBusAccountService {
    @Resource
    private BusAccountDao busAccountDao;
    @Resource
    private IBasUserService basUserService;
    @Resource
    private IBasUserSecurityService basUserSecurityService;
    @Resource
    private BusAccountRechargeDao busAccountRechargeDao;
    @Resource
    private BusAccountLogDao busAccountLogDao;
    @Override
    public BusAccount queryBasAccountByUserId(Integer userId) {
        return busAccountDao.queryBusAccountByUserId(userId);
    }

    @Override
    public PayDto addRechargeRequestInfo(BigDecimal amount, String bussinessPassword, Integer userId) {
        checkParams(amount,bussinessPassword,userId);
        /**
         * 构建支付请求参数
         */
        BusAccountRecharge busAccountRecharge=new BusAccountRecharge();
        busAccountRecharge.setAddtime(new Date());
        busAccountRecharge.setFeeAmount(BigDecimal.ZERO);
        String orderNo= com.shsxt.xm.api.util.StringUtils.getOrderNo();
        busAccountRecharge.setOrderNo(orderNo);
        busAccountRecharge.setFeeRate(BigDecimal.ZERO);
        busAccountRecharge.setRechargeAmount(amount);
        busAccountRecharge.setRemark("PC端用户充值");
        busAccountRecharge.setResource("PC端用户充值");
        busAccountRecharge.setStatus(2);
        busAccountRecharge.setType(3);
        busAccountRecharge.setUserId(userId);
        AssertUtil.isTrue(busAccountRechargeDao.insert(busAccountRecharge)<1, P2pConstant.OPS_FAILED_MSG);
        PayDto payDto=new PayDto();
        payDto.setBody("PC端用户充值操作");
        payDto.setOrderNo(orderNo);
        payDto.setSubject("PC端用户充值操作");
        payDto.setTotalFee(amount);
        String md5Sign=buildMd5Sign(payDto);
        payDto.setSign(md5Sign);
        return payDto;
    }

    @Override
    public void updateAccountRecharge(Integer userId, BigDecimal totalFee, String outOrderNo, String sign, String tradeNo, String tradeStatus) {
        AssertUtil.isTrue(userId == null || basUserService.queryBasUserById(userId)==null,"用户未登录");
        AssertUtil.isTrue(totalFee==null||StringUtils.isBlank(outOrderNo)||StringUtils.isBlank(sign)||
        StringUtils.isBlank(tradeNo)||StringUtils.isBlank(tradeStatus),"回调参数异常");
        Md5Util md5Util = new Md5Util();
        String  tempStr= md5Util.encode(outOrderNo+totalFee.toString()+tradeStatus+ YunTongFuConstant.PARTNER+YunTongFuConstant.KEY,null);
        AssertUtil.isTrue(!tempStr.equals(sign),"订单信息异常,请联系客服!");
        AssertUtil.isTrue(!tradeStatus.equals(YunTongFuConstant.TRADE_STATUS_SUCCESS),"订单支付失败!");
        BusAccountRecharge busAccountRecharge=busAccountRechargeDao.queryBusAccountRechargeByOrderNo(outOrderNo);
        AssertUtil.isTrue(null==busAccountRecharge,"订单记录不存在，请联系管理员!");
        AssertUtil.isTrue(busAccountRecharge.getStatus().equals(1),"该订单已支付!");
        AssertUtil.isTrue(busAccountRecharge.getStatus().equals(0),"订单异常，请联系客服!");
        AssertUtil.isTrue(busAccountRecharge.getRechargeAmount().compareTo(totalFee)!=0,"订单异常，请联系客服!");
        busAccountRecharge.setStatus(1);
        busAccountRecharge.setActualAmount(totalFee);
        busAccountRecharge.setAuditTime(new Date());
        AssertUtil.isTrue(busAccountRechargeDao.update(busAccountRecharge)<1,P2pConstant.OPS_FAILED_MSG);
        BusAccount busAccount=busAccountDao.queryBusAccountByUserId(userId);
        busAccount.setCash(busAccount.getCash().add(totalFee));// 设置可提现金额
        busAccount.setTotal(busAccount.getTotal().add(totalFee));// 设置总金额
        busAccount.setUsable(busAccount.getUsable().add(totalFee));
        AssertUtil.isTrue(busAccountDao.update(busAccount)<1,P2pConstant.OPS_FAILED_MSG);
        // 添加账户信息变动操作日志
        BusAccountLog busAccountLog=new BusAccountLog();
        busAccountLog.setAddtime(new Date());
        busAccountLog.setBudgetType(1);// 收入日志
        busAccountLog.setCash(busAccount.getCash());// 设置可提现金额
        busAccountLog.setFrozen(busAccount.getFrozen());
        busAccountLog.setOperMoney(totalFee);// 设置操作金额
        busAccountLog.setOperType("user_recharge_success");
        busAccountLog.setRemark("用户充值");
        busAccountLog.setRepay(busAccount.getRepay());
        busAccountLog.setTotal(busAccount.getTotal());
        busAccountLog.setUsable(busAccount.getUsable());
        busAccountLog.setUserId(userId);
        busAccountLog.setWait(busAccount.getWait());
        AssertUtil.isTrue(busAccountLogDao.insert(busAccountLog)<1,P2pConstant.OPS_FAILED_MSG);
    }

    private String buildMd5Sign(PayDto payDto) {
        StringBuffer arg = new StringBuffer();
        if(!StringUtils.isBlank(payDto.getBody())){
            arg.append("body="+payDto.getBody()+"&");
        }
        arg.append("notify_url="+payDto.getNotifyUrl()+"&");
        arg.append("out_order_no="+payDto.getOrderNo()+"&");
        arg.append("partner="+payDto.getPartner()+"&");
        arg.append("return_url="+payDto.getReturnUrl()+"&");
        arg.append("subject="+payDto.getSubject()+"&");
        arg.append("total_fee="+payDto.getTotalFee().toString()+"&");
        arg.append("user_seller="+payDto.getUserSeller());
        String tempSign= StringEscapeUtils.unescapeJava(arg.toString());
        Md5Util md5Util=new Md5Util();
        return md5Util.encode(tempSign+payDto.getKey(),"");
    }

    /**
     * 基本参数校验
     * @param amount
     * @param bussinessPassword
     * @param userId
     */
    private void checkParams(BigDecimal amount, String bussinessPassword, Integer userId) {
        AssertUtil.isTrue(amount.compareTo(BigDecimal.ZERO)<=0,"充值金额非法!");
        BasUserSecurity basUserSecurity=basUserSecurityService.queryBasUserSecurityByUserId(userId);
        AssertUtil.isTrue(null==basUserSecurity,"用户未登录!");
        AssertUtil.isTrue(StringUtils.isBlank(bussinessPassword),"交易密码不能为空!");
        bussinessPassword= MD5.toMD5(bussinessPassword);
        AssertUtil.isTrue(!bussinessPassword.equals(basUserSecurity.getPaymentPassword()),"交易密码错误!");
    }

}
