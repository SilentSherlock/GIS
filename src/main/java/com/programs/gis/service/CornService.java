package com.programs.gis.service;

import com.programs.gis.dao.CornHeightAndChloDao;
import com.programs.gis.dao.CornLeafDao;
import com.programs.gis.dao.CornYieldDao;
import com.programs.gis.entity.CornHeightAndChlo;
import com.programs.gis.entity.CornLeaf;
import com.programs.gis.entity.CornYield;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CornService {//合并玉米本身信息相关的Dao
    @Resource
    private CornHeightAndChloDao cornHeightAndChloDao;
    @Resource
    private CornLeafDao cornLeafDao;
    @Resource
    private CornYieldDao cornYieldDao;

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
        cornHeightAndChlo.setCornHeight(height);
        cornHeightAndChlo.setChlorophyll(chlo);
        cornHeightAndChloDao.save(cornHeightAndChlo);
        System.out.println("save CornHeightAndChlo successfully");
    }
    public void saveCornHeightAndChloByFile(){//待补全，通过文件保存

    }

    public List<CornHeightAndChlo> getAllCornHeightAndChlo() throws Exception{
        System.out.println("Get all CornHeightAndChlo");
        return cornHeightAndChloDao.getAll();
    }

    public void deleteCornHeightAndChlo(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2) throws Exception{
        System.out.println("Delete CornHeightAndChlo By Primary Key");
        cornHeightAndChloDao.deleteByPrimaryKey(DOY, TRT, NUM_1, NUM_2);
    }

    public CornHeightAndChlo getCornHeightAndChlo(Integer DOY, Integer TRT, Float NUM_1, Float NUM_2) throws Exception{
        System.out.println("Get CornHeightAndChlo By Primary Key");
        return cornHeightAndChloDao.getByPrimaryKey(DOY, TRT, NUM_1, NUM_2);
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

        cornLeafDao.save(cornLeaf);
        System.out.println("Save CornLeaf Successfully");
    }

    public void saveCornLeafByFile(){//待补全

    }

    public void deleteCornLeaf(Integer DOY, Float TRT) throws Exception{
        System.out.println("Delete CornLeaf By Primary Key");
        cornLeafDao.deleteByPrimaryKey(DOY, TRT);
    }

    public List<CornLeaf> getAllCornLeaf() throws Exception{
        System.out.println("Get All CornLeaf");
        return cornLeafDao.getAll();
    }

    public CornLeaf getCornLeaf(Integer DOY, Float TRT) throws Exception{
        System.out.println("Get CornLeaf By Primary Key");
        return cornLeafDao.getByPrimaryKey(DOY, TRT);
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

    public void saveCornYieldByFile(){//待补全

    }

    public List<CornYield> getAllCornYield() throws Exception{
        System.out.println("Get All CornYield");
        return cornYieldDao.getAll();
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
