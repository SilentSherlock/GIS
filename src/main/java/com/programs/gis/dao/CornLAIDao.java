package com.programs.gis.dao;

import com.programs.gis.entity.CornLAI;
import java.util.List;

/**
 * Author:  SilentSherlock
 * Date: 2020/5/9
 * Time: 14:58
 */
public interface CornLAIDao {

    void save(CornLAI cornLAI) throws Exception;
    void deleteByPrimaryKey(Integer DOY, Float NUM_2) throws Exception;
    List<CornLAI> getAll() throws Exception;
    List<CornLAI> getByAttr(Object attrValue, String attrName) throws Exception;
}
