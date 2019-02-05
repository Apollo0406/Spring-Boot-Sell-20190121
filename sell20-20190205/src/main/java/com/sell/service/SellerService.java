package com.sell.service;

import com.sell.entity.SellerInfo;

public interface SellerService {

    /**
     * 通过openid查询卖家信息
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
