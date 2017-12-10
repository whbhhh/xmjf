package com.shsxt.xm.server.service;

import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.po.*;
import com.shsxt.xm.api.service.IBasUserService;
import com.shsxt.xm.api.util.AssertUtil;
import com.shsxt.xm.api.util.DateUtils;
import com.shsxt.xm.api.util.MD5;
import com.shsxt.xm.api.util.RandomCodesUtils;
import com.shsxt.xm.server.db.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BasUserServiceImpl implements IBasUserService {
    @Resource
    private BasUserDao basUserDao;

    @Resource
    private SysLogDao sysLogDao;

    @Resource
    private BasExperiencedGoldDao basExperiencedGoldDao;

    @Resource
    private BusUserStatDao busUserStatDao;

    @Resource
    private BusIncomeStatDao busIncomeStatDao;

    @Resource
    private BusUserIntegralDao busUserIntegralDao;

    @Resource
    private BusAccountDao busAccountDao;

    @Resource
    private BasUserSecurityDao basUserSecurityDao;

    @Resource
    private BasUserInfoDao basUserInfoDao;


    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    @Override
    public BasUser queryBasUserById(Integer id) {
        return basUserDao.queryBasUserById(id);
    }

    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    @Override
    public BasUser queryBasUserByPhone(String phone) {
        return basUserDao.queryBasUserByPhone(phone);
    }

    public void saveBasUser(String phone,String password){
        Integer userId = initBasUser(phone,password);
        initBasUserInfo(userId);
        initBasUserSecurity(userId);
        initBusAccount(userId);
        initBusUserIntegral(userId);
        initBusIncomeStat(userId);
        initBusUserStat(userId);
        initBasExperiencedGold(userId);
        initSysLog(userId);
    }

    /**
     * 普通登录
     * @param phone
     * @param password
     * @return
     */
    @Override
    public BasUser userLogin(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空");
        BasUser basUser = basUserDao.queryBasUserByPhone(phone);
        AssertUtil.isTrue(basUser == null,"该用户不存在");
        String salt = basUser.getSalt();
        password = MD5.toMD5(password+salt);
        AssertUtil.isTrue(!password.equals(basUser.getPassword()),"密码错误");
        return basUser;
    }

    /**
     * 快速登录
     * @param phone
     * @return
     */
    public BasUser quickLogin(String phone){
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        BasUser basUser = basUserDao.queryBasUserByPhone(phone);
        AssertUtil.isTrue(basUser == null,"用户不存在");
        return basUser;
    }

    /**
     * 初始化用户记录表
     * @param userId
     */
    private void initSysLog(Integer userId) {
        SysLog sysLog = new SysLog();
        sysLog.setAddtime(new Date());
        sysLog.setCode("REGISTER");
        sysLog.setOperating("用户注册");
        sysLog.setResult(1);
        sysLog.setUserId(userId);
        sysLog.setType(4);
        AssertUtil.isTrue(sysLogDao.insert(sysLog)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 初始化体验金表
     * @param userId
     */
    private void initBasExperiencedGold(Integer userId) {
        BasExperiencedGold basExperiencedGold = new BasExperiencedGold();
        basExperiencedGold.setUserId(userId);
        basExperiencedGold.setAddtime(new Date());
        basExperiencedGold.setGoldName("注册体验金");
        basExperiencedGold.setAmount(BigDecimal.valueOf(2888L));
        basExperiencedGold.setStatus(2);
        basExperiencedGold.setUsefulLife(3);
        basExperiencedGold.setWay("register");
        basExperiencedGold.setExpiredTime(DateUtils.addTime(1,new Date(),30));
        AssertUtil.isTrue(basExperiencedGoldDao.insert(basExperiencedGold)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 初始化用户统计表
     * @param userId
     */
    private void initBusUserStat(Integer userId) {
        BusUserStat busUserStat = new BusUserStat();
        busUserStat.setUserId(userId);
        AssertUtil.isTrue(busUserStatDao.insert(busUserStat)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 初始化收益表
     * @param userId
     */
    private void initBusIncomeStat(Integer userId) {
        BusIncomeStat busIncomeStat = new BusIncomeStat();
        busIncomeStat.setUserId(userId);
        AssertUtil.isTrue(busIncomeStatDao.insert(busIncomeStat)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 初始化用户积分表
     * @param userId
     */
    private void initBusUserIntegral(Integer userId) {
        BusUserIntegral busUserIntegral = new BusUserIntegral();
        busUserIntegral.setUsable(0);
        busUserIntegral.setUserId(userId);
        busUserIntegral.setTotal(0);
        AssertUtil.isTrue(busUserIntegralDao.insert(busUserIntegral)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 初始化账户表
     * @param userId
     */
    private void initBusAccount(Integer userId) {
        BusAccount busAccount =  new BusAccount();
        busAccount.setUserId(userId);
        busAccount.setCash(BigDecimal.ZERO);
        AssertUtil.isTrue(busAccountDao.insert(busAccount)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     *初始化用户安全表
     * @param userId
     */
    private void initBasUserSecurity(Integer userId) {
        BasUserSecurity basUserSecurity = new BasUserSecurity();
        basUserSecurity.setUserId(userId);
        basUserSecurity.setPhoneStatus(1);
        AssertUtil.isTrue(basUserSecurityDao.insert(basUserSecurity)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 初始化用户信息扩展表
     * @param userId
     */
    private void initBasUserInfo(Integer userId) {
        BasUserInfo basUserInfo = new BasUserInfo();
        basUserInfo.setUserId(userId);
        String investCode = RandomCodesUtils.createRandom(false,6);
        basUserInfo.setInviteCode(investCode);
        AssertUtil.isTrue(basUserInfoDao.insert(basUserInfo)<1,P2pConstant.OPS_FAILED_MSG);
    }

    /**
     * 增加注册用户
     * @param phone
     * @param password
     * @return
     */
    private Integer initBasUser(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空");
        AssertUtil.isTrue(basUserDao.queryBasUserByPhone(phone) != null,"该手机号已经注册");
        BasUser basUser = new BasUser();
        basUser.setAddtime(new Date());
        basUser.setMobile(phone);
        String salt = RandomCodesUtils.createRandom(false,4);
        basUser.setSalt(salt);
        basUser.setPassword(MD5.toMD5(password+salt));
        basUser.setReferer("PC");
        basUser.setStatus(1);
        basUser.setType(1);
        AssertUtil.isTrue(basUserDao.insert(basUser)<1, P2pConstant.OPS_FAILED_MSG);
        Integer userId = basUser.getId();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        basUser.setUsername("SXT_P2P"+year+userId);
        AssertUtil.isTrue(basUserDao.update(basUser)<1,P2pConstant.OPS_FAILED_MSG);
        return userId;
    }
}
