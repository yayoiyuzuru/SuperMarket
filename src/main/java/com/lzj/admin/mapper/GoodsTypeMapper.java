package com.lzj.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzj.admin.dto.TreeDto;
import com.lzj.admin.pojo.GoodsType;

public interface GoodsTypeMapper extends BaseMapper<GoodsType> {
	List<TreeDto> queryAllGoodsTypes();
}
