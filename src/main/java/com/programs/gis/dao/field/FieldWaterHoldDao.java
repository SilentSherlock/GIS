package com.programs.gis.dao.field;

import com.programs.gis.entity.field.FieldWaterHold;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Date: 2020/5/14
 * Description: offer The field water hold data access operations with interface
 */
@Repository
public interface FieldWaterHoldDao {

    void save(FieldWaterHold fieldWaterHold) throws Exception;
    void deleteByPrimaryKey(String NUM_2) throws Exception;
    List<FieldWaterHold> getAll() throws Exception;
    List<FieldWaterHold> getByAttr(String attrName, Object attrValue) throws Exception;
}
