package com.shsxt.xm.api.service;

import com.shsxt.xm.api.po.BasUser;

public interface IBasUserService {
    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    public BasUser queryBasUserById(Integer id);

    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    public BasUser queryBasUserByPhone(String phone);

    /**
     * 保存用户记录
     * @param phone
     * @param password
     */
    public  void saveBasUser(String phone,String password);

    /**
     * 登录
     */
    public BasUser userLogin(String phone,String password);

    /**
     * 快速登录
     * @param phone
     * @return
     */
    public BasUser quickLogin(String phone);
}
