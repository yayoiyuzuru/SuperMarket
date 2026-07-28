package com.lzj.admin.mapper;

import com.lzj.admin.model.CountResultModel;
import com.lzj.admin.pojo.PurchaseList;
import com.lzj.admin.query.PurchaseListQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 进货单接口
 * @author TianTian
 * @date 2022/1/21 18:27
 */
@Mapper
public interface PurchaseListMapper extends BaseMapper<PurchaseList> {
	String  getNextPurchaseNumber();
	
	IPage<PurchaseList>  purchaseList(IPage<PurchaseList> page, @Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);
	
	Long countPurchaseTotal(@Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);

    List<CountResultModel> purchaseListQueryList(@Param("purchaseListQuery") PurchaseListQuery purchaseListQuery);

    List<Map<String, Object>>  countDayPurchase(@Param("begin") String begin, @Param("end") String end);
}
