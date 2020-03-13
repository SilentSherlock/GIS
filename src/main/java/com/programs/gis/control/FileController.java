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
import java.io.*;
import java.net.URLEncoder;

/*玉米作物本身相关数据*/
@Controller
public class CornController {

    @Resource
    private CornService cornService;

    /*上传模板文件并保存*/
    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("template") MultipartFile file) throws IOException {
        System.out.println(file.getOriginalFilename());
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/fileTemplates/";
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
    public void fileDownload(HttpServletResponse httpServletResponse) throws IOException {

        String fileName = "template.xlsx";//文件名可以根据具体需要下载什么模板来进行确定
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/fileTemplates/";

        File templates = new File(path);
        if (!templates.exists()){
            templates.mkdirs();
        }

        File excelTemplate = new File(path + fileName);
        httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        httpServletResponse.addHeader("Content-length", String.valueOf(excelTemplate.length()));

        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileName.trim(), "UTF-8"));

        //输出
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(excelTemplate));
        OutputStream outputStream = httpServletResponse.getOutputStream();
        byte[] buff = new byte[10000];
        int i = 0;
        while (i != -1){
            i = bufferedInputStream.read(buff);
            if (i != -1){
                outputStream.write(buff, 0, i);
            }
        }
        bufferedInputStream.close();
        outputStream.flush();
        outputStream.close();
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
