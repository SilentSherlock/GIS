package com.programs.gis.service;

import com.programs.gis.dao.CornHeightAndChloDao;
import com.programs.gis.dao.CornLeafDao;
import com.programs.gis.dao.CornYieldDao;
import com.programs.gis.entity.CornHeightAndChlo;
import com.programs.gis.entity.CornLeaf;
import com.programs.gis.entity.CornYield;
import com.programs.gis.function.Tools;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Service
public class CornService {//合并玉米本身信息相关的Dao
    @Resource
    private CornHeightAndChloDao cornHeightAndChloDao;
    @Resource
    private CornLeafDao cornLeafDao;
    @Resource
    private CornYieldDao cornYieldDao;

    /*根据不同daoType确定哪个dao进行数据库操作
    * 0-----cornHeightAndChloDao
    * 1-----cornLeafDao
    * 2-----cornYieldDao
    * */

    private boolean saveByFile(String filePath, int daoType) throws Exception {
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println("The wanted excel isn't exist");
            return false;
        }

        //FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(file);
        Tools tools = new Tools();
        int numberOfSheets = workbook.getNumberOfSheets();//excel文件中sheet的数量
        for (int i = 0;i < numberOfSheets;i++){
            Sheet sheet = workbook.getSheetAt(i);//获得当前工作表
            if (sheet != null){
                for (int row = 1;row < sheet.getPhysicalNumberOfRows();row++){//跳过表头
                    System.out.println("Excel表行数:" + sheet.getPhysicalNumberOfRows());
                    Row tmpRow = sheet.getRow(row);//获取当前行
                    if (tmpRow != null){
                        switch (daoType){//根据不同的daoType,确定是哪个dao要进行数据库操作
                            case 0:
                                Integer DOY = (int)tmpRow.getCell(0).getNumericCellValue();
                                Integer TRT = (int)tmpRow.getCell(1).getNumericCellValue();
                                Float NUM_1 = tools.transSeparator2dot(tmpRow.getCell(2).getStringCellValue());
                                Float NUM_2 = tools.transSeparator2dot(tmpRow.getCell(3).getStringCellValue());
                                Integer NUM_3 = (int)tmpRow.getCell(4).getNumericCellValue();
                                Float height = (float)tmpRow.getCell(5).getNumericCellValue();
                                Float chlo = (float)tmpRow.getCell(6).getNumericCellValue();
                                saveCornHeightAndChlo(DOY, TRT, NUM_1, NUM_2, NUM_3, height, chlo);
                                break;
                            case 1:
                                Integer DOY1 = (int)tmpRow.getCell(0).getNumericCellValue();
                                Float TRT1 = tools.transSeparator2dot(tmpRow.getCell(1).getStringCellValue());
                                Double leafArea = tmpRow.getCell(2).getNumericCellValue();
                                Double leafPerimeter = tmpRow.getCell(3).getNumericCellValue();
                                Integer leafNumber = (int)tmpRow.getCell(4).getNumericCellValue();
                                Float recordDay = (float)tmpRow.getCell(5).getNumericCellValue();
                                saveCornLeaf(DOY1, TRT1, leafArea, leafPerimeter, leafNumber, recordDay);
                                break;
                            case 2://读取方法待修改
                                Float cornFieldId = tools.transSeparator2dot(tmpRow.getCell(0).getStringCellValue());
                                Double moistureYield = tmpRow.getCell(1).getNumericCellValue();
                                Float boxWeight = (float) tmpRow.getCell(2).getNumericCellValue();
                                Float beforeDehydration = (float) tmpRow.getCell(3).getNumericCellValue();
                                Float afterDehydration = (float) tmpRow.getCell(4).getNumericCellValue();
                                Float moistureContent = (float) tmpRow.getCell(5).getNumericCellValue();
                                Double dryYield = tmpRow.getCell(6).getNumericCellValue();
                                saveCornYield(cornFieldId, moistureYield, boxWeight, beforeDehydration, afterDehydration, moistureContent, dryYield);
                                break;
                        }
                    }
                }
            }
        }
        return true;
    }
    /*株高和叶绿素*/
    public void saveCornHeightAndChlo(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2, Integer NUM_3,
                                      Float height, Float chlo) throws Exception{
        CornHeightAndChlo cornHeightAndChlo = new CornHeightAndChlo();
        System.out.println("Save CornHeightAndChlo");
        cornHeightAndChlo.setDOY(DOY);
        cornHeightAndChlo.setTRT(TRT);
        cornHeightAndChlo.setNUM_1(NUM_1);
        cornHeightAndChlo.setNUM_2(NUM_2);
        cornHeightAndChlo.setNUM_3(NUM_3);
        cornHeightAndChlo.setHeight(height);
        cornHeightAndChlo.setChlorophyll(chlo);
        cornHeightAndChloDao.save(cornHeightAndChlo);
        System.out.println("save CornHeightAndChlo successfully");
    }
    public boolean saveCornHeightAndChloByFile(String filePath) throws Exception {//待补全，通过文件保存
        return saveByFile(filePath, 0);
    }

    public List<CornHeightAndChlo> getAllCornHeightAndChlo() throws Exception{
        System.out.println("Get all CornHeightAndChlo");
        return cornHeightAndChloDao.getAll();
    }

    public void deleteCornHeightAndChlo(Integer DOY, Float NUM_2) throws Exception{
        System.out.println("Delete CornHeightAndChlo By Primary Key");
        cornHeightAndChloDao.deleteByPrimaryKey(DOY, NUM_2);
    }

    public CornHeightAndChlo getCornHeightAndChlo(Integer DOY, Float NUM_2) throws Exception{
        System.out.println("Get CornHeightAndChlo By Primary Key");
        return cornHeightAndChloDao.getByPrimaryKey(DOY, NUM_2);
    }

    public List<CornHeightAndChlo> getCornHandChByAttr(Object attr, String attrName) throws Exception{
        System.out.println("Get CornHeightAndChlo By " + attrName);
        List<CornHeightAndChlo> cornHeightAndChloList = cornHeightAndChloDao.getByAttr(attr, attrName);
        if (cornHeightAndChloList != null){
            System.out.println("Get CornHeightAndChlo By "+ attrName + " success");
            return cornHeightAndChloList;
        }
        return null;
    }

    /*玉米叶参数*/
    public void saveCornLeaf(Integer DOY, Float TRT, Double leafArea, Double leafPerimeter, Integer leafNumber,
                             Float recordDay) throws Exception{
        System.out.println("Save CornLeaf");
        CornLeaf cornLeaf = new CornLeaf();
        cornLeaf.setDOY(DOY);
        cornLeaf.setTRT(TRT);
        cornLeaf.setLeafArea(leafArea);
        cornLeaf.setLeafPerimeter(leafPerimeter);
        cornLeaf.setLeafNumber(leafNumber);
        cornLeaf.setRecordDay(recordDay);

        System.out.println(cornLeaf.toString());
        cornLeafDao.save(cornLeaf);
        System.out.println("Save CornLeaf Successfully");
    }

    public boolean saveCornLeafByFile(String filePath) throws Exception {//待补全
        return saveByFile(filePath, 1);
    }

    public void deleteCornLeaf(Integer DOY, Float TRT) throws Exception{
        System.out.println("Delete CornLeaf By Primary Key");
        cornLeafDao.deleteByPrimaryKey(DOY, TRT);
    }

    public List<CornLeaf> getAllCornLeaf() throws Exception{
        System.out.println("Get All CornLeaf");
        List<CornLeaf> cornLeafList = cornLeafDao.getAll();
        if (cornLeafList != null){
            System.out.println("Get All CornLeaf success");
            return cornLeafList;
        }
        return null;
    }

    public CornLeaf getCornLeaf(Integer DOY, Float TRT) throws Exception{
        System.out.println("Get CornLeaf By Primary Key");
        return cornLeafDao.getByPrimaryKey(DOY, TRT);
    }

    /*传递属性名来获取全部或部分数据*/
    public List<CornLeaf> getCornLeafByAttr(Object attr, String attrName) throws Exception{
        System.out.println("Get CornLeaf By " + attrName);
        List<CornLeaf> cornLeafList = cornLeafDao.getByAttr(attr, attrName);
        if (cornLeafList != null){
            System.out.println("Get CornLeafList By " + attrName + " success");
            return cornLeafList;
        }
        return null;
    }

    /*玉米产量*/
    public void saveCornYield(Float cornFieldId, Double moistureYield, Float boxWeight, Float beforeDehydration,
                              Float afterDehydration, Float moistureContent, Double dryYield) throws Exception{
        System.out.println("Save CornYield");
        CornYield cornYield = new CornYield();
        cornYield.setCornFieldId(cornFieldId);
        cornYield.setMoistureYield(moistureYield);
        cornYield.setBoxWeight(boxWeight);
        cornYield.setBeforeDehydration(beforeDehydration);
        cornYield.setAfterDehydration(afterDehydration);
        cornYield.setMoistureContent(moistureContent);
        cornYield.setDryYield(dryYield);

        cornYieldDao.save(cornYield);
        System.out.println("Save CornYield Successfully");
    }

    public boolean saveCornYieldByFile(String filePath) throws Exception{//待补全
        return saveByFile(filePath, 2);
    }

    public List<CornYield> getAllCornYield() throws Exception{
        System.out.println("Get All CornYield");
        List<CornYield> cornYieldList = cornYieldDao.getAll();

        if (cornYieldList != null) {
            System.out.println("Get All CornYield Successfully");
            return cornYieldList;
        }
        return null;
    }

    public CornYield getCornYield(Float cornFieldId) throws Exception{
        System.out.println("Get CornYield By Primary Key");
        return cornYieldDao.getById(cornFieldId);
    }

    public void deleteCornYield(Float cornFieldId) throws Exception{
        System.out.println("Delete CornYield By Primary Key");
        cornYieldDao.deleteById(cornFieldId);
    }

}
