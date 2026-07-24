package com.lzj.admin.service.impl;

import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.mapper.GoodsMapper;
import com.lzj.admin.service.CustomerReturnListGoodsService;
import com.lzj.admin.service.GoodsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.service.GoodsTypeService;
import com.lzj.admin.service.SaleListGoodsService;
import com.lzj.admin.utils.PageResultUtil;

import org.springframework.stereotype.Service;

import java.util.Map;

import javax.annotation.Resource;

/**
 * 商品表实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private SaleListGoodsService saleListGoodsService;

    @Resource
    private CustomerReturnListGoodsService customerReturnListGoodsService;

    @Override
    public Map<String, Object> goodsList(GoodsQuery goodsQuery) {
	    IPage<Goods> page = new Page<Goods>(goodsQuery.getPage(),goodsQuery.getLimit());
	    
	    if(null != goodsQuery.getTypeId()) {
	    	goodsQuery.setTypeIds(goodsTypeService.queryAllSubTypeIdsByTypeId(goodsQuery.getTypeId()));
	    }
	    
	    page = this.baseMapper.queryGoodsByParams (page,goodsQuery);
	    return PageResultUtil.setResult(page.getTotal(),page.getRecords());
    }

}
