package com.lzj.admin.service;

import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.GoodsType;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品类别表单服务类
 * @author TianTian
 * @date 2022/1/19 13:56
 */
public interface GoodsTypeService extends IService<GoodsType> {
	Map<String, Object> goodsTypeList();

	List<Integer> queryAllSubTypeIdsByTypeId(Integer typeId);
	
	List<TreeDto> queryAllGoodsTypes (Integer typeId);
	
	void saveGoodsType(GoodsType goodsType);
	
	void deleteGoodsType(Integer id);
}
