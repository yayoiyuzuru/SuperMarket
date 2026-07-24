package com.lzj.admin.service;

import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.GoodsType;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品类别表单服务类
 * @author TianTian
 * @date 2022/1/19 13:56
 */
public interface GoodsTypeService extends IService<GoodsType> {
	List<TreeDto> queryAllGoodsTypes();

	List<Integer> queryAllSubTypeIdsByTypeId(Integer typeId);
}
