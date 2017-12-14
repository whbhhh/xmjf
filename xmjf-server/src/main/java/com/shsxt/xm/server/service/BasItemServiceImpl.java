package com.shsxt.xm.server.service;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.api.constant.P2pConstant;
import com.shsxt.xm.api.dto.BasItemDto;
import com.shsxt.xm.api.po.BasItem;
import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.service.IBasItemService;
import com.shsxt.xm.api.util.AssertUtil;
import com.shsxt.xm.api.util.PageList;
import com.shsxt.xm.server.db.dao.BasItemDao;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@Service
public class BasItemServiceImpl implements IBasItemService {
    @Resource
    private BasItemDao basItemDao;
    @Override
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery) {
        PageHelper.startPage(basItemQuery.getPageNum(),basItemQuery.getPageSize());
        List<BasItemDto> basItemDtos = basItemDao.queryForPage(basItemQuery);
        if (!CollectionUtils.isEmpty(basItemDtos)){
            for (BasItemDto basItemDto:basItemDtos) {
                if (basItemDto.getItemStatus().equals(1)){
                    //发布时间
                    Date releaseTime = basItemDto.getReleaseTime();
                    long syTime = (releaseTime.getTime()-new Date().getTime())/1000;
                    basItemDto.setSyTime(syTime);
                }
            }
        }
        return new PageList(basItemDtos);
    }

    @Override
    public void updateBasItemStatusToOpen(Integer itemId) {
        AssertUtil.isTrue(basItemDao.queryById(itemId)==null,"更新记录不存在");
        AssertUtil.isTrue(basItemDao.updateBasItemStatusToOpen(itemId)<1, P2pConstant.OPS_FAILED_MSG);
    }

    @Override
    public BasItemDto queryBasItemByItemId(Integer itemId) {
        return basItemDao.queryById(itemId);
    }
}
