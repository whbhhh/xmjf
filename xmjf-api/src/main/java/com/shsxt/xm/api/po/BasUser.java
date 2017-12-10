package com.shsxt.xm.api.po;

import java.io.Serializable;
import java.util.Date;

public class BasUser implements Serializable{
    private static final long serialVersionUID = -1861609083349457782L;
    /**
     * id
     * 主键
     */
    private Integer id;

    /**
     * username
     * 
     */
    private String username;

    /**
     * password
     * 密码	
     */
    private String password;

    /**
     * salt
     * 密码校验 	password+salt
     */
    private String salt;

    /**
     * status
     * 状态	0-不启用 1-启用
     */
    private Integer status;

    /**
     * mobile
     * 电话	
     */
    private String mobile;

    /**
     * email
     * 邮箱	
     */
    private String email;

    /**
     * type
     * 用户类型:1-投资用户2-借款用户3-借款企业
     */
    private Integer type;

    /**
     * last_login_ip
     * 上次登录ip	
     */
    private String lastLoginIp;

    /**
     * last_login_time
     * 上次登录时间	
     */
    private Date lastLoginTime;

    /**
     * referer
     * 
     */
    private String referer;

    /**
     * addtime
     * 添加时间	
     */
    private Date addtime;

    /**
     * addip
     * 添加ip
     */
    private String addip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }
}