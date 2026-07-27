package com.lzj.admin.service.impl;

import com.lzj.admin.pojo.Goods;
import com.lzj.admin.query.GoodsQuery;
import com.lzj.admin.mapper.GoodsMapper;
import com.lzj.admin.service.CustomerReturnListGoodsService;
import com.lzj.admin.service.GoodsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzj.admin.service.GoodsTypeService;
import com.lzj.admin.service.SaleListGoodsService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;

import org.apache.commons.lang3.StringUtils;
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

    @Override
    public String genGoodsCode() {
    	String maxGoodsCode = this.baseMapper.selectOne(new QueryWrapper<Goods>().select("max(code) as code")).getCode();
    	if(StringUtils.isNotEmpty(maxGoodsCode)){
    		Integer code = Integer.valueOf(maxGoodsCode)+1;
    		String codes = code.toString();
    		int length = codes.length();
    		for (int i = 4; i > length; i--) {
    			codes = "0"+codes;
    		}
    		return codes;
    	} else {
    		return "0001";
    	}
    }
    
	@Override
	public void saveGoods(Goods goods) {
		AssertUtil.isTrue(StringUtils.isBlank(goods.getName()), "请指商品名称!");
		AssertUtil.isTrue(null == goods.getTypeId(), "请指商品类别!");
		AssertUtil.isTrue(StringUtils.isBlank(goods.getUnit()), "请指商品单位!");
		goods.setCode(genGoodsCode());
		goods.setInventoryQuantity(0);
		goods.setState(0);
		goods.setLastPurchasingPrice(0F);
		goods.setIsDel(0);
		System.out.println(goods);
		AssertUtil.isTrue(!(this.save(goods)), "记录添加失败!");	
	}
	
	@Override
	public void updateGoods (Goods goods) {
		AssertUtil.isTrue(StringUtils.isBlank(goods.getName()), "请指定商品名称!");
		AssertUtil.isTrue(null == goods.getTypeId(), "请指定商品类别!");
		AssertUtil.isTrue(StringUtils.isBlank(goods.getUnit()), "请指商品单位!");
		AssertUtil.isTrue(!(this.updateById(goods)), "记录更新失败!");
	}
	
	@Override
	public void deleteGoods(Integer id) {
		Goods goods =this.getById(id);
		AssertUtil.isTrue(null == goods,"待删除的商品记录不存在!");
		AssertUtil.isTrue(goods.getState() == 1,  "该商品已经期初入库，不能删除!");
		AssertUtil.isTrue(goods.getState() == 2,  "该商品已经单据，不能删除!");
		goods.setIsDel(1);
		AssertUtil.isTrue(!(this.updateById(goods)), "商品删除失败!");
	}
}
