package com.programs.gis.service;

import com.programs.gis.dao.climatic.ClimaticStationDao;
import com.programs.gis.dao.climatic.FieldPAIDao;
import com.programs.gis.entity.climatic.ClimaticStation;
import com.programs.gis.entity.climatic.FieldPAI;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Date: 2020/6/9
 * Description: give a unify operation for Climatic data
 */
@Service
public class ClimaticService {
    @Resource
    private ClimaticStationDao climaticStationDao;
    @Resource
    private FieldPAIDao fieldPAIDao;


    /*daotype:
    * o----climaticStationDao
    * 1----fieldPAI*/

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
                    Row.MissingCellPolicy policy = Row.RETURN_NULL_AND_BLANK;//将空单元格和空指分别返回
                    if (!rowSolution(tmpRow, policy, daoType)) continue;
                }
            }
        }
        return true;
    }

    private boolean rowSolution(Row tmpRow, Row.MissingCellPolicy policy, int daoType) throws Exception {
        ClimaticStation climaticStation = null;
        FieldPAI fieldPAI = null;
        if (daoType == 0) climaticStation = new ClimaticStation();
        else if (daoType == 1) fieldPAI = new FieldPAI();
        else throw new Exception("In ClimaticService daoType must be 0 or 1, given "+ daoType);

        if (tmpRow != null) {
            int cols = tmpRow.getPhysicalNumberOfCells();
            for (int col = 0;col < cols;col++) {
                Cell cell = tmpRow.getCell(col, policy);
                if (cell != null) {
                    switch (daoType) {
                        case 0:
                            switch (col) {
                                case 0:
                                    climaticStation.setDataId(String.valueOf((int)cell.getNumericCellValue()));
                                    break;
                                case 1:
                                    climaticStation.setRecordDate(cell.getDateCellValue());
                                    break;
                                case 2:
                                    climaticStation.setAirTemperature((float) cell.getNumericCellValue());
                                    break;
                                case 3:
                                    climaticStation.setAirHumidity((float) cell.getNumericCellValue());
                                    break;
                                case 4:
                                    climaticStation.setRadiationAmount((int) cell.getNumericCellValue());
                                    break;
                                case 5:
                                    climaticStation.setWindSpeed((float) cell.getNumericCellValue());
                                    break;
                                case 6:
                                    climaticStation.setRainfall((float) cell.getNumericCellValue());
                                    break;
                            }
                            break;
                        case 1:
                            switch (col) {
                                case 0:
                                    fieldPAI.setRecordDate(cell.getDateCellValue());
                                    break;
                                case 1:
                                    fieldPAI.setDOY((int) cell.getNumericCellValue());
                                    break;
                                case 2:
                                    fieldPAI.setTRT((int) cell.getNumericCellValue());
                                    break;
                                case 3:
                                    fieldPAI.setPrecipitation((int) cell.getNumericCellValue());
                                    break;
                                case 4:
                                    fieldPAI.setIrrigation((int) cell.getNumericCellValue());
                                    break;
                            }
                    }
                }
            }
            if (daoType == 0) climaticStationDao.save(climaticStation);
            else fieldPAIDao.save(fieldPAI);
            return true;
        }
        return false;
    }
    /*ClimaticStation*/
    //待补全
    public boolean saveClimaticStationByFile(String filePath) throws Exception {
        return saveByFile(filePath, 0);
    }

    public void saveClimaticStation(String dataId, Date rd, Float at, Float ah, Integer ra, Float ws, Float rf) throws Exception {
        System.out.println("Save ClimaticStation");
        ClimaticStation climaticStation = new ClimaticStation();
        climaticStation.setDataId(dataId);
        climaticStation.setRecordDate(rd);
        climaticStation.setAirTemperature(at);
        climaticStation.setAirHumidity(ah);
        climaticStation.setRadiationAmount(ra);
        climaticStation.setWindSpeed(ws);
        climaticStation.setRainfall(rf);

        climaticStationDao.save(climaticStation);
    }

    public void deleteClimaticStation(String dataId) throws Exception {
        System.out.println("Delete one climaticStation data");
        climaticStationDao.deleteById(dataId);
    }

    public List<ClimaticStation> getAllClimaticStation() throws Exception {
        System.out.println("Get all ClimaticStation");
        List<ClimaticStation> stationList = climaticStationDao.getAll();
        if (stationList != null) {
            System.out.println("Get all ClimaticStation successfully");
            return stationList;
        }
        return null;
    }

    public List<ClimaticStation> getClimaticStationByAttr(String attrName, Object attrValue) throws Exception {
        System.out.println("Get ClimaticStation By " + attrName);
        List<ClimaticStation> stationList = climaticStationDao.getByAttr(attrName, attrValue);
        if (stationList != null) {
            System.out.println("Get ClimaticStation By " + attrName + "Successfully");
            return stationList;
        }
        return null;
    }

    /*FieldPAI*/
    public boolean saveFieldPAIByFile(String filePath) throws Exception {
        return saveByFile(filePath, 1);
    }

    public void saveFieldPAI(Integer DOY, Integer TRT, Integer p, Integer i, Date date) throws Exception {
        System.out.println("Save FieldPAI");
        FieldPAI fieldPAI = new FieldPAI();
        fieldPAI.setDOY(DOY);
        fieldPAI.setTRT(TRT);
        fieldPAI.setPrecipitation(p);
        fieldPAI.setIrrigation(i);
        fieldPAI.setRecordDate(date);
        fieldPAIDao.save(fieldPAI);
    }

    public void deleteFieldPAI(Integer DOY, Integer TRT) throws Exception {
        System.out.println("Delete FieldPAI By primary key");
        fieldPAIDao.deleteByPrimaryKey(DOY, TRT);
    }

    public List<FieldPAI> getAllFieldPAI() throws Exception {
        System.out.println("Get all FieldPAI");
        List<FieldPAI> list = fieldPAIDao.getAll();
        if (list != null) {
            System.out.println("Get all FieldPAI successfully");
            return list;
        }
        return null;
    }

    public List<FieldPAI> getFieldPAIByAttr(String attrName, String attrValue) throws Exception {
        System.out.println("Get FieldPAI By " + attrName);
        List<FieldPAI> list = fieldPAIDao.getByAttr(attrName, attrValue);
        if (list != null) {
            System.out.println("Get FieldPAI By " + attrName + "Successfully");
            return list;
        }
        return null;
    }
}
