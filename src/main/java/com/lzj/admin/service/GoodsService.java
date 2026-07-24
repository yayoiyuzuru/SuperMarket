package com.lzj.admin.service;

import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品表服务
 * @author TianTian
 * @date 2022/1/19 13:55
 */
public interface GoodsService extends IService<Goods> {
	Map<String, Object> goodsList(GoodsQuery goodsQuery);
}
