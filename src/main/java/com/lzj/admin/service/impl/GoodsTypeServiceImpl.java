package com.lzj.admin.service.impl;

import com.lzj.admin.pojo.GoodsType;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.mapper.GoodsTypeMapper;
import com.lzj.admin.service.GoodsTypeService;
import com.lzj.admin.utils.AssertUtil;
import com.lzj.admin.utils.PageResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 商品表类型实现类
 * @author TianTian
 * @date 2022/1/19 14:51
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

	@Override  
    public List<TreeDto> queryAllGoodsTypes (Integer typeId) {
    	List<TreeDto> treeDtos = this.baseMapper.queryAllGoodsTypes();
    	if(null !=typeId){
    		for (TreeDto treeDto : treeDtos) {
    			if(treeDto.getId().equals(typeId)){
    				treeDto.setChecked(true);
    				break;
    			}
    		}
    	}
    	return treeDtos;
    }
	
	@Override
	public Map<String, Object> goodsTypeList() {
		List<GoodsType> menus = this.list();
		return PageResultUtil.setResult((long) menus.size(),menus);
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
	
	@Override
	public void saveGoodsType(GoodsType goodsType) {
		AssertUtil.isTrue(StringUtils.isBlank(goodsType.getName()), "商品类别名称不能为空!");
		AssertUtil.isTrue(null == goodsType.getpId(), "请指父类别!");
		goodsType.setState(0);
		AssertUtil.isTrue(!(this.save(goodsType)), "记录添加失败!");
		GoodsType parent = this.getById(goodsType.getpId());
		if(parent.getState()==0){
			parent.setState(1);
		}
		AssertUtil.isTrue(!(this.updateById(parent)), "记录添加失败!");
	}
	
	@Override
	public void deleteGoodsType(Integer id) {
		GoodsType temp= this.getById(id);
		AssertUtil.isTrue(null == temp, "待删除的记录不存在!");
		int count = this.count(new QueryWrapper<GoodsType>().eq("p_id",id));
		AssertUtil.isTrue(count>0, "存在子类别，暂不支持级联删除!");
		count = this.count(new QueryWrapper<GoodsType>().eq("p_id", temp.getpId()));
		if(count==1){
			AssertUtil.isTrue(!(this.update(new UpdateWrapper<GoodsType>().set("state", 0).eq("id",temp.getpId()))), "类别删除失败!");
		}
		AssertUtil.isTrue(!(this.removeById(id)), "类别删除失败!");
	}
}
