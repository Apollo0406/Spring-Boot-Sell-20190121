package com.sell.repository;

import com.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    //按照买家的Openid来查询
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
