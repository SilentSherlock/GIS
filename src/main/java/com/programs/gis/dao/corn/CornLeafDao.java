package com.programs.gis.dao.corn;

import com.programs.gis.entity.corn.CornLeaf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CornLeafDao {

    void save(CornLeaf cornLeaf) throws Exception;
    void deleteByPrimaryKey(Integer DOY, Float TRT) throws Exception;
    List<CornLeaf> getAll() throws Exception;
    CornLeaf getByPrimaryKey(Integer DOY, Float TRT) throws Exception;
    List<CornLeaf> getByAttr(@Param("attr") Object attr, @Param("attrName") String attrName) throws Exception;
}
