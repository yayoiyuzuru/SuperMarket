package com.lzj.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;

public interface GoodsMapper extends BaseMapper<Goods> {
	IPage<Goods> queryGoodsByParams(IPage<Goods> page,@Param("goodsQuery") GoodsQuery goodsQuery);
}
