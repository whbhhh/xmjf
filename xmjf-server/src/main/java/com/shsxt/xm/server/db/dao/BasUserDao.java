package com.shsxt.xm.server.db.dao;

import com.shsxt.xm.api.po.BasUser;
import com.shsxt.xm.server.base.BaseDao;
import org.apache.ibatis.annotations.Param;

public interface BasUserDao extends BaseDao<BasUser>{
    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    public BasUser queryBasUserById(@Param("id") Integer id);

    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    public BasUser queryBasUserByPhone(@Param("phone") String phone);
}
