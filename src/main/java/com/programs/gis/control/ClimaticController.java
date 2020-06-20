package com.programs.gis.control;

import com.alibaba.fastjson.JSON;
import com.programs.gis.entity.climatic.ClimaticStation;
import com.programs.gis.entity.climatic.FieldPAI;
import com.programs.gis.service.ClimaticService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Date: 2020/6/11
 * Description: accept the front request and return Climatic data
 */
@RestController
public class ClimaticController {

    @Resource
    private ClimaticService climaticService;

    /*获取所有气象站数据*/
    @RequestMapping("/climatic/station")
    public String getAllClimaticStation() throws Exception {
        List<ClimaticStation> stationList = climaticService.getAllClimaticStation();
        if (stationList != null) {
            String stationJson = JSON.toJSONString(stationList);
            System.out.println(stationJson);
            return stationJson;
        }
        return JSON.toJSONString("0");
    }

    /*获取所有降雨量和灌溉量数据*/
    @RequestMapping("/climatic/fieldpai")
    public String getFieldPAI() throws Exception {
        List<FieldPAI> fieldPAIS = climaticService.getAllFieldPAI();
        if (fieldPAIS != null) {
            String paiJson = JSON.toJSONString(fieldPAIS);
            System.out.println(paiJson);
            return paiJson;
        }
        return JSON.toJSONString("0");
    }

}
