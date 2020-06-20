package com.programs.gis.dao.climatic;

import com.programs.gis.entity.climatic.FieldPAI;
import org.apache.ibatis.annotations.Param;
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
    void deleteByPrimaryKey(@Param("DOY") Integer DOY, @Param("TRT") Integer TRT) throws Exception;
    List<FieldPAI> getByAttr(@Param("attrName") String attrName, @Param("attrValue") Object attrValue) throws Exception;
}
