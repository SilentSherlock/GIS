package com.programs.gis.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.programs.gis.entity.CornLeaf;
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
    public String getCornLeaf() throws Exception {//待补全，需要将java对象转换为json对象
        List<CornLeaf> cornLeafList = cornService.getAllCornLeaf();
        if (cornLeafList != null){
            String cornLeafJson = JSON.toJSONString(cornLeafList);
            System.out.println(cornLeafJson);
            return cornLeafJson;
        }
        return JSON.toJSONString("0");
    }

    /*根据TRT获取数据*/
    @RequestMapping("/corn/cornLeafTRT")
    @ResponseBody
    public String getCornLeafByTRT(@RequestBody String strTRT) throws Exception{
        JSONObject jsonObject = JSON.parseObject(strTRT);
        Float TRT = Float.valueOf(jsonObject.getString("TRT"));

        List<CornLeaf> cornLeafList = cornService.getByTRT(TRT);
        if (cornLeafList != null){
            String cornLeafJson = JSON.toJSONString(cornLeafList);
            System.out.println(cornLeafJson);
            return cornLeafJson;
        }
        return JSON.toJSONString("0");
    }
}
