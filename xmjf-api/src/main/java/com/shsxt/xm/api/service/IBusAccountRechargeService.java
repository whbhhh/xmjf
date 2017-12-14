package com.shsxt.xm.api.service;

import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.util.PageList;

/**
 * Created by lp on 2017/12/14.
 */
public interface IBusAccountRechargeService {
    public PageList queryRechargeRecodesByParams(AccountRechargeQuery accountRechargeQuery);
}
