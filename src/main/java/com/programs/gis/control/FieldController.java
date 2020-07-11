package com.programs.gis.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.programs.gis.entity.field.FieldWaterHold;
import com.programs.gis.entity.field.SWC;
import com.programs.gis.service.FieldService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Date: 2020/6/11
 * Description: accept the front request and return different FieldData
 */
@RestController
public class FieldController {

    @Resource
    private FieldService fieldService;

    /*获取所有田间持水量数据*/
    @RequestMapping("/field/fwh")
    public String getFieldWaterHold() throws Exception {
        List<FieldWaterHold> list = fieldService.getAllFieldWaterHold();
        if (list != null) {
            String listJson = JSON.toJSONString(list);
            System.out.println(listJson);
            return listJson;
        }
        return JSON.toJSONString("0");
    }

    /*获取所有土壤含水量数据*/
    @RequestMapping("/field/swc")
    public String getSWC() throws Exception {
        List<SWC> swcList = fieldService.getAllSWC();
        if (swcList != null) {
            String swcJson = JSON.toJSONString(swcList);
            System.out.println(swcJson);
            return swcJson;
        }
        return JSON.toJSONString("0");
    }

    /*根据DOY获取SWC数据*/
    @RequestMapping(value = "/field/swcByDOY")
    public String getSWCByDOY(@RequestBody String strDOY) throws Exception {
        JSONObject jsonObject = JSON.parseObject(strDOY);
        Integer DOY = Integer.parseInt(jsonObject.getString("DOY"));

        List<SWC> swcList = fieldService.getSWCByAttr("DOY", DOY);
        if (swcList != null) {
            String swcJson = JSON.toJSONString(swcList);
            System.out.println(swcJson);
            return swcJson;
        }
        return JSON.toJSONString("0");
    }
}
