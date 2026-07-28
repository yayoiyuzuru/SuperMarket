package com.lzj.admin.service;

import com.lzj.admin.pojo.PurchaseListGoods;
import com.lzj.admin.query.PurchaseListGoodsQuery;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 进货单商品表服务类
 * @author TianTian
 * @date 2022/1/19 13:58
 */
public interface PurchaseListGoodsService extends IService<PurchaseListGoods> {

	Map<String, Object> purchaseListGoodsList(PurchaseListGoodsQuery purchaseListGoodsQuery);

}
