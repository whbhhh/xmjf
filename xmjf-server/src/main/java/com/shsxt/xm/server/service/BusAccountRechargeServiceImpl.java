package com.shsxt.xm.server.service;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.api.po.BusAccountRecharge;
import com.shsxt.xm.api.query.AccountRechargeQuery;
import com.shsxt.xm.api.service.IBusAccountRechargeService;
import com.shsxt.xm.api.util.PageList;
import com.shsxt.xm.server.db.dao.BusAccountRechargeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lp on 2017/12/14.
 */
@Service
public class BusAccountRechargeServiceImpl implements IBusAccountRechargeService {
    @Resource
    private BusAccountRechargeDao busAccountRechargeDao;
    @Override
    public PageList queryRechargeRecodesByParams(AccountRechargeQuery accountRechargeQuery) {
        PageHelper.startPage(accountRechargeQuery.getPageNum(),accountRechargeQuery.getPageSize());
       List<BusAccountRecharge> busAccountRecharges= busAccountRechargeDao.queryForPage(accountRechargeQuery);
        return new PageList(busAccountRecharges);
    }
}
