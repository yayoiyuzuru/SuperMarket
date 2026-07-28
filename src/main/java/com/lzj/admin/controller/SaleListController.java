package com.lzj.admin.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzj.admin.model.RespBean;
import com.lzj.admin.model.SaleCount;
import com.lzj.admin.pojo.SaleList;
import com.lzj.admin.pojo.SaleListGoods;
import com.lzj.admin.query.SaleListQuery;
import com.lzj.admin.service.SaleListService;
import com.lzj.admin.service.UserService;
import com.lzj.admin.utils.DateUtil;
import com.lzj.admin.utils.MathUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售单表控制器
 * @author TianTian
 * @date 2022/1/19 23:02
 */
@Controller
@RequestMapping("/sale")
public class SaleListController {

    @Resource
    private SaleListService saleListService;

    @Resource
    private UserService userService;


    /**
     * 销售出库主页
     * @return
     */
    @RequestMapping("index")
    public String index(Model model){
        model.addAttribute("saleNumber",saleListService.getNextSaleNumber());
        return "sale/sale";
    }


    @RequestMapping("save")
    @ResponseBody
    public RespBean save(SaleList saleList, String goodsJson, Principal principal){
        String userName = principal.getName();
        saleList.setUserId(userService.findForName(userName).getId());
        Gson gson = new Gson();
        List<SaleListGoods> slgList = gson.fromJson(goodsJson,new TypeToken<List<SaleListGoods>>(){}.getType());
        saleListService.saveSaleList(saleList,slgList);
        return RespBean.success("商品销售出库成功!");
    }

    /**
     * 销售单查询页
     * @return
     */
    @RequestMapping("searchPage")
    public String searchPage(){
        return "sale/sale_search";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> saleList(SaleListQuery saleListQuery){
        return saleListService.saleList(saleListQuery);
    }
    @RequestMapping("delete")
    @ResponseBody
    public RespBean delete(Integer id){
        saleListService.deletesaleList(id);
        return RespBean.success("删除成功");
    }

    @RequestMapping("countSale")
    @ResponseBody
    public Map<String,Object> countSale(SaleListQuery saleListQuery){
        return saleListService.countSale(saleListQuery);
    }



    @RequestMapping("countSaleByDay")
    @ResponseBody
    public Map<String, Object> countDaySale(String begin, String end) {
        Map<String, Object> result = new HashMap<>();
        List<SaleCount> saleCounts = new ArrayList<>();
        
        List<Map<String, Object>> list = saleListService.countDaySale(begin, end);
        List<String> datas = DateUtil.getRangeDates(begin, end);
        
        for (String data : datas) {
            SaleCount saleCount = new SaleCount();
            saleCount.setDate(data);
            boolean flag = true;
            
            for (Map<String, Object> map : list) {
                Object saleDateObj = map.get("saleDate");
                if (saleDateObj == null) {
                    continue;
                }
                
                String dd = saleDateObj.toString();
                if (dd.length() > 10) {
                    dd = dd.substring(0, 10);
                }
                
                if (data.equals(dd)) {
                    float amountCost = Float.parseFloat(map.get("amountCost").toString());
                    float amountSale = Float.parseFloat(map.get("amountSale").toString());
                    
                    saleCount.setAmountCost(MathUtil.format2Bit(amountCost));
                    saleCount.setAmountSale(MathUtil.format2Bit(amountSale));
                    saleCount.setAmountProfit(MathUtil.format2Bit(amountSale - amountCost));
                    flag = false;
                    break;
                }
            }
            
            if (flag) {
                saleCount.setAmountCost(0F);
                saleCount.setAmountSale(0F);
                saleCount.setAmountProfit(0F);
            }
            if (saleCount.getAmountSale() > 0) {
                saleCounts.add(saleCount);
            }
        }
        
        result.put("code", 0);
        result.put("msg", "success");
        result.put("count", saleCounts.size());
        result.put("data", saleCounts);
        return result;
    }


    @RequestMapping("countSaleByMonth")
    @ResponseBody
    public Map<String, Object> countSaleByMonth(String begin, String end) {
        Map<String, Object> result = new HashMap<>();
        
        List<Map<String, Object>> list = saleListService.countMonthSale(begin, end);
        
        List<SaleCount> saleCounts = new ArrayList<>();
        for (Map<String, Object> map : list) {
            SaleCount saleCount = new SaleCount();
            saleCount.setDate(map.get("saleMonth").toString());
            
            float amountCost = Float.parseFloat(map.get("amountCost").toString());
            float amountSale = Float.parseFloat(map.get("amountSale").toString());
            
            saleCount.setAmountCost(MathUtil.format2Bit(amountCost));
            saleCount.setAmountSale(MathUtil.format2Bit(amountSale));
            saleCount.setAmountProfit(MathUtil.format2Bit(amountSale - amountCost));
            saleCounts.add(saleCount);
        }
        
        result.put("code", 0);
        result.put("msg", "success");
        result.put("count", saleCounts.size());
        result.put("data", saleCounts);
        return result;
    }
}
