package com.programs.gis.control;

import com.programs.gis.service.CornService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*玉米作物本身相关数据*/
@Controller
public class CornController {

    @Resource
    private CornService cornService;

    /*上传模板文件并保存*/
    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("templates") MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/fileTemplates";
        System.out.println(path);
        File test = new File(path);
        if (!test.exists()){
            test.mkdirs();
        }
        file.transferTo(new File(path  + file.getOriginalFilename()));
        return "success";
    }
    /*下载文件模板*/
    @RequestMapping("/fileDownload")
    public void fileDownload(HttpServletResponse httpServletResponse) throws FileNotFoundException {

        String fileName = "template.xlsx";
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/fileTemplates";

        File templates = new File(path);
        if (!templates.exists()){
            templates.mkdirs();
        }
    }

    @RequestMapping("/addToDatabase")
    @ResponseBody
    public String addToDatabase(){
        return "success";
    }

    @RequestMapping("/addToDatabaseByFile")
    @ResponseBody
    public String addToDatabaseByFile(){
        return "success";
    }
}
