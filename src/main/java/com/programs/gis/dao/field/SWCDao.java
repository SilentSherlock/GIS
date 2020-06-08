package com.programs.gis.dao.field;

import com.programs.gis.entity.field.SWC;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Date: 2020/5/14
 * Description: offer Data access operation for SWC
 */
@Repository
public interface SWCDao {

    void save(SWC swc) throws Exception;
    void deleteByPrimaryKey(Integer DOY, String NUM_2) throws Exception;
    List<SWC> getAll() throws Exception;
    List<SWC> getByAttr(String attrName, Object attrValue) throws Exception;
}
