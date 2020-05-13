package com.programs.gis.service;

import com.programs.gis.dao.CornHeightAndChloDao;
import com.programs.gis.dao.CornLAIDao;
import com.programs.gis.dao.CornLeafDao;
import com.programs.gis.dao.CornYieldDao;
import com.programs.gis.entity.CornHeightAndChlo;
import com.programs.gis.entity.CornLAI;
import com.programs.gis.entity.CornLeaf;
import com.programs.gis.entity.CornYield;
import com.programs.gis.function.Tools;
import org.apache.poi.ss.usermodel.*;
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
    @Resource
    private CornLAIDao cornLAIDao;

    /*根据不同daoType确定哪个dao进行数据库操作
    * 0-----cornHeightAndChloDao
    * 1-----cornLeafDao
    * 2-----cornYieldDao
    * 3-----cornLAIDao
    * */
    private boolean saveByFile(String filePath, int daoType) throws Exception {
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println("The wanted excel isn't exist");
            return false;
        }

        //FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(file);
        int numberOfSheets = workbook.getNumberOfSheets();//excel文件中sheet的数量
        for (int i = 0;i < numberOfSheets;i++){
            Sheet sheet = workbook.getSheetAt(i);//获得当前工作表
            if (sheet != null){
                int rows = sheet.getPhysicalNumberOfRows();
                System.out.println("Excel表共" + rows + "行数据");
                for (int row = 1;row < rows;row++){//跳过表头
                    System.out.println("读取到Excel表第" + row + "行");
                    Row tmpRow = sheet.getRow(row);//获取当前行
                    Row.MissingCellPolicy policy = Row.RETURN_NULL_AND_BLANK;//将空白单元格作为空值处理
                    if (!rowSolution(tmpRow, policy, daoType)) continue;
                }
            }
        }
        return true;
    }

    /*根据daotype对表的一行进行处理*/
    private boolean rowSolution(Row tmpRow, Row.MissingCellPolicy policy, int daoType) throws Exception {

        CornHeightAndChlo cornHeightAndChlo = null;
        CornLeaf cornLeaf = null;
        CornYield cornYield = null;
        CornLAI cornLAI = null;

        switch (daoType) {
            case 0:
                cornHeightAndChlo = new CornHeightAndChlo();
                break;
            case 1:
                cornLeaf = new CornLeaf();
                break;
            case 2:
                cornYield = new CornYield();
                break;
            case 3:
                cornLAI = new CornLAI();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + daoType);
        }

        Tools tools = new Tools();
        if (tmpRow != null){
            int cols = tmpRow.getPhysicalNumberOfCells();
            System.out.println("Excel表有" + cols + "列");
            for (int col = 0;col < cols;col++) {
                Cell currentCell = tmpRow.getCell(col, policy);
                if (currentCell != null) {
                    switch (daoType){//根据不同的daoType，进行每个单元格的处理
                        case 0:
                            switch (col) {
                                case 0:
                                    cornHeightAndChlo.setDOY((int) currentCell.getNumericCellValue());
                                    break;
                                case 1:
                                    cornHeightAndChlo.setTRT((int) currentCell.getNumericCellValue());
                                    break;
                                case 2:
                                    cornHeightAndChlo.setNUM_1(tools.transSeparator2dot(currentCell.getStringCellValue()));
                                    break;
                                case 3:
                                    cornHeightAndChlo.setNUM_2(tools.transSeparator2dot(currentCell.getStringCellValue()));
                                    break;
                                case 4:
                                    cornHeightAndChlo.setNUM_3((int) currentCell.getNumericCellValue());
                                    break;
                                case 5:
                                    cornHeightAndChlo.setHeight((float) currentCell.getNumericCellValue());
                                    break;
                                case 6:
                                    cornHeightAndChlo.setChlorophyll((float) currentCell.getNumericCellValue());
                                    break;
                            }
                            break;
                        case 1:
                            switch (col) {
                                case 0:
                                    cornLeaf.setDOY((int) currentCell.getNumericCellValue());
                                    break;
                                case 1:
                                    cornLeaf.setTRT(tools.transSeparator2dot(currentCell.getStringCellValue()));
                                    break;
                                case 2:
                                    cornLeaf.setLeafArea(currentCell.getNumericCellValue());
                                    break;
                                case 3:
                                    cornLeaf.setLeafPerimeter(currentCell.getNumericCellValue());
                                    break;
                                case 4:
                                    cornLeaf.setLeafNumber((int) currentCell.getNumericCellValue());
                                    break;
                                case 5:
                                    cornLeaf.setRecordDay((float) currentCell.getNumericCellValue());
                            }
                            break;
                        case 2:
                            switch (col) {
                                case 0:
                                    cornYield.setCornFieldId(tools.transSeparator2dot(currentCell.getStringCellValue()));
                                    break;
                                case 1:
                                    cornYield.setMoistureYield(currentCell.getNumericCellValue());
                                    break;
                                case 2:
                                    cornYield.setBoxWeight((float) currentCell.getNumericCellValue());
                                    break;
                                case 3:
                                    cornYield.setBeforeDehydration((float) currentCell.getNumericCellValue());
                                    break;
                                case 4:
                                    cornYield.setAfterDehydration((float) currentCell.getNumericCellValue());
                                    break;
                                case 5:
                                    cornYield.setMoistureContent((float) currentCell.getNumericCellValue());
                                    break;
                                case 6:
                                    cornYield.setDryYield(currentCell.getNumericCellValue());
                                    break;
                            }
                            break;
                        case 3:
                            switch (col) {
                                case 0:
                                    cornLAI.setDOY((int) currentCell.getNumericCellValue());
                                    break;
                                case 1:
                                    cornLAI.setTRT((int) currentCell.getNumericCellValue());
                                    break;
                                case 2:
                                    cornLAI.setNUM_1(currentCell.getStringCellValue());
                                    break;
                                case 3:
                                    cornLAI.setNUM_2(currentCell.getStringCellValue());
                                    break;
                                case 4:
                                    cornLAI.setNUM_3((int) currentCell.getNumericCellValue());
                                    break;
                                case 5:
                                    cornLAI.setLAI((float) currentCell.getNumericCellValue());
                                    break;
                            }
                            break;
                    }
                }else {
                    System.out.println("表格的第" + tmpRow.getRowNum() + "行第" + col + "列中有空格或未定义的单元格");
                    return false;
                }
            }

            switch (daoType) {
                case 0:
                    cornHeightAndChloDao.save(cornHeightAndChlo);
                    break;
                case 1:
                    cornLeafDao.save(cornLeaf);
                    break;
                case 2:
                    cornYieldDao.save(cornYield);
                    break;
                case 3:
                    cornLAIDao.save(cornLAI);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + daoType);
            }
        }
        return true;
    }
    /*LAI*/
    public void saveCornLAI(Integer DOY, Integer TRT, String NUM_1, String NUM_2, Integer NUM_3, Float LAI) throws Exception {

        CornLAI cornLAI = new CornLAI();
        System.out.println("Save CornLAI");
        cornLAI.setDOY(DOY);
        cornLAI.setTRT(TRT);
        cornLAI.setNUM_1(NUM_1);
        cornLAI.setNUM_2(NUM_2);
        cornLAI.setNUM_3(NUM_3);
        cornLAI.setLAI(LAI);
        cornLAIDao.save(cornLAI);
    }
    public Boolean saveCornLAIByFile(String filePath) throws Exception {
        return saveByFile(filePath, 3);
    }
    public List<CornLAI> getAllCornLAI() throws Exception {
        List<CornLAI> cornLAIList = cornLAIDao.getAll();
        if (cornLAIList != null) {
            System.out.println("Get All cornLAI successfully");
            return cornLAIList;
        }
        return null;
    }
    public List<CornLAI> getCornLAIByAttr(Object attrValue, String attrName) throws Exception {
        List<CornLAI> cornLAIList = cornLAIDao.getByAttr(attrValue, attrName);
        if (cornLAIList != null) {
            System.out.println("Get cornLAI by " + attrName + " successfully");
            return cornLAIList;
        }
        return null;
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
