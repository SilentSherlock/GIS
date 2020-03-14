package com.programs.gis.control;

import com.programs.gis.service.CornService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
/*数据文件上传下载相关功能*/
@Controller
public class FileController {

    @Resource
    private CornService cornService;

    /*上传模板文件并保存*/
    @RequestMapping("/templateUpload")
    @ResponseBody
    public String templateUpload(@RequestParam("template") MultipartFile file) throws IOException {
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
    @RequestMapping("/templateDownload")
    public void templateDownload(HttpServletResponse httpServletResponse, String fileName) throws IOException {

        //String fileName = "template.xlsx";//根据前端的需求选择下载哪个模板文件
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/fileTemplates/";

        File templates = new File(path);
        if (!templates.exists()){
            templates.mkdirs();
        }

        //设置下载文件头信息，防止文件乱码不可读
        File excelTemplate = new File(path + fileName);
        //httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
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
    /*上传数据文件*/
    @RequestMapping(value = "/dataFileUpload")
    @ResponseBody
    public String dataFileUpload(@RequestParam("dataFile") MultipartFile multipartFile) throws IOException {
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/dataFile/";
        System.out.println(path);
        File test = new File(path);
        if (!test.exists()){
            test.mkdirs();
        }
        multipartFile.transferTo(new File(path + multipartFile.getOriginalFilename()));
        return "success";
    }
    /*保存上传的数据文件到数据库
    * 前端提供要保持的是哪个文件
    * 对应调取service中的方法
    * */
    @RequestMapping("/addToDatabaseByFile")
    @ResponseBody
    public String addToDatabaseByFile(@RequestBody String dataFileName) throws IOException, InvalidFormatException {
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/dataFile/";
        String dataPath = path + dataFileName;

        if (cornService.saveCornHeightAndChloByFile(dataPath)){
            System.out.println("Add To Database By File Success");
            return "success";
        }
        return "failed";
    }


    @RequestMapping("/addToDatabase")
    @ResponseBody
    public String addToDatabase(){
        return "success";
    }


}