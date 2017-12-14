package com.shsxt.xm.api.service;

import com.shsxt.xm.api.dto.PayDto;
import com.shsxt.xm.api.po.BusAccount;

import java.math.BigDecimal;

public interface IBusAccountService {
    public BusAccount queryBasAccountByUserId(Integer userId);
    public PayDto addRechargeRequestInfo(BigDecimal amount, String bussinessPassword, Integer userId);
    public void updateAccountRecharge(Integer userId,BigDecimal totalFee,String outOrderNo,String sign,
                                      String tradeNo,String tradeStatus);
}
