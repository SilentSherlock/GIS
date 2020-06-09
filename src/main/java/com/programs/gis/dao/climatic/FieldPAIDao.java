package com.programs.gis.dao.climatic;

import com.programs.gis.entity.climatic.FieldPAI;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Date: 2020/6/9
 * Description: offer database operations for entity FieldPAI
 */
@Repository
public interface FieldPAIDao {

    void save(FieldPAI fieldPAI) throws Exception;
    List<FieldPAI> getAll() throws Exception;
    void deleteByPrimaryKey(Integer DOY, Integer TRT) throws Exception;
    List<FieldPAI> getByAttr(String attrName, Object attrValue) throws Exception;
}
