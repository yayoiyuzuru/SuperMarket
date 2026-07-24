package com.lzj.admin.service.impl;

import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.mapper.GoodsTypeMapper;
import com.lzj.admin.service.GoodsTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * 商品表类型实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

	@Override
	public List<TreeDto> queryAllGoodsTypes() {
		return this.baseMapper.queryAllGoodsTypes();
	}

	@Override
	public List<Integer> queryAllSubTypeIdsByTypeId(Integer typeId) {
		GoodsType goodsType = this.getById(typeId);
		if (goodsType.getpId()==-1) {
			return this.list().stream().map(GoodsType::getId).collect(Collectors.toList());
		}
		
		List<Integer> result = new ArrayList<Integer>();
		result.add(typeId);
		return getSubTypeIds(typeId,result);
	}

	private List<Integer> getSubTypeIds(Integer typeId, List<Integer> result) {
		List<GoodsType> goodsTypes = this.baseMapper.selectList(new QueryWrapper<GoodsType>().eq("p_id", typeId));;
		if (CollectionUtils.isNotEmpty(goodsTypes)) {
			goodsTypes.forEach(gt->{
				result.add(gt.getId());
				getSubTypeIds(gt.getId(), result);
			});
		}
		return result;
	}

}
