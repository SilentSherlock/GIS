package com.programs.gis.service;

import com.programs.gis.dao.field.FieldWaterHoldDao;
import com.programs.gis.dao.field.SWCDao;
import com.programs.gis.entity.field.FieldWaterHold;
import com.programs.gis.entity.field.SWC;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Date: 2020/5/14
 * Description: Field Service, contain all the field dao, offer a unified call for field data operation
 */
@Service
public class FieldService {

    @Resource
    private FieldWaterHoldDao fieldWaterHoldDao;
    @Resource
    private SWCDao swcDao;

    /*daoType
    * 0-----fieldWaterHoldDao
    * 1-----swcDao
    * */
    private boolean saveByFile(String filePath, int daoType) throws Exception {
        File file = new File(filePath);
        if (!file.exists()){
            System.out.println("The wanted excel isn't exist");
            return false;
        }

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
                    Row.MissingCellPolicy policy = Row.RETURN_NULL_AND_BLANK;//将空单元格和空值分别返回
                    if (!rowSolution(tmpRow, policy, daoType)) continue;
                }
            }
        }
        return true;
    }

    private boolean rowSolution(Row tmpRow, Row.MissingCellPolicy policy, int daoType) throws Exception {
        SWC swc = null;
        FieldWaterHold fieldWaterHold = null;
        switch (daoType) {
            case 0:
                fieldWaterHold = new FieldWaterHold();
                break;
            case 1:
                swc = new SWC();
                break;
            default:
                throw new Exception("daoType is illegal, it must be 0 or 1, given " + daoType);
        }

        if (tmpRow != null) {
            int cols = tmpRow.getPhysicalNumberOfCells();
            System.out.println("Excel表有" + cols + "列");
            for (int col = 0;col < cols;col++) {
                Cell cell = tmpRow.getCell(col, policy);
                if (cell != null) {
                    switch (daoType) {
                        case 0:
                            switch (col) {
                                case 0:
                                    fieldWaterHold.setTRT((int) cell.getNumericCellValue());
                                    break;
                                case 1:
                                    fieldWaterHold.setNUM_1(cell.getStringCellValue());
                                    break;
                                case 2:
                                    fieldWaterHold.setNUM_2(cell.getStringCellValue());
                                    break;
                                case 3:
                                    fieldWaterHold.setMassWaterContent((float) cell.getNumericCellValue());
                                    break;
                                case 4:
                                    fieldWaterHold.setContentWeight((float) cell.getNumericCellValue());
                                    break;
                                case 5:
                                    fieldWaterHold.setVolumeWaterContent((float) cell.getNumericCellValue());
                                    break;
                            }
                            break;
                        case 1:
                            switch (col) {
                                case 0:
                                    swc.setRecordDate(cell.getDateCellValue());
                                    break;
                                case 1:
                                    swc.setDOY((int) cell.getNumericCellValue());
                                    break;
                                case 2:
                                    swc.setTRT((int) cell.getNumericCellValue());
                                    break;
                                case 3:
                                    swc.setNUM_1(cell.getStringCellValue());
                                    break;
                                case 4:
                                    swc.setNUM_2(cell.getStringCellValue());
                                    break;
                                case 5:
                                    swc.setNUM_3((int) cell.getNumericCellValue());
                                    break;
                                case 6:
                                    swc.setBoxAndWetSoil((float) cell.getNumericCellValue());
                                    break;
                                case 7:
                                    swc.setBoxAndDrySoil((float) cell.getNumericCellValue());
                                    break;
                                case 8:
                                    swc.setBox((float) cell.getNumericCellValue());
                                    break;
                                case 9:
                                    swc.setWater((float) cell.getNumericCellValue());
                                    break;
                                case 10:
                                    swc.setDrySoil((float) cell.getNumericCellValue());
                                    break;
                                case 11:
                                    swc.setSWC((float) cell.getNumericCellValue());
                                    break;
                            }
                            break;
                    }
                }
            }
            if (daoType == 0) fieldWaterHoldDao.save(fieldWaterHold);
            else swcDao.save(swc);
            return true;
        }
        return false;
    }
    /*田间持水量*/
    public void saveFieldWaterHold(Integer TRT, String NUM_1, String NUM_2, Float mwc,
                            Float vwc, Float contentWeight) throws Exception {
        FieldWaterHold fieldWaterHold = new FieldWaterHold();
        System.out.println("save FieldWaterHold");
        fieldWaterHold.setTRT(TRT);
        fieldWaterHold.setNUM_1(NUM_1);
        fieldWaterHold.setNUM_2(NUM_2);
        fieldWaterHold.setMassWaterContent(mwc);
        fieldWaterHold.setVolumeWaterContent(vwc);
        fieldWaterHold.setContentWeight(contentWeight);

        fieldWaterHoldDao.save(fieldWaterHold);
    }

    public boolean saveFieldWaterHoldByFile(String filePath) throws Exception {
        return saveByFile(filePath, 0);
    }

    public void deleteFieldWaterHold(String NUM_2) throws Exception {
        System.out.println("Delete FieldWaterHold by primary key");
        fieldWaterHoldDao.deleteByPrimaryKey(NUM_2);
    }

    public List<FieldWaterHold> getAllFieldWaterHold() throws Exception {
        System.out.println("Get all FieldWaterHold");
        List<FieldWaterHold> fieldWaterHolds = fieldWaterHoldDao.getAll();
        if (fieldWaterHolds != null) {
            System.out.println("Get all FieldWaterHold success");
            return fieldWaterHolds;
        }
        return null;
    }

    public List<FieldWaterHold> getFieldWaterHoldByAttr(String attrName, Object attrValue) throws Exception {
        System.out.println("Get FieldWaterHold By " + attrName);
        List<FieldWaterHold> fieldWaterHoldList = fieldWaterHoldDao.getByAttr(attrName, attrValue);
        if (fieldWaterHoldList != null) {
            System.out.println("Get FieldWaterHold by " + attrName + " success");
            return fieldWaterHoldList;
        }
        return null;
    }

    /*土壤含水量*/
    public void saveSWC(Integer DOY, Integer TRT, String NUM_1, String NUM_2, Integer NUM_3, Float baws,
                        Float bads, Float box, Float water, Float drySoil, Float swc, Date recordDate) throws Exception {
        System.out.println("Save SWC");
        SWC swc1 = new SWC();
        swc1.setDOY(DOY);
        swc1.setTRT(TRT);
        swc1.setNUM_1(NUM_1);
        swc1.setNUM_2(NUM_2);
        swc1.setNUM_3(NUM_3);
        swc1.setBoxAndWetSoil(baws);
        swc1.setBoxAndDrySoil(bads);
        swc1.setBox(box);
        swc1.setWater(water);
        swc1.setDrySoil(drySoil);
        swc1.setSWC(swc);
        swc1.setRecordDate(recordDate);

        swcDao.save(swc1);
    }

    public boolean saveSWCByFile(String filePath) throws Exception {
        return saveByFile(filePath, 1);
    }

    public List<SWC> getAllSWC() throws Exception {

        System.out.println("Get All SWC");
        List<SWC> swcList = swcDao.getAll();
        if (swcList != null) {
            System.out.println("Get All SWC success");
            return swcList;
        }
        return null;
    }

    public List<SWC> getSWCByAttr(String attrName, Object attrValue) throws Exception {
        System.out.println("Get SWC by " + attrName);
        List<SWC> swcList = swcDao.getByAttr(attrName, attrValue);
        if (swcList != null) {
            System.out.println("Get SWC By " + attrName + " success");
            return swcList;
        }
        return null;
    }
}
