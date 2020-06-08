package com.programs.gis.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.programs.gis.entity.corn.CornHeightAndChlo;
import com.programs.gis.entity.corn.CornLAI;
import com.programs.gis.entity.corn.CornLeaf;
import com.programs.gis.entity.corn.CornYield;
import com.programs.gis.function.Tools;
import com.programs.gis.service.CornService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CornController {

    @Resource
    private CornService cornService;

    /*获取所有cornLeaf数据*/
    @RequestMapping("/corn/cornLeafAll")
    @ResponseBody
    public String getCornLeaf() throws Exception {
        List<CornLeaf> cornLeafList = cornService.getAllCornLeaf();
        if (cornLeafList != null){
            String cornLeafJson = JSON.toJSONString(cornLeafList);
            System.out.println(cornLeafJson);
            return cornLeafJson;
        }
        return JSON.toJSONString("0");
    }

    /*根据TRT获取cornLeaf数据*/
    @RequestMapping("/corn/cornLeafTRT")
    @ResponseBody
    public String getCornLeafByTRT(@RequestBody String strTRT) throws Exception{
        JSONObject jsonObject = JSON.parseObject(strTRT);
        Float TRT = new Tools().transSeparator2dot(jsonObject.getString("TRT"));

        List<CornLeaf> cornLeafList = cornService.getCornLeafByAttr(TRT, "TRT");
        if (cornLeafList != null){
            String cornLeafJson = JSON.toJSONString(cornLeafList);
            System.out.println(cornLeafJson);
            return cornLeafJson;
        }
        return JSON.toJSONString("0");
    }

    /*根据DOY获取cornLeaf数据*/
    @RequestMapping("/corn/cornLeafDOY")
    @ResponseBody
    public String getCornLeafByDOY(@RequestBody String strDOY) throws Exception {
        JSONObject jsonObject = JSON.parseObject(strDOY);
        Integer DOY = Integer.parseInt(jsonObject.getString("DOY"));

        List<CornLeaf> cornLeafList = cornService.getCornLeafByAttr(DOY, "DOY");
        if (cornLeafList != null){
            String cornLeafJson = JSON.toJSONString(cornLeafList);
            System.out.println(cornLeafJson);
            return cornLeafJson;
        }
        return JSON.toJSONString("0");
    }

    /*根据DOY获取cornHeightAndChlo数据*/
    @RequestMapping("/corn/cornHeightAndChloDOY")
    @ResponseBody
    public String getCornHeightAndChloByDOY(@RequestBody String strDOY) throws Exception{
        JSONObject jsonObject = JSON.parseObject(strDOY);
        Integer DOY = Integer.parseInt(jsonObject.getString("DOY"));

        List<CornHeightAndChlo> cornHeightAndChloList = cornService.getCornHandChByAttr(DOY, "DOY");
        if (cornHeightAndChloList != null){
            String cornHeightAndChloJson = JSON.toJSONString(cornHeightAndChloList);
            System.out.println(cornHeightAndChloJson);
            return cornHeightAndChloJson;
        }
        return JSON.toJSONString("0");
    }

    /*获取cornYield所有数据*/
    @RequestMapping("/corn/cornYield")
    @ResponseBody
    public String getCornYield() throws Exception {
        List<CornYield> cornYieldList = cornService.getAllCornYield();
        if (cornYieldList != null) {
            String cornYieldJson = JSON.toJSONString(cornYieldList);
            System.out.println(cornYieldJson);
            return cornYieldJson;
        }
        return JSON.toJSONString("0");
    }

    /*根据DOY获取cornLAI数据*/
    @RequestMapping(value = "/corn/cornLAIDOY")
    @ResponseBody
    public String getCornLAIByDOY(@RequestBody String strDOY) throws Exception {
        JSONObject jsonObject = JSON.parseObject(strDOY);
        Integer DOY = Integer.parseInt(jsonObject.getString("DOY"));

        List<CornLAI> cornLAIList = cornService.getCornLAIByAttr(DOY, "DOY");
        if (cornLAIList != null) {
            String cornLAIJson = JSON.toJSONString(cornLAIList);
            System.out.println(cornLAIJson);
            return cornLAIJson;
        }
        return JSON.toJSONString("0");
    }
}
