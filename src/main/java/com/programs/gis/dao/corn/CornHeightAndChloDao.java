package com.programs.gis.dao.corn;

import com.programs.gis.entity.corn.CornHeightAndChlo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CornHeightAndChloDao {

    void save(CornHeightAndChlo cornHeightAndChlo) throws Exception;
    List<CornHeightAndChlo> getAll() throws Exception;
    void deleteByPrimaryKey(Integer DOY, Float NUM_2) throws Exception;
    CornHeightAndChlo getByPrimaryKey(Integer DOY, Float NUM_2) throws Exception;
    List<CornHeightAndChlo> getByAttr(@Param("attr") Object attr, @Param("attrName") String attrName) throws Exception;
}
