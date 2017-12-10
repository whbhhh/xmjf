package com.shsxt.xm.api.service;

import com.shsxt.xm.api.query.BasItemQuery;
import com.shsxt.xm.api.util.PageList;

public interface IBasItemService {
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery);
}
